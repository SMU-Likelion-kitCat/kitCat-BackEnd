package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor
public class Path {
    @Id
    @Column(name = "path_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @ManyToOne(fetch = LAZY)
    private UserRecord userRecord;

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;

        if(!userRecord.getPaths().contains(this)) {
            userRecord.addPath(this);
        }
    }

    public Path(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
