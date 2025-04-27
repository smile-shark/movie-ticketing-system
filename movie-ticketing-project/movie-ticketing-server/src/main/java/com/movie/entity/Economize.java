package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Economize {
    private String economizeId;
    private String economizeName;
    private List<Market> markets;
}
