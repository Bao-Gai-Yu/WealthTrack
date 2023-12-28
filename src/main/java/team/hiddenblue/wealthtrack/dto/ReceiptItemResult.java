package team.hiddenblue.wealthtrack.dto;

import lombok.Data;

@Data
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
