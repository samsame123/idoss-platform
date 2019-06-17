package com.idoss.web.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * Created by xiaoxuwang on 2019/6/14.
 */
public abstract class AbstractAspect {

    protected Gson gson;

    protected static final LocalVariableTableParameterNameDiscoverer varible = new LocalVariableTableParameterNameDiscoverer();

    protected Method getMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method;
    }

    protected String[] getParameterNames(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        String[] parameterNames = varible.getParameterNames(method);
        return parameterNames;
    }

    protected String getParamLog(String[] parameterNames, Object[] args) {
        return this.getParamLog(parameterNames, args, ";");
    }

    protected String getParamLog(String[] parameterNames, Object[] args, String paramsSplit) {
        if ((parameterNames != null) && (args != null) && (parameterNames.length == args.length)) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                String json;
                try {
                    json = gson.toJson(args[i]);
                } catch (Error e) {
                    json = args[i].toString();
                }
                String value = (args[i] == null) ? null : json;
                buffer.append("param").append(i).append(" ").append(parameterNames[i]).append(":").append(value).append(paramsSplit);
            }
            return buffer.toString();
        }
        return null;
    }

}
