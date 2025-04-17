package com.movie.entity;

import com.movie.entity.groups.InsertMovie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class MovieType {
    @Length(min=32, max=32,message = "电影类型出错",groups = {InsertMovie.class})
    private String movieTypeId;
    private String movieTypeName;
}
