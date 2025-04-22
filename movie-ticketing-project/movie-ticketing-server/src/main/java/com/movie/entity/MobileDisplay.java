package com.movie.entity;

import com.movie.entity.groups.MobileDisplayDelete;
import com.movie.entity.groups.MobileDisplayInsert;
import com.movie.entity.groups.MobileDisplayUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class MobileDisplay {
    @Length(min=32, max=32, message="电影ID不符合规则",groups = {MobileDisplayUpdate.class,
            MobileDisplayInsert.class})
    private String movieId;
    private Movie movie;
    @NotNull(message = "图片不能为空", groups = {MobileDisplayUpdate.class,
            MobileDisplayInsert.class, MobileDisplayDelete.class})
    private String mobileDisplayImage;
    private LocalDateTime mobileDisplayEndTime;
}
