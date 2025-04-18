package me.ehp246.aufllm.deepseek;

import me.ehp246.aufllm.model.ChatCompletionRequest;
import me.ehp246.aufllm.model.ChatCompletionResponse;
import me.ehp246.aufrest.api.annotation.ByRest;
import me.ehp246.aufrest.api.annotation.ByRest.Auth;
import me.ehp246.aufrest.api.annotation.OfRequest;
import me.ehp246.aufrest.api.rest.AuthScheme;

@ByRest(value = "${me.ehp246.aufllm.deepseek.base-url:https://api.deepseek.com}", auth = @Auth(scheme = AuthScheme.BEARER, value = {
        "${me.ehp246.aufllm.deepseek.api-key}" }))
public interface DeepSeekApi {
    @OfRequest("/chat/completions")
    ChatCompletionResponse postChatCompletion(ChatCompletionRequest request);
}
