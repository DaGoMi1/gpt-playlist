package GPT_Playlist.demo.dto.spotify;

import lombok.Data;

import java.util.List;

@Data
public class Playlists {
    private List<SpotifyPlaylist> items;
}
