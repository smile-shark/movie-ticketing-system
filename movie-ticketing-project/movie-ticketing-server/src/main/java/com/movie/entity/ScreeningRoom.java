package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ScreeningRoom {
    private String screeningRoomId;
    private String cinemaId;
    private String screeningRoomName;
    private Integer rowAll;
    private Integer colAll;
    private String layout; // json格式的布局
}
