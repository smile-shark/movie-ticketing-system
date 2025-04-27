package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CinemaBrand {
    private String cinemaBrandId;
    private String cinemaBrandName;
    private String cinemaBrandLogo;
}
