package com.boot.mybatis20220923nk.dto;


import com.boot.mybatis20220923nk.domain.NewsFile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class NewsReadRespDto {
    private int id;
    private String title;
    private String writer;
    private String broadcastingName;
    private String content;

    //2022-09-28 add
    private List<NewsFile> fileList;

    private String createDate;
    private String updateDate;

}
