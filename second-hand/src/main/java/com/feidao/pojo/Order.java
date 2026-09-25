package com.feidao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer goodsId;
    private Integer buyerId;
    private Integer sellerId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
