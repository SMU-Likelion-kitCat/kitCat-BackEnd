package com.kitcat.likelion.requestDTO;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LocationDTO {
    private BigDecimal latitude;

    private BigDecimal longitude;
}
