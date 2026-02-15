package com.ecommerce.auth_service.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;

public class CookieService {

    private final String refreshTokenCookieName;
    private final boolean cookieHttpOnly;
    private final boolean cookieSecure;
    private final String cookieDomain;
    private final String cookieSameSite;

    public CookieService(
        @Value("${security.jwt.refresh-token-cookie-name}") String refreshTokenCookieName,
        @Value("${security.jwt.cookie-http-only}") boolean cookieHttpOnly,
        @Value("${security.jwt.cookie-secure}") boolean cookieSecure,
        @Value("${security.jwt.cookie-domain}") String cookieDomain,
        @Value("${security.jwt.cookie-same-site}") String cookieSameSite
    ) {
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.cookieHttpOnly = cookieHttpOnly;
        this.cookieSecure = cookieSecure;
        this.cookieDomain = cookieDomain;
        this.cookieSameSite = cookieSameSite;
    }

    public void attachRefreshCookie(HttpServletResponse response , String refreshToken , int maxAge) {

        ResponseCookie responseCookie = ResponseCookie.from(refreshTokenCookieName , refreshToken)
                .httpOnly(cookieHttpOnly)
                .maxAge(maxAge)
                .domain(cookieDomain)
                .path("/")
                .sameSite(cookieSameSite)
                .secure(cookieSecure)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE , responseCookie.toString());
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {

        ResponseCookie responseCookie = ResponseCookie.from(refreshTokenCookieName , "")
                .httpOnly(cookieHttpOnly)
                .maxAge(0)
                .domain(cookieDomain)
                .path("/")
                .sameSite(cookieSameSite)
                .secure(cookieSecure)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE , responseCookie.toString());
    }

    public void addNoStoreHeaders(HttpServletResponse response) {
        // HTTP 1.1: Standard for modern browsers
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");

        // HTTP 1.0: For older proxies/browsers
        response.setHeader("Pragma", "no-cache");

        // Proxies: Tells them the content is already expired
        response.setHeader("Expires", "0");
    }
}
