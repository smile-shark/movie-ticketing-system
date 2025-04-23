package com.movie.common.resp;

public enum RespCode {
    SUCCESS(200, "成功"),
    SEND_EMAIL_SUCCESS(200, "验证码发送成功"),
    REGISTER_USER_SUCCESS(200, "注册成功"),
    LOGIN_USER_SUCCESS(200, "登录成功"),
    UPLOAD_FILE_SUCCESS(200,"上传文件成功" ),
    ERROR(500, "失败"),
    DATABASE_ERROR(500, "数据库连接失败"),
    PARAM_ERROR(5001, "参数错误"),
    SEND_EMAIL_ERROR(5002, "验证码发送失败"),
    USER_ALREADY_EXISTS(5003, "用户已存在"),
    DECRYPT_ERROR(5004, "解密失败"),
    EMAIL_VERIFY_CODE_LOSE_EFFICACY(5005, "邮件验证码失效"),
    EMAIL_VERIFICATION_CODE_INPUT_ERROR(5006, "邮箱验证码输入错误"),
    NOT_FOUND_USER(5007, "未找到用户"),
    PASSWORD_ERROR(5008, "密码错误"),
    UPLOAD_FILE_ERROR(5009, "上传文件失败"),
    FIND_SUCCESS(200, "查询成功"),
    DELETE_FILE_ERROR(5010,"删除文件失败" ),
    DELETE_FILE_SUCCESS(200, "删除文件成功"),
    ADD_MOVIE_ERROR(5011, "添加电影失败"),
    ADD_MOVIE_SUCCESS(200, "添加电影成功"),
    FIND_ERROR(5012, "查询失败"),
    UPDATE_USER_INFO_ERROR(5013, "用户信息修改失败"),
    UPDATE_USER_INFO_SUCCESS(200, "用户信息修改成功"),
    UPDATE_MOVIE_ERROR(5014, "电影信息修改失败"),
    UPDATE_MOVIE_SUCCESS(200, "电影信息修改成功"),
    ADD_MOVIE_TYPE_SUCCESS(200, "添加电影类型成功"),
    ADD_MOVIE_TYPE_ERROR(5015, "添加电影类型失败"),
    DELETE_MOVIE_TYPE_SUCCESS(200, "删除电影类型成功"),
    DELETE_MOVIE_TYPE_ERROR(5016, "删除电影类型失败"),
    ADD_SUCCESS(200, "添加成功"),
    ADD_ERROR(5017, "添加失败"),
    UPDATE_SUCCESS(200, "修改成功"),
    UPDATE_ERROR(5018, "修改失败"),
    DELETE_SUCCESS(200, "删除成功"),
    DELETE_ERROR(5019, "删除失败"),
    TOKEN_VERIFY_ERROR(401, "token验证失败");

    private final int code;
    private final String message;

    RespCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
