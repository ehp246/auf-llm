package me.ehp246.aufllm.model;

import java.time.Instant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatCompletionResponse(String id, String object, Instant created, String model, Choice[] choices,
        String systemFingerprint) {
}
