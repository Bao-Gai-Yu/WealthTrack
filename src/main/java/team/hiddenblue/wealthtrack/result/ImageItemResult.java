package team.hiddenblue.wealthtrack.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageItemResult {
    private String text;
}
