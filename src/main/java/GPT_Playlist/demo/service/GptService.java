package GPT_Playlist.demo.service;

import GPT_Playlist.demo.dto.gpt.GptResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class GptService {

    private final WebClient webClient;

    public GptService(@Value("${openai.api-key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Mono<GptResponse> analyzeEmotion(String inputText) {
        String prompt = "다음 문장에서 감정을 한 단어로 분석해줘: \"" + inputText + "\". " +
                "기쁨, 슬픔, 분노, 불안, 설렘, 중립 중 하나만 출력해.";

        return webClient.post()
                .bodyValue(Map.of(
                        "model", "gpt-3.5-turbo",
                        "messages", new Object[]{
                                Map.of("role", "user", "content", prompt)
                        }
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    String content = (String) ((Map<?, ?>)((Map<?, ?>)((java.util.List<?>)response.get("choices")).get(0)).get("message")).get("content");
                    return new GptResponse(content.trim());
                });
    }
}
