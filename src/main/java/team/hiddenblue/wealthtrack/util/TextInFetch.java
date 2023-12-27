package team.hiddenblue.wealthtrack.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import team.hiddenblue.wealthtrack.dto.TextInResponseResult;
import team.hiddenblue.wealthtrack.config.ApplicationContextProvider;
import team.hiddenblue.wealthtrack.config.TextInConfig;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.constant.TextInApi;
import team.hiddenblue.wealthtrack.exception.AppException;


/**
 * 调用合合科技 OCR 接口识别图片的工具
 */
public class TextInFetch {


    /**
     * 用于发送HTTP请求
     *
     * @return
     */
    private static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    private static TextInConfig getTextInConfig() {
        return ApplicationContextProvider.getApplicationContext().getBean(TextInConfig.class);
    }


    /**
     * @param url
     * @param img
     * @return
     */
    public static Object post(String url, byte[] img) {

        TextInConfig textInConfig = getTextInConfig();

        //设置HTTP头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-ti-app-id", textInConfig.getAppId());
        headers.add("x-ti-secret-code", textInConfig.getSecretCode());
        headers.add("connection", "Keep-Alive");
        if (url.equals(TextInApi.CROP_ENHANCE)) {
            //1表示矫正图片方向，详见api文档
            headers.add("correct_direction", "1");
        }
        //表示发送二进制数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(img, headers);
        ResponseEntity<TextInResponseResult> resp = getRestTemplate().postForEntity(url, httpEntity, TextInResponseResult.class);
        if (resp.getBody() == null) {
            throw new AppException(ErrorCode.SERVER_ERROR);
        } else if (resp.getBody().getCode() != 200) {
            switch (resp.getBody().getCode()) {
                case 40301: {
                    throw new AppException(ErrorCode.IMAGE_TYPE_ERROR);
                }
                case 40302: {
                    throw new AppException(ErrorCode.IMAGE_SIZE_ERROR);
                }
                case 40303: {
                    System.out.println(url);
                    throw new AppException(ErrorCode.FILE_TYPE_ERROR);
                }
                case 40304: {
                    throw new AppException(ErrorCode.IMAGE_MEASUREMENT_ERROR);
                }
                default: {
                    System.out.println(resp.getBody().getMessage());
                    throw new AppException(ErrorCode.SERVER_ERROR);
                }
            }
        }
        return resp.getBody().getResult();
    }
}
