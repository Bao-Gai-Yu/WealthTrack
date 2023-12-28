package team.hiddenblue.wealthtrack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReceiptResult {
    /**
     * 图片角度，指原图需要经过顺时针旋转多少度，才能得到正方向的图片
     */
    private int imageAngle;
    /**
     * 正方向时图片的宽
     */
    private int rotatedImageWidth;
    /**
     * 正方向时图片的高
     */
    private int rotatedImageHeight;

    private ArrayList<ReceiptItemResult> itemList;

    private ArrayList<ReceiptObjectResult> objectList;

}
