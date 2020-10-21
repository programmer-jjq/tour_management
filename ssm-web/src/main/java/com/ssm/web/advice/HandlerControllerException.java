//package com.ssm.web.advice;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.nio.file.AccessDeniedException;
//
//@Component
//public class HandlerControllerException implements HandlerExceptionResolver {
//
//    // handler：出现异常的对象 ex：出现的异常信息
//    @Override
//    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        ModelAndView mv = new ModelAndView();
//        // 将异常信息放入 request域中，基本不用
//        mv.addObject("errorMsg",ex.getMessage());
//        // 指定不同异常跳转的页面
//        if(ex instanceof AccessDeniedException){
//            mv.setViewName("forward:/403.jsp");
//        }else{
//            mv.setViewName("forward:/500.jsp");
//        }
//        return mv;
//    }
//}
