package team.hiddenblue.wealthtrack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiptItemResult {
    /**
     * 识别的字段中文描述
     */
    private String description;

    /**
     * 识别的字段的值
     */
    private String value;

    /**
     * 识别的字段
     */
    private String key;
}
