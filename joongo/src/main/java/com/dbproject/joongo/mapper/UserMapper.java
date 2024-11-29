package com.dbproject.joongo.mapper;

import com.dbproject.joongo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    // 기존 메서드들
    void insertUser(User user);

    User selectUserById(int userId);

    List<User> selectAllUsers();

    void updateUser(User user);

    void deleteUserById(int userId);

    User selectUserByEmail(String email);

    // 이메일과 비밀번호로 사용자 조회
    User selectUserByEmailAndPassword(@Param("email") String email, @Param("userPassword") String password);

    @Select("SELECT money FROM USERS WHERE user_id = #{id}")
    Long selectUserMoney(int id);

    @Select("SELECT * FROM TRANSACTIONS WHERE buyer_id = #{id} OR seller_id = #{id}")
    List<String> selectTransactionHistory(int id);
}

