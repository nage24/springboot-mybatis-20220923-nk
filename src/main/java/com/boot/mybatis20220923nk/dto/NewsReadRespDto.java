package com.boot.mybatis20220923nk.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class NewsReadRespDto {
    private int news_id;
    private String news_title;
    private String news_writer;
    private String news_broadcastingName;
    private String news_content;


    private LocalDateTime create_Date;
    private LocalDateTime update_Date;

}
