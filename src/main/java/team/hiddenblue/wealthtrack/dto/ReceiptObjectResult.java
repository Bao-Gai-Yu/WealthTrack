package team.hiddenblue.wealthtrack.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ReceiptObjectResult {

    private ArrayList<ReceiptObjectItemResult> objectItemList;

    /**
     * sku商品详细信息
     */
    private String type;
}
