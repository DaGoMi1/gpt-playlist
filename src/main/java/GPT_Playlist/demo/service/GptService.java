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
        String prompt = "너는 감정 분석가야. 반드시 한 단어의 감정만 출력해. 그 외의 어떤 말도 하지 마.: \"" + inputText + "\". " +
                "기쁨, 행복, 즐거움, 감사, 슬픔, 우울, 외로움, 분노, 짜증, 불안, 긴장, 설렘, 사랑, 중립, 평온, 놀람, 두려움, 자신감, 지루함, 감동 중 하나만 출력해.";

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
