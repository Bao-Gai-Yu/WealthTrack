package team.hiddenblue.wealthtrack.Result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CropEnhanceImageResult {
    /**
     * 图像处理后的宽
     */
    private int croppedWidth;

    /**
     * 图像处理后的高
     */
    private int croppedHeight;

    /**
     * 图像处理后的jpg图片，base64格式
     */
    private String image;

    /**
     * 切图区域的4个角点坐标, 是个长度为8的数组
     */
    private ArrayList<Integer> position;

    /**
     * 图像角度，定义0度为人类阅读文字的图像方向，称为正置图像，本字段表示输入图像是正置图像进行顺时针若干角度的旋转所得
     */
    private int angle;
}
