package GPT_Playlist.demo.controller;

import GPT_Playlist.demo.dto.gpt.GptRequest;
import GPT_Playlist.demo.dto.spotify.SpotifyPlaylist;
import GPT_Playlist.demo.service.GptService;
import GPT_Playlist.demo.service.SpotifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaylistController {
    private final GptService gptService;
    private final SpotifyService spotifyService;

    @PostMapping("/spotify/recommend")
    public Mono<List<SpotifyPlaylist>> getEmotion(@Valid @RequestBody GptRequest request) {
        return gptService.analyzeEmotion(request.getText())
                .doOnNext(response -> System.out.println("🎯 GPT 감정 분석 결과: " + response))
                .flatMap(response -> spotifyService.searchPlaylistsByMood(response.getEmotion()));
    }
}
