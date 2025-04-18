package me.ehp246.aufllm.model;

public record ChatCompletionRequest(String model, Message[] messages, ResponseFormat responseFormat,
        Integer temperature) {
    public record ResponseFormat(String type) {
        public static final ResponseFormat JSON_OBJECT = new ResponseFormat("json_object");
    }
}
