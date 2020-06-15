package com.mikes.nolorry.common;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthAspect {

	private final Logger logger = LoggerFactory.getLogger(AuthAspect.class);

	@Pointcut("execution(public * com.mikes.nolorry.controller..*.*(..))")
	public void checkToken() {
	}

	@Around("checkToken()")
	public Object checkTokenBeforeRestInvoke(ProceedingJoinPoint joinPoint) throws Throwable {

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();// 这个RequestContextHolder是Springmvc提供来获得请求的东西
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Annotation excludeAuth = signature.getMethod().getAnnotation(ExcludeAuth.class);

		if (excludeAuth == null) {
			Object obj = request.getSession().getAttribute("token");
			String token = obj != null ? obj.toString() : "";
			String hToken = request.getHeader("token");

			if (!StringUtils.isEmpty(token) && token.equals(hToken)) {
				// 记录下请求内容
				logger.info("################URL : " + request.getRequestURL().toString());
				logger.info("################HTTP_METHOD : " + request.getMethod());
				logger.info("################IP : " + request.getRemoteAddr());
				logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

				// 下面这个getSignature().getDeclaringTypeName()是获取包+类名的
				// 然后后面的joinPoint.getSignature.getName()获取了方法名
				logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
						+ joinPoint.getSignature().getName());
				// logger.info("################TARGET: " +
				// joinPoint.getTarget());//返回的是需要加强的目标类的对象
				// logger.info("################THIS: " +
				// joinPoint.getThis());//返回的是经过加强后的代理类的对象
				return joinPoint.proceed();
			} else {
				BodyBuilder builder = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("message", "unauthorized");
				return builder.body(result);
			}
		} else {
			return joinPoint.proceed();
		}
	}

}
