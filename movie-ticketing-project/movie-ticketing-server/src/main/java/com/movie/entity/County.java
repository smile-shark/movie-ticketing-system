package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class County {
    private String countyId;
    private String marketId;
    private String countyName;
}
