package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class Answers {

    @JsonProperty("value")
    public int value;

    @JsonProperty("text")
    public List<Languages> answerText;

}