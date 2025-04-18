package me.ehp246.aufllm.deepseek;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.ehp246.aufllm.model.ChatCompletionRequest;
import me.ehp246.aufllm.model.Message;
import me.ehp246.test.JacksonInfra;
import me.ehp246.test.model.CompanyRev;

@SpringBootTest(classes = { AppConfig.class, JacksonInfra.class }, webEnvironment = WebEnvironment.NONE)
@EnabledIfSystemProperty(named = "me.ehp246.aufllm.test.deepseek", matches = "enabled")
class DeepSeekApiTest {
    @Autowired
    private DeepSeekApi api;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_01() {
        final var response = this.api.postChatCompletion(new ChatCompletionRequest("deepseek-chat",
                new Message[] { new Message("system",
                        "Extract the following data as JSON: company name, revenue, quarter, CEO, product, announcement date. Return ONLY valid JSON."),
                        new Message("user",
                                "Apple Inc. reported $90 billion in revenue for Q1 2024. \r\n"
                                        + "The CEO, Tim Cook, announced a new iPhone model on January 15, 2024.") },
                ChatCompletionRequest.ResponseFormat.JSON_OBJECT, 0));

        Assertions.assertNotNull(response.systemFingerprint());
        Assertions.assertEquals(1, response.choices().length);
    }

    @Test
    void choice_01() throws JsonMappingException, JsonProcessingException {
        final var choices = this.api.postChatCompletion(new ChatCompletionRequest("deepseek-chat",
                new Message[] { new Message("system",
                        "Extract the following data as JSON: company name, revenue, quarter, CEO, product, announcement date. Return ONLY valid JSON."),
                        new Message("user",
                                "Apple Inc. reported $90 billion in revenue for Q1 2024. \r\n"
                                        + "The CEO, Tim Cook, announced a new iPhone model on January 15, 2024.") },
                ChatCompletionRequest.ResponseFormat.JSON_OBJECT, 0)).choices();

        final var rev = this.objectMapper.readValue(choices[0].message().content(), CompanyRev.class);

        Assertions.assertEquals("Tim Cook", rev.ceo());
        Assertions.assertEquals("Apple Inc.", rev.companyName());
        Assertions.assertEquals("$90 billion", rev.revenue());
        Assertions.assertEquals("Q1 2024", rev.quarter());
    }
}
