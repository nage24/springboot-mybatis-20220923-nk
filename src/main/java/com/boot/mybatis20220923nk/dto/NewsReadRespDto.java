package com.boot.mybatis20220923nk.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class NewsReadRespDto {
    private int id;
    private String title;
    private String writer;
    private String broadcastingName;
    private String content;


    private String createDate;
    private String updateDate;

}
