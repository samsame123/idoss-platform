package com.idoss.web.aspect;

import com.google.gson.Gson;
import com.idoss.api.exception.BizException;
import com.idoss.api.exception.GlobalErrorCode;
import com.idoss.web.model.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by xiaoxuwang on 2019/6/14.
 */
@Component
@Aspect
@Slf4j
public class ControllerErrorHandler extends AbstractAspect{

    @Autowired
    private Gson gson;

    @Pointcut("execution(* com.idoss.web.controller..*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void controllerMethodPointCut() {}

    @Around("controllerMethodPointCut()")
    public Object interceptor(ProceedingJoinPoint pjp) {
        Method method = getMethod(pjp);
        try {
            return pjp.proceed();
        } catch (BizException be) {
            return exceptionDeal(method, be, be);
        } catch (Throwable t) {
            log.error(GlobalErrorCode.UNKNOWN.getMessage(), t);
            return exceptionDeal(method, new BizException(GlobalErrorCode.UNKNOWN), t);
        }
    }

    /**
     @AfterReturning(value = "controllerMethodPointCut()", returning = "rt")
     public void interceptor(JoinPoint jp, Object rt) {

     }

     @AfterThrowing(value = "controllerMethodPointCut()", throwing = "ex")
     public void interceptor(JoinPoint jp, Throwable ex) {

     }
     */

    private JsonResult exceptionDeal(Method m, BizException e, Throwable t) {
        return JsonResult.fail(e.getCode(), e.getMessage());
    }

}
