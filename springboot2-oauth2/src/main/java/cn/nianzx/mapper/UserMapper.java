package cn.nianzx.mapper;

import cn.nianzx.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
    User getUserList(@Param("userCode") String userCode);
}