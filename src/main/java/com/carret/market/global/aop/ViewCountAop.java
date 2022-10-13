package com.carret.market.global.aop;

import com.carret.market.application.item.ItemService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@RequiredArgsConstructor
public class ViewCountAop {

    private final ItemService itemService;

    @Pointcut("execution(* com.carret.market.web.item.ItemController.pageForm(..))")
    public void getViewCountMethod() {
    }

    @Around("getViewCountMethod()")
    public Object preItemView(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = null;

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            itemService.viewCountIncrease(Long.valueOf(request.getParameter("itemId")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return proceedingJoinPoint.proceed();
    }
}
