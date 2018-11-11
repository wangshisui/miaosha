package com.zyx.miaosha.dao;

import com.zyx.miaosha.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:zhangyx
 * @Date:Created in 20:132018/11/11
 * @Modified By:
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id}")
    public User getById(@Param("id") Integer id);
}
