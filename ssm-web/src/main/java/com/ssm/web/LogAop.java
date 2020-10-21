package com.ssm.web;

import com.ssm.domain.SysLog;
import com.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysLogService sysLogService;

    private Date visitTime; //开始时间
    private Class clazz; //访问的类
    private Method method; //访问的方法

    // 前置通知
    @Before("execution(* com.ssm.web.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date(); //1.当前时间 == 开始访问时间

        //2.获取访问的类
        clazz = jp.getTarget().getClass();

        //3.获取访问的方法
        String methodName = jp.getSignature().getName(); //获取访问的方法名称
        Object[] args = jp.getArgs(); // 获取访问的方法的参数
        if(args == null || args.length==0){
            method = clazz.getMethod(methodName); //只能获取无参数的方法
        }else{
            Class[] classArgs = new Class[args.length];
            for(int i=0;i<args.length;i++){
                classArgs[i]=args[i].getClass();
            }
            method = clazz.getMethod(methodName,classArgs); //获取有参数的方法
        }
    }

    //后置通知
    @After("execution(* com.ssm.web.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        //4.获取访问时长
        long executionTime = new Date().getTime()-visitTime.getTime();

        //5.获取url（反射+注解）
        String url ="";
        if(clazz != null && method != null && clazz != LogAop.class){ //保证方法和类不为空，不是本方法
            //1.获取类上的 @RequestMapping() 的值
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation != null){
                String[] classValue = classAnnotation.value();
                //2.获取方法上的 @RequestMapping() 的值
                RequestMapping methodAnnotation = (RequestMapping) method.getAnnotation(RequestMapping.class);
                if(methodAnnotation != null){
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0]+methodValue[0];

                    //6.获取访问的ip地址
                    String ip = request.getRemoteAddr();

                    //7.获取当前操作者
                    SecurityContext context = SecurityContextHolder.getContext(); //从上下文获取当前登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //8.将日志相关信息封装到 SysLog对象
                    SysLog sysLog = new SysLog();
                    sysLog.setVisitTime(visitTime);
                    sysLog.setUsername(username);
                    sysLog.setIp(ip);
                    sysLog.setUrl(url);
                    sysLog.setExecutionTime(executionTime);
                    sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());

                    //9.调用 Service完成日志记录操作
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}
