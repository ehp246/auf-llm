package me.ehp246.aufllm.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatCompletionRequest(String model, Message[] messages, ResponseFormat responseFormat,
        Integer temperature) {
    public record ResponseFormat(String type) {
        public static final ResponseFormat JSON_OBJECT = new ResponseFormat("json_object");
    }
}
