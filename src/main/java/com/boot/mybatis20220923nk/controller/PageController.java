package com.boot.mybatis20220923nk.controller;

import com.boot.mybatis20220923nk.controller.api.NewsController;
import com.boot.mybatis20220923nk.domain.News;
import com.boot.mybatis20220923nk.repository.NewsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private NewsRepository newsRepository;

    @GetMapping("/news/newpost")
    public String loadWriteNews() {
        return "news/write";
    }

    @GetMapping("/news/{id}")
    public String loadReadNews(@PathVariable int id) {
        return "/news/read";
    }

    @GetMapping("/auth/signup")
    public String loadAuthSignup() {
        return "auth/signup";
    }

}
