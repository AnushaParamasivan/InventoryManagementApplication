package com.example.retailInventory.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)*/
public class AccessDeniedHandlerAspect {

	//ToDo: The around invoke is not getting invoked. To be fixed.
	
	
	/*
	@Pointcut("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
	public void preAuthorizeMethods() {}
	
	
	@Around("preAuthorizeMethods()")
	public ResponseEntity<Object> accessDeniedHanler(ProceedingJoinPoint joinPoint) throws Throwable{
		try {
			Object result = joinPoint.proceed();
			return ResponseEntity.ok(result);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied due to insufficient permission.");
		}
		
	}*/
}
