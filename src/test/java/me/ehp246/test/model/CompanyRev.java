package me.ehp246.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CompanyRev(String companyName, String revenue, String quarter, @JsonProperty("CEO") String ceo,
        String product, String announcementDate) {

}
