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
import me.ehp246.test.model.Layoff;

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
                Message.systemAndUser(
                        "Extract the following data as JSON: company name, revenue, quarter, CEO, product, announcement date. Return ONLY valid JSON.",
                        "Apple Inc. reported $90 billion in revenue for Q1 2024. \r\n"
                                + "The CEO, Tim Cook, announced a new iPhone model on January 15, 2024."),
                ChatCompletionRequest.ResponseFormat.JSON_OBJECT, 0)).choices();

        final var rev = this.objectMapper.readValue(choices[0].message().content(), CompanyRev.class);

        Assertions.assertEquals("Tim Cook", rev.ceo());
        Assertions.assertEquals("Apple Inc.", rev.companyName());
        Assertions.assertEquals("$90 billion", rev.revenue());
        Assertions.assertEquals("Q1 2024", rev.quarter());
    }

    @Test
    void layoff_01() throws JsonMappingException, JsonProcessingException {
        final var choices = this.api.postChatCompletion(new ChatCompletionRequest("deepseek-chat",
                Message.systemAndUser("Extract from the article the following data:\r\n"
                        + "'company': the company or organization name implementing the layoff,\r\n"
                        + "'number': the number of laid-off employees,\r\n"
                        + "'priorTotal': the total number of employees of the company before the layoff, set to null if not mentioned,\r\n"
                        + "'percentage': Use the data from the article if provided,\r\n"
                        + "'location': the location where the layoff happened,\r\n"
                        + "'date': the date when the layoff happened, formatted in YYYY-MM-DD, set to null if the vaule can not be determined,\r\n"
                        + "'position': the specific job positions or departments affected by the layoff,\r\n"
                        + "'reason': the reasons provided for the layoffs, e.g., bankruptcy, restructuring, financial difficulties,\r\n"
                        + "\r\n" + "Return the above data in the specified JSON schema format.",
                        "Amscan, Party City’s wholesale division, is laying off 471 workers and closing its Orange County distribution center due to Chapter 11 bankruptcy. The closure is scheduled for Friday, and affected roles include material handlers, supervisors, and HR staff."),
                ChatCompletionRequest.ResponseFormat.JSON_OBJECT, 0)).choices();

        final var layoff = this.objectMapper.readValue(choices[0].message().content(), Layoff.class);

        Assertions.assertEquals(471, layoff.number());
        Assertions.assertEquals(null, layoff.date());
    }
}
