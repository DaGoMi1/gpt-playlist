package GPT_Playlist.demo.dto.gpt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GptRequest {
    @NotBlank(message = "text 미입력")
    private String text;
}
