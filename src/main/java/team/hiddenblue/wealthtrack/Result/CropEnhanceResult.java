package team.hiddenblue.wealthtrack.Result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CropEnhanceResult {
    /**
     * 原图的宽
     */
    private int originWidth;

    /**
     * 原图的高
     */
    private int originHeight;

    /**
     * 修改后图像信息
     */
    private ArrayList<CropEnhanceImageResult> imageList;
}

