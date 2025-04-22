package me.ehp246.aufllm.model;

public record Message(String role, String content) {

    public static Message system(String content) {
        return new Message("system", content);
    }

    public static Message user(String content) {
        return new Message("system", content);
    }

    public static Message[] systemAndUser(String system, String user) {
        if (system == null) {
            return new Message[] { new Message("user", user) };
        }

        return new Message[] { new Message("system", system), new Message("user", user) };
    }
}