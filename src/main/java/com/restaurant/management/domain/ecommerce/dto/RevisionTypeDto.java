package com.restaurant.management.domain.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RevisionTypeDto {

    @JsonProperty("add")
    ADD,

    @JsonProperty("mod")
    MOD,

    @JsonProperty("del")
    DEL
}
