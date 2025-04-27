package com.movie.entity;

import com.movie.entity.groups.InsertScreeningRoom;
import com.movie.entity.groups.UpdateScreeningRoom;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class ScreeningRoom {
    @Length(min=32,max=32,message = "放映厅id的格式不正确",groups = {UpdateScreeningRoom.class})
    private String screeningRoomId;
    @Length(min = 32,max = 32,message = "影院id的格式不正确",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    private String cinemaId;
    @NotNull(message = "放映厅名称不能为空",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    @Length(min=1,max=30,message = "放映厅名称最少1个字符，最多30个字符",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    private String screeningRoomName;
    @NotNull(message = "放映厅行不能为空",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    @Min(value = 1,message = "放映厅行必须大于0",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    private Integer rowAll;
    @Min(value = 1,message = "放映厅列必须大于0",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    @NotNull(message = "放映厅列不能为空",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    private Integer colAll;
    @NotNull(message = "放映厅布局不能为空",groups = {InsertScreeningRoom.class,UpdateScreeningRoom.class})
    private String layout; // json格式的布局
}
