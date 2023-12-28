package team.hiddenblue.wealthtrack.dto;

import lombok.Data;

@Data
public class ReceiptObjectItemResult {
    /**
     * 商品金额
     */
    private String price;

    /**
     * 商品名称
     */
    private String data;

    /**
     * 商品数量
     */
    private String amount;
}
