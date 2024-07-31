package com.kitcat.likelion.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class LocationDTO {
    private BigDecimal latitude;

    private BigDecimal longitude;
}
