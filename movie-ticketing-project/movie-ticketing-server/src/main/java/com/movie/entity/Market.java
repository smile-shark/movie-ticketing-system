package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Market {
    private String marketId;
    private String economizeId;
    private String marketName;
    private List<County> counties;
}
