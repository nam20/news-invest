package com.nam20.news_invest.security.oauth2;

import com.nam20.news_invest.security.JwtGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtGenerator jwtGenerator;
    private final RequestCache requestCache = new HttpSessionRequestCache(); // Use default request cache
    private final OAuth2Properties oauth2Properties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Generate JWT Token for the authenticated OAuth2 user
        String token = jwtGenerator.generateToken(authentication);

        // Add JWT Token to an HTTP-only cookie
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/"); // Set path to root so it's available everywhere
        jwtCookie.setMaxAge(24 * 60 * 60); // Set expiry (e.g., 1 day)

        response.addCookie(jwtCookie);

        // Determine the redirect URL
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        // Redirect to the determined URL
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        // Clear the saved request after successful authentication
        clearAuthenticationAttributes(request);
    }

    @Override // Override the method from SimpleUrlAuthenticationSuccessHandler
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1. Try to retrieve the originally requested URL from RequestCache
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            // Clear the saved request from cache to prevent redirection loops
            requestCache.removeRequest(request, response);
            return savedRequest.getRedirectUrl();
        }

        // 2. If no saved request, check for a 'redirect_uri' parameter in the current request
        String redirectUri = request.getParameter("redirect_uri");

        // Validate the redirect URI
        if (redirectUri != null && !redirectUri.isEmpty() && isAuthorizedRedirectUri(redirectUri)) {
             return redirectUri;
        }

        // 3. Fallback to default target URL if neither is present or redirect_uri is invalid
        setDefaultTargetUrl("/"); // Ensure default is set (though it might be already)
        return super.determineTargetUrl(request, response, authentication); // Use superclass logic which applies default
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        try {
            URI clientRedirectUri = new URI(uri);
            return oauth2Properties.getAuthorizedRedirectUris().stream().anyMatch(authorizedUri -> {
                URI authorizedClientUri = URI.create(authorizedUri);
                // Simple check: scheme, host, and port must match
                // More complex validation might be needed depending on requirements
                return authorizedClientUri.getScheme().equalsIgnoreCase(clientRedirectUri.getScheme())
                        && authorizedClientUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                        && authorizedClientUri.getPort() == clientRedirectUri.getPort();
            });
        } catch (Exception e) {
            // Log invalid URI format
            logger.error("Invalid redirect URI format: " + uri, e);
            return false;
        }
    }
}