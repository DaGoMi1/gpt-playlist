package GPT_Playlist.demo.dto.gpt;

import lombok.Data;

@Data
public class GptResponse {
    private String emotion;

    public GptResponse(String emotion) {
        this.emotion = emotion;
    }
}
