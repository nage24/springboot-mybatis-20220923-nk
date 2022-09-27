package com.boot.mybatis20220923nk.controller.api;

import com.boot.mybatis20220923nk.domain.News;
import com.boot.mybatis20220923nk.domain.NewsFile;
import com.boot.mybatis20220923nk.dto.CMRespDto;
import com.boot.mybatis20220923nk.dto.NewsReadRespDto;
import com.boot.mybatis20220923nk.dto.NewsWriteReqDto;
import com.boot.mybatis20220923nk.dto.NewsWriteRespDto;
import com.boot.mybatis20220923nk.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class NewsController {

    // File
    @Value("${file.path}") // NewsController 가 생성될 때 이 값에 얘가 주입이 됨. -> yml 에서 가져와 줄 수 있다는 것 임. ->  yml 에서 모든 경로를 관리해 줄 수 있게 됨.
    private String filePath;    // private String filePath = "파일 경로"; 와 다름. 얘는 자리 차지하니 생성될 때 값을 넣겠다 이말 ..

    private final NewsRepository newsRepository;

    @PostMapping("/news")
    public ResponseEntity<?> write(NewsWriteReqDto newsWriteReqDto) {

        log.info("{}", newsWriteReqDto);

        // newsFile 리스트를 생성해서 news_id 값으로 DB insert?
        List<NewsFile> newsFileList = null;

        // 클라이언트에서 파일 받아오기 : -> DB에 File 업로드
        MultipartFile firstFile = newsWriteReqDto.getFiles().get(0);
        String firstFileName = firstFile.getOriginalFilename();

        // 비어 있어도 -> MultipartFile 객체는 들어옴. 그러니까 이름을 보고,
        // File 이름이 비어 있지 않으면 반복문을 실행하여 , newsWriteReqDto 에서 파일 리스트들을 가져와서 파일명 출력...
        if(!firstFileName.isBlank()) {

            log.info("파일 입출력을 합니다.");

            newsFileList = new ArrayList<NewsFile>(); // 파일 입출력 할 때만 생성이 됨.

            for(MultipartFile file: newsWriteReqDto.getFiles()) {
                // @Value 에 파일경로를 가지고 오기
                String originFileName = file.getOriginalFilename();

                // 유일한 이름 생성
                String uuid = UUID.randomUUID().toString();
                String extension = originFileName.substring(originFileName.lastIndexOf(".")); // 확장자명
                String tempFileName = uuid + extension;

                Path uploadPath = Paths.get(filePath, "news/" + tempFileName);

                log.info("파일 업로드 경로: {}", uploadPath.toString());

                // Path 를 잡아주는 걸로는 파일이 만들어지지 않음 ! 파일 객체를 생성해둔 곳에 덮어씌우기 할 것.
                // 단, 이름이 겹치면 같은 파일에 덮어쓰기가 되겠지유 ,, -> UUID 로 이름을 바꾸어 유일한 이름으로 만들어 줄거에요.
                File f = new File(filePath + "news");

                if(!f.exists()) { // 경로가 존재하지 않으면,
                        f.mkdirs(); // : make directories 로 없는 경로를 만들어 줄 수 있다.
                }


                // 폴더 경로를 생성, 빈 파일 만들어 주었으니 Bytes 데이터 덮어쓰기 ...
                try {
                    Files.write(uploadPath, file.getBytes());
                } catch (IOException e) { // IO : input , output 파일 입출력에 있어서 파일이 없을 경우의 예외가 발생할 수 있기 때문에 try catch 로 감싸준다.
                    throw new RuntimeException(e);
                }
                // NoSuchFileException upload/news/ ... 를 찾을 수가 없다.
                // <파일 다운로드> 는 우리 PC에 완전히 비어있고 이름만을 가진 자리가 하나 할당됨.
                // 그 경로에 해당 파일 명으로 비어있는 파일을 하나 만들어두어야 ...
                // 그 빈 파일에 Bytes 데이터가 씌워지는 것임 ... ! 충격실화


                // newsFileList 에 파일 넣어줌 차곡차곡~
                NewsFile newsFile = NewsFile.builder()
                        .file_origin_name(originFileName)
                        .file_temp_name(tempFileName)
                        .build();

                newsFileList.add(newsFile);

            }
        }else {

            log.info("업로드할 파일이 없었어요. ");
        }
        // News 를 DB insert 해주고, Auto increment 된 news_id 를 가져와서 해당 news_id로 fileList 업로드 할 것임.



        News news = newsWriteReqDto.toEntity("짱아");
        // 파라미터로 주던지, setter 로 writer 설정 해주어야 함. (Not Null)
        // news.setNews_writer("짱구");

        int result = newsRepository.save(news); // 이 때 auto increment 된 news_id 가져와지는 것임.
        
        if(result == 0) {
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "작성 실패", news));
        }

        // news 가 insert 가 되었을 때 !
        if(newsFileList != null) {      // 업로드 파일이 존재하면 , (null 이면 무시되는 구문)
            for(NewsFile newsFile : newsFileList) {         // 리스트에 있는 파일들 전부 올려야 겠죠? 그 전에,
                newsFile.setNews_id(news.getNews_id());     // Auto increment 된 news id 가져와서 그 news id 로 잡아주어야 함.
                log.info("News file 객체: {}", newsFile);
            }

            newsRepository.saveFiles(newsFileList);

            if(result != newsFileList.size()) {
                return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "파일 업로드 실패", newsFileList));
            }
        }

        NewsWriteRespDto newsWriteRespDto = news.toNewsWriteRespDto(newsFileList);

        return ResponseEntity.ok(new CMRespDto<>(1, "새 글 작성 완료", newsWriteRespDto));
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<?>  read(@PathVariable int newsId) {

        log.info("{}", newsId);

       News news = newsRepository.getNews(newsId);

        NewsReadRespDto newsReadRespDto = news.toNewsReadRespDto();

        return ResponseEntity.ok(new CMRespDto<>(1, "게시글 불러오기 성공", newsReadRespDto));
    }

}
