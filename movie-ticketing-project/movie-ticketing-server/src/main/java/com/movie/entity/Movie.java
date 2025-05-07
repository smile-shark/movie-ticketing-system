package com.movie.entity;

import com.movie.entity.groups.InsertMovie;
import com.movie.entity.groups.UpdateMovie;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
public class    Movie {
    @Length(min = 32, max = 32, message = "唯一标识符不符合规则", groups = {UpdateMovie.class})
    private String movieId;
    @NotBlank(message = "电影名称不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String movieName;
    @NotNull(message = "电影上映时间不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private LocalDateTime movieStartTime;
    @NotBlank(message = "电影时长不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String movieDuration;
    @NotBlank(message = "导演不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String director;
    @NotBlank(message = "主演不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String star;
    @NotBlank(message = "电影简介不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String introduction;
    @Min(value = 0, message = "电影评分不能为负数", groups = {InsertMovie.class, UpdateMovie.class})
    @Max(value = 10, message = "电影评分不能超过10分", groups = {InsertMovie.class, UpdateMovie.class})
    @NotNull(message = "电影评分不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private Double score;
    @NotBlank(message = "电影海报不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String posterImage;
    @NotBlank(message = "发行地区不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private String issuingRegion;
    @NotEmpty(message = "电影类型不能为空", groups = {InsertMovie.class, UpdateMovie.class})
    private List<MovieType> tags;
}
