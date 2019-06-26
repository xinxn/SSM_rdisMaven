package com.yingjun.ssm.aop;

import com.yingjun.ssm.dto.BaseResult;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author yingjun
 *
 * 采用AOP的方式处理参数问题。
 */
@Component
@Aspect
public class BindingResultAop {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.yingjun.ssm.web.*.*(..))")
    public void aopMethod(){}

    /**
     * 环绕通知，放在方法头上，这个方法要决定真实的方法是否执行，而且必须有返回值
     */
    @Around("aopMethod()")
    public Object  around(ProceedingJoinPoint joinPoint) throws Throwable{
        LOG.info(" ①  before method invoking!");
        BindingResult bindingResult = null;
        for(Object arg:joinPoint.getArgs()){
            if(arg instanceof BindingResult){
                bindingResult = (BindingResult) arg;
            }
        }
        if(bindingResult != null){
            if(bindingResult.hasErrors()){
                String errorInfo="["+bindingResult.getFieldError().getField()+"]"+bindingResult.getFieldError().getDefaultMessage();
                return new BaseResult<Object>(false, errorInfo);
            }
        }
        return joinPoint.proceed();
    }
    
    
    @Before("aopMethod()")  
    public void before(JoinPoint jp) {  
        String className = jp.getThis().toString();  
        String methodName = jp.getSignature().getName(); // 获得方法名  
        LOG.info(" ② 位于：" + className + "调用" + methodName + "()方法-开始！");  
        Object[] args = jp.getArgs(); // 获得参数列表  
        if (args.length <= 0) {  
        	LOG.info("====" + methodName + "方法没有参数");  
        } else {  
            for (int i = 0; i < args.length; i++) {  
            	LOG.info("====参数  " + (i + 1) + "：" + args[i]);  
            }  
        }  
        LOG.info("=====================================");  
    }  
    
    @AfterReturning(pointcut = "aopMethod()")  
    public void simpleAdvice() {  
        LOG.info(" ⑤ AOP后处理成功 ");  
    } 
    
    @AfterThrowing("aopMethod()")  
    public void catchInfo() {  
    	LOG.info(" ④ 异常信息");  
    }  
  
    @After("aopMethod()")  
    public void after(JoinPoint jp) {  
    	LOG.info(" ③ " + jp.getSignature().getName() + "()方法-结束！");  
        LOG.info("=====================================");  
    }  
}
