package com.zyx.miaosha.dao;

import com.zyx.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author:zhangyx
 * @Date:Created in 19:442018/11/14
 * @Modified By:
 */
@Mapper
public interface MiaoshaUserDao {
    @Select("SELECT * FROM miaosha_user where id=#{id}")
    public MiaoshaUser getById(@Param("id") Long id);
   //更新密码
    @Update("update miaosha_user set password =#{password} where id=#{id}")
    void updateUser(MiaoshaUser toNewUser);
}
