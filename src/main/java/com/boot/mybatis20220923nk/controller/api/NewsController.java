package com.boot.mybatis20220923nk.controller.api;

import com.boot.mybatis20220923nk.domain.News;
import com.boot.mybatis20220923nk.dto.CMRespDto;
import com.boot.mybatis20220923nk.dto.NewsReadRespDto;
import com.boot.mybatis20220923nk.dto.NewsWriteReqDto;
import com.boot.mybatis20220923nk.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;

    @PostMapping("/news")
    public ResponseEntity<?> write(NewsWriteReqDto newsWriteReqDto) {

        log.info("{}", newsWriteReqDto);

        // News news = newsWriteReqDto.toEntity();
        // news.setNews_writer("짱구");

        News news = newsWriteReqDto.toEntity("짱아");

        int result = newsRepository.save(news);
        
        if(result == 0) {
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "작성 실패", news));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "새 글 작성 완료", news));
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<?>  read(@PathVariable int newsId) {

        log.info("{}", newsId);

       News news = newsRepository.getNews(newsId);

        NewsReadRespDto newsReadRespDto = news.toDto();

        return ResponseEntity.ok(new CMRespDto<>(1, "게시글 불러오기 성공", newsReadRespDto));
    }

}
