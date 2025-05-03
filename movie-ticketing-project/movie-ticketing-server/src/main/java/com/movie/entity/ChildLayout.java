package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChildLayout {
    private Integer x;
    private Integer y;
    private Boolean seat;
    private String color;
    private Boolean isBuy;
    private Boolean isSelected;
    private String borderColor;
}
