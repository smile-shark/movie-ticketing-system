package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MovieTypeMiddle {
    private String movieTypeId;
    private String movieId;
}
