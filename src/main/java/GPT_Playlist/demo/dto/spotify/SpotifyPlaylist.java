package GPT_Playlist.demo.dto.spotify;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyPlaylist {
    private String name;
    private String description;
    private List<Image> images;
    private ExternalUrls external_urls;
}
