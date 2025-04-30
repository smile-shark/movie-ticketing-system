package com.movie.utils;

import com.alibaba.fastjson.JSONObject;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SendErrorResponseUtil {
    public static void sendErrorResponse(HttpServletResponse response, RespCode respCode)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(JSONObject.toJSONString(
                Result.error(respCode)
        ));
    }
}
