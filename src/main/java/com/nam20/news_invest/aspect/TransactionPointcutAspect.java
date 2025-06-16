package com.nam20.news_invest.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// 서비스 레이어 트랜잭션 AOP 포인트컷 정의
@Aspect
@Component
public class TransactionPointcutAspect {

    // 서비스 레이어의 모든 public 메서드를 포인트컷으로 지정
    @Pointcut("execution(public * com.nam20.news_invest.service..*(..))")
    public void servicePublicMethods() {
        // 포인트컷 시그니처용 빈 메서드
    }
} 