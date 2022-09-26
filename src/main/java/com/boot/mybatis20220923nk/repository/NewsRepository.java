package com.boot.mybatis20220923nk.repository;

import com.boot.mybatis20220923nk.domain.News;
import org.apache.ibatis.annotations.Mapper;

@Mapper // myBatis 와 연결 해서
// 얘가 스프링 IoC 으로 구현된 xml 을 가지고
// 자동으로 @서비스, @레포지토리 등등을 알아서 처리해주는 것
public interface NewsRepository {

    public int save(News news);

    public News getNews(int news_id);

}
