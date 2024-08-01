package com.kitcat.likelion.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private BigDecimal latitude;

    private BigDecimal longitude;
}
