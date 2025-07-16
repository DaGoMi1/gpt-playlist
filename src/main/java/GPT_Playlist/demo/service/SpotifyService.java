package GPT_Playlist.demo.service;

import GPT_Playlist.demo.dto.spotify.SpotifyPlaylist;
import GPT_Playlist.demo.dto.spotify.SpotifySearchResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<String> getAccessToken() {
        return reactiveRedisTemplate.opsForValue().get("spotify_access_token")
                .flatMap(Mono::just) // 값 있으면 그대로 사용
                .switchIfEmpty( // 값 없을 때 실행
                        requestNewAccessToken()
                                .flatMap(newToken ->
                                        reactiveRedisTemplate.opsForValue()
                                                .set("spotify_access_token", newToken, Duration.ofMinutes(55))
                                                .thenReturn(newToken)
                                )
                );
    }

    public Mono<String> requestNewAccessToken() {
        return WebClient.create("https://accounts.spotify.com/api/token")
                .post()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .doOnNext(System.out::println)
                .map(TokenResponse::getAccessToken);
    }

    @Data
    public static class TokenResponse {

        @JsonProperty("access_token")
        private String accessToken;

    }

    public Mono<List<SpotifyPlaylist>> searchPlaylistsByMood(String moodKeyword) {
        return getAccessToken()
                .flatMap(token -> WebClient.create("https://api.spotify.com/v1/search")
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("q", mapEmotionToKeyword(moodKeyword))
                                .queryParam("limit", 10)
                                .queryParam("type", "playlist")
                                .build())
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(SpotifySearchResponse.class)
                        .map(response -> response.getPlaylists().getItems().stream()
                                .filter(Objects::nonNull) // null 제거
                                .collect(Collectors.toList())
                        )
                );
    }

    private String mapEmotionToKeyword(String emotion) {
        if (emotion.contains("두려움")) return "안심시키는 음악";
        if (emotion.contains("기쁨")) return "희망찬 음악";
        if (emotion.contains("행복")) return "행복한 음악";
        if (emotion.contains("즐거움")) return "신나는 음악";
        if (emotion.contains("감사")) return "따뜻한 음악";
        if (emotion.contains("슬픔")) return "위로가 되는 음악";
        if (emotion.contains("우울")) return "차분한 음악";
        if (emotion.contains("외로움")) return "포근한 음악";
        if (emotion.contains("분노")) return "기분전환 되는 음악";
        if (emotion.contains("짜증")) return "에너지 넘치는 음악";
        if (emotion.contains("불안")) return "편안한 음악";
        if (emotion.contains("긴장")) return "진정시키는 음악";
        if (emotion.contains("설렘")) return "사랑스러운 음악";
        if (emotion.contains("사랑")) return "감미로운 음악";
        if (emotion.contains("중립")) return "요즘 뜨는 음악";
        if (emotion.contains("평온")) return "잔잔한 음악";
        if (emotion.contains("놀람")) return "흥미로운 음악";
        if (emotion.contains("자신감")) return "힘이 나는 음악";
        if (emotion.contains("지루함")) return "활기찬 음악";
        if (emotion.contains("감동")) return "감성적인 음악";

        return "요즘 뜨는 음악"; // fallback
    }

}
