package com.boot.mybatis20220923nk.repository;

import com.boot.mybatis20220923nk.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    public int save(User user);

}
