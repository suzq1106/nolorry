package com.mikes.nolorry.common;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mikes.nolorry.model.basic.SystemLog;
import com.mikes.nolorry.service.basic.ISystemLogService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

@Aspect
@Component
public class LogAspect {

	private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Resource(name = "SystemLogService")
	private ISystemLogService systemLogService;

	@Pointcut("execution(public * com.mikes.nolorry.controller..*.*(..))")
	public void logPointCut() {
	}

	@After(value = "logPointCut()")
	public void after(JoinPoint joinPoint) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();// 这个RequestContextHolder是Springmvc提供来获得请求的东西
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		Object account = request.getSession().getAttribute("account");

		if (account != null) {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();

			BasicLog basicLog = signature.getMethod().getAnnotation(BasicLog.class);
			if (basicLog != null) {
				logger.info("wwww action={}", basicLog.action());

				UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
				Browser browser = userAgent.getBrowser();
				// OperatingSystem operatingSystem =
				// userAgent.getOperatingSystem();
				// Version browserVersion = userAgent.getBrowserVersion();
				String addr = request.getRemoteAddr();

				SystemLog systemLog = SystemLog.builder().optTime(new Date()).optType(basicLog.optType().value())
						.addr(addr).account(String.valueOf(request.getSession().getAttribute("account")))
						.accName(String.valueOf(request.getSession().getAttribute("accName"))).tmnl(browser.getName())
						.action(basicLog.action()).actionDesc(basicLog.actionDesc()).build();

				if (basicLog.optType() == OptType.LOGOUT) {
					request.getSession().removeAttribute("account");
					request.getSession().removeAttribute("accName");
				}

				systemLogService.create(systemLog);
			}
		}

	}
}
