package com.github.hippoom.runner.challenges.http.support;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

        @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
               && parameter.getParameterType().equals(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        // TODO: Replace with real session-based authentication
        // For now, return a mock UserId for testing isolation
        // In real implementation, this would extract UserId from:
        // - HTTP Session
        // - JWT token
        // - X-Session-Token header
        // - Security context

        return UserId.generate(); // Mock implementation
    }
}
