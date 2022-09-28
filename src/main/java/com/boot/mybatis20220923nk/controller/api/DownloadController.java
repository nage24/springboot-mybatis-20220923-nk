package com.boot.mybatis20220923nk.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequestMapping("/download")
@RestController
public class DownloadController {

    @Value("${file.path}") // 생성될 때 이 값에 얘가 주입이 됨. -> yml 에서 가져와 줄 수 있다는 것 임. ->  yml 에서 모든 경로를 관리해 줄 수 있게 됨.
    private String filePath;





    // 20220928
    // http://localhost:8000/api/news/download?originFileName=%ED%85%8C%EC%8A%A4%ED%8A%B8&tempFileName=6ded(uuidName).jpg
    // http://localhost:8000/download/news?originFileName=%ED%85%8C%EC%8A%A4%ED%8A%B8.jpg&tempFileName=6ded(uuidName).jpg
    @GetMapping("/news")
    public ResponseEntity<?> download(@RequestParam String originFileName, @RequestParam String tempFileName) throws IOException {
        // IOException ; 파일이 없을 경우

        Path path = Paths.get(filePath + "news/" + tempFileName); // 실제 파일 경로 , 경로만 생성해둠.

        String contentType = Files.probeContentType(path); // 파일의 콘텐츠 유형을 조사

        log.info("ContentType: " + contentType); // image/jpeg -> MIME 타입이다 ... MIME 타입이어야지만 다운로드가 가능함.


        ContentDisposition contentDisposition = ContentDisposition.builder("attachment") // attachment 로 잡아주어야 ... 다운로드 가능?
                .filename(originFileName, StandardCharsets.UTF_8)
                .build();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.add(HttpHeaders.CONTENT_TYPE, contentType); // key , value

        // InputStream & OutputStream -> 가져오기(입력) , 내보내기(출력)
        Resource resource = new InputStreamResource(Files.newInputStream(path)); // 외부 데이터 입력 받은걸 가져와서 자바 객체로 만들겠다 ...







        // new CMRespDto<>(1, "게시글 불러오기 성공", null)
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
