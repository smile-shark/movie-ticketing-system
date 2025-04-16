package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MovieType {
    private String movieTypeId;
    private String movieTypeName;
}
