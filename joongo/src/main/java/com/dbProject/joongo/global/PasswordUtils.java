package com.dbProject.joongo.global;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // 비밀번호 해싱
    public static String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    // 비밀번호 비교
    public static boolean matchPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
