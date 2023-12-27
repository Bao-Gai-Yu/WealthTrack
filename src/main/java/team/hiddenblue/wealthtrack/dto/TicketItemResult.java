package team.hiddenblue.wealthtrack.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TicketItemResult {
    private String key;
    private String value;
    private String description;
    private ArrayList<Integer> position;
}
