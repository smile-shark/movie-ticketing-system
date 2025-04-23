package com.movie.intercopter;

import com.movie.common.resp.RespCode;
import com.movie.exception.BusinessException;
import com.movie.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class CustomerJwtInterceptor implements HandlerInterceptor {
    private final TokenUtils tokenUtils;
    /**
     * @deprecated 进入Controller接口之前进入的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * - 如果为true 意味着放行 进入接口
     * - 返回false 被拦截下来
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if(token==null || token.isEmpty()){
            throw new BusinessException(RespCode.TOKEN_VERIFY_ERROR);
        }
        try{
            if(!tokenUtils.verifyToken(token)){
                throw new BusinessException(RespCode.TOKEN_VERIFY_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.TOKEN_VERIFY_ERROR);
        }
        System.out.println("验证token成功");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * @deprecated 进入接口正常执行完毕进入的方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * @deprecated 接口进入接口之后执行的方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
