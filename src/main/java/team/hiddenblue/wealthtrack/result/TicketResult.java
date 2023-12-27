package team.hiddenblue.wealthtrack.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketResult {
    private String type;
    private String typeDescription;
    private int imageAngle;
    private int rotatedImageWidth;
    private int rotatedImageHeight;
    private String kind;
    private String kindDescription;
    private ArrayList<TicketItemResult> itemList;
}
