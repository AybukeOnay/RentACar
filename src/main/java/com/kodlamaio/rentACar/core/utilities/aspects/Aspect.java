package com.kodlamaio.rentACar.core.utilities.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

	@Around("execution(* com.kodlamaio.rentACar.business.concretes.BrandManager.deleteById(int))")
	public void beforeLog(ProceedingJoinPoint proceedingJoinPoint) {
		
		try {
			System.out.println("before logging");
			proceedingJoinPoint.proceed(); //--> proceed şu an deleteById metoduna karşılık gelmektedir.
			System.out.println("after returning");
		} catch (Throwable e) {
			System.out.println("after throwing");
			e.printStackTrace();
		}
		System.out.println("after finally");
	}
	
	
	
	
	//--> Tüm yerlerde çalışsın istersek eğer * koymamız gerekir.
//	@Before("execution(* com.kodlamaio.rentACar.business.concretes.BrandManager.deleteById(int))")
//	public void beforeLog(JoinPoint joinPoint) {
//		
//		MethodSignature signature =(MethodSignature) joinPoint.getSignature();
//		System.out.println("before brand manager delete get by id");
//		System.out.println(joinPoint.getArgs()[0]); //--> İşlem yapılan id'yi gösterir
//		System.out.println(signature.getParameterNames()[0]); //--> işlem yaptığımız parametre adını gösterir
//		System.out.println(joinPoint.getSignature()); //--> metodun imzasını getirir.
//	}
	
	

//	@After("pointcut()")
//	public void afterLog() {
//		System.out.println("after brand manager delete get by id");
//	}
//
//	@Pointcut("execution(* com.kodlamaio.rentACar.business.concretes.BrandManager.deleteById(int))")
//	public void pointcut() {} //-->dummy metot
//	
}
