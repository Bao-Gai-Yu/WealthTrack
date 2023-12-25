package team.hiddenblue.wealthtrack.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.hiddenblue.wealthtrack.Result.*;
import team.hiddenblue.wealthtrack.config.TextInConfig;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.constant.TextInApi;
import team.hiddenblue.wealthtrack.exception.AppException;
import team.hiddenblue.wealthtrack.mapper.LedgerPermissionMapper;
import team.hiddenblue.wealthtrack.service.ExpensesRecordService;
import team.hiddenblue.wealthtrack.service.TextInService;
import team.hiddenblue.wealthtrack.util.TextInFetch;

import java.util.Base64;
import java.util.Calendar;


@Service
@RequiredArgsConstructor
public class TextInServiceImpl implements TextInService {

    @Autowired
    private ExpensesRecordService expensesRecordService;

    @Autowired
    private LedgerPermissionMapper ledgerPermissionMapper;

    /**
     *
     * @param img
     * @return
     */
    public ExpenseRecordResult insertByTicket(byte[] img) {

        //将图片发送到TextApi接口，提取文本信息后存储在result中
        Object result = TextInFetch.post(TextInApi.TRAIN_TICKET, img);
        //用于处理JSON数据的一个类
        ObjectMapper objectMapper = new ObjectMapper();
        //把result转换为自定义的Result类型
        TicketResult ticketResult = objectMapper.convertValue(result, TicketResult.class);

        String value = null;
        String departureStation = null;
        String arrivalStation = null;
        String dateRaw = null;
        for (TicketItemResult item : ticketResult.getItemList()) {
            if ("price".equals(item.getKey())) {
                value = item.getValue();
            } else if ("departure_date".equals(item.getKey())) {
                dateRaw = item.getValue().substring(0, 10);
            } else if ("arrival_station".equals(item.getKey())) {
                arrivalStation = item.getValue();
            } else if ("departure_station".equals(item.getKey())) {
                departureStation = item.getValue();
            }
        }
        String remark = "本张火车票是从 " + departureStation + " 到 " + arrivalStation + " 的火车票";
        return ExpenseRecordResult.builder()
                .type(true)
                .value(value)
                .kind("traffic")
                .remark(remark)
                .date(dateRaw).build();
    }

    public ExpenseRecordResult insertByVoice(String sentence) {
        String dateRaw = null;
        Calendar calendar = Calendar.getInstance();
        if (sentence.contains("年") && sentence.contains("月") && sentence.contains("日")) {
            dateRaw = sentence.split("日", 2)[0];
            dateRaw = dateRaw.replaceFirst("年", "-");
            dateRaw = dateRaw.replaceFirst("月", "-");
        } else if (sentence.contains("月") && sentence.contains("日")) {
            dateRaw = sentence.split("日", 2)[0];
            dateRaw = dateRaw.replaceFirst("月", "-");
        } else if (sentence.contains("今天")) {
            sentence = sentence.substring(2);
            dateRaw = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DATE);
        } else if (sentence.contains("昨天")) {
            sentence = sentence.substring(2);
            calendar.add(Calendar.DATE, -1);
            dateRaw = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DATE);
        }
        String remark;
        String value;
        String[] s1;
        boolean type;
        if (sentence.contains("花了")) {
            s1 = sentence.split("花了", 2);
            type = true;
        } else if (sentence.contains("赚了")) {
            s1 = sentence.split("赚了", 2);
            type = false;
        } else {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        remark = s1[0];
        value = s1[1].substring(0, s1[1].length() - 1);
        return ExpenseRecordResult.builder()
                .date(dateRaw)
                .remark(remark)
                .value(value)
                .type(type).build();
    }

    private byte[] processImage(byte []img) {
        ObjectMapper objectMapper = new ObjectMapper();
        Base64.Decoder decoder = Base64.getDecoder();
        Object result = TextInFetch.post(TextInApi.CROP_ENHANCE, img);
        CropEnhanceResult cropEnhanceResult = objectMapper.convertValue(result, CropEnhanceResult.class);
        result = TextInFetch.post(TextInApi.DEMOIRE, decoder.decode(cropEnhanceResult.getImageList().get(0).getImage()));
        ProcessImageResult processImageResult = objectMapper.convertValue(result, ProcessImageResult.class);
        return decoder.decode(processImageResult.getImage());
    }
}
