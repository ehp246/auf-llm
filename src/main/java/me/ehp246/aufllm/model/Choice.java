package me.ehp246.aufllm.model;

public record Choice(Integer index, Message messages, String logprobs, String finishReason) {
}