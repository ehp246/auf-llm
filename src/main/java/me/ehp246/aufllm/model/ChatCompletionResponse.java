package me.ehp246.aufllm.model;

import java.time.Instant;

public record ChatCompletionResponse(String id, String object, Instant created, String model, Choice[] choices,
        String systemFingerprint) {
}
