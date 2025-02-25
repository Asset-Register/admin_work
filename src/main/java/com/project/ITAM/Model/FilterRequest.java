package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilterRequest {

    @JsonProperty("searchText")
    private String searchText;

    @JsonProperty("filterExpression")
    private FilterExpression filterExpression;

    @JsonProperty("selectedKeys")
    private String selectedKeys;

    public FilterRequest() {
    }

    public FilterRequest(String searchText, FilterExpression filterExpression, String selectedKeys) {
        this.searchText = searchText;
        this.filterExpression = filterExpression;
        this.selectedKeys = selectedKeys;
    }

    // Inner class to represent the FilterExpression
    @Data
    public static class FilterExpression {

        @JsonProperty("logic")
        private String logic;

        @JsonProperty("conditions")
        private List<Condition> conditions;

        public FilterExpression() {
        }
    }

    // Inner class to represent each condition
    @Data
    public static class Condition {

        @JsonProperty("field")
        private String field;
        @JsonProperty("operator")
        private String operator;
        @JsonProperty("value")
        private String value;

        public Condition() {
        }
    }
}
