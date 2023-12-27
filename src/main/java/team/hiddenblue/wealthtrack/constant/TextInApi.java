package team.hiddenblue.wealthtrack.constant;

import org.springframework.stereotype.Component;

/**
 * 合合科技 API 常量集
 * @author Patrick_Star
 * @version 1.2
 */
@Component
public class TextInApi {
    /**
     * 火车票识别
     */
    public static final String TRAIN_TICKET = "https://api.textin.com/robot/v1.0/api/train_ticket";
    /**
     * 通用文字识别
     */
    public static final String COMMON_RECOGNIZE = "https://api.textin.com/ai/service/v2/recognize";
    /**
     * 图片切边增强
     */
    public static final String CROP_ENHANCE = "https://api.textin.com/ai/service/v1/crop_enhance_image";
    /**
     *去屏幕纹
     */
    public static final String DEMOIRE = "https://api.textin.com/ai/service/v1/demoire";

    /**
     * 文档图像切边矫正
     */
    public static final String DEWARP = "https://api.textin.com/ai/service/v1/dewarp";
}
