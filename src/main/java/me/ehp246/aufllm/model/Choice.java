package me.ehp246.aufllm.model;

public record Choice(Integer index, Message message, String logprobs, String finishReason) {
}