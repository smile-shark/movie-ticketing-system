package com.movie.entity;

import com.movie.entity.groups.InsertOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class Order {
   private String orderId;
   @Length(min=32,max=32,message = "用户数据格式不正确",groups = {InsertOrder.class})
   private String userId;
   private User user;
   @Length(min=32,max=32,message = "排片数据格式不正确",groups = {InsertOrder.class})
   private String sliceArrangementId;
   private SliceArrangement sliceArrangement;
   @NotNull(message = "票价不能为空",groups = {InsertOrder.class})
   @Min(value = 0,message = "票价不能为负数",groups = {InsertOrder.class})
   private Double voteAllPrice;
   @NotNull(message = "订购的座位不能为空",groups = {InsertOrder.class})
   private String seats;
   private Integer votePayState;
   private LocalDateTime payTimeOut;
   private LocalDateTime createTime;
   private String codeImage;
   @NotNull(message = "布局不能为空",groups = {InsertOrder.class})
   private String layout;
}
