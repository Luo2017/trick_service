package com.trick.trick_class.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trick.trick_class.utils.JWTUtils;
import com.trick.trick_class.utils.JsonData;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 进入controller之前执行的方法，只有return true时才会进入controller
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //header是报文头
        try {
            //考虑从多个位置获取token
            //我们实际在前端中是放在header里
            String accessToken = request.getHeader("token");
            if (accessToken == null) {
                accessToken = request.getParameter("token");
            }
            if (StringUtils.isNotBlank(accessToken)) {
                Claims claims = JWTUtils.checkJWT(accessToken);
                if (claims == null) {
                    sendJsonMessage(response, JsonData.buildError("登录过期，重新登录"));
                    return false;
                }

                //jwt工具类生成令牌时是加入了id和name的
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");

                //下一步将进入的controller的执行中
                request.setAttribute("user_id", id);
                request.setAttribute("name", name);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendJsonMessage(response, JsonData.buildError("登录过期，重新登录"));
        return false;
    }

    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response,
                                       Object obj){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            //在header的hiden自动生成部分
            response.setContentType("application/json; charset=utf- 8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 执行完controller之后执行的方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
