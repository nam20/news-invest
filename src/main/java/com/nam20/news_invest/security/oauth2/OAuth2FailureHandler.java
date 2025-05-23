package com.nam20.news_invest.security.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // TODO: Define a default failure URL or load it from properties
    private final String failureUrl = "/login?error=oauth2_failure"; // Example default failure URL

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Log the exception or handle it as needed
        logger.error("OAuth2 authentication failed", exception);

        // Determine the redirect URL based on the request or a default
        String targetUrl = UriComponentsBuilder.fromUriString(failureUrl)
                .queryParam("message", exception.getLocalizedMessage())
                .build().toUriString();

        // Redirect to the target URL
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
} 