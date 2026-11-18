package com.example.webflix.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("currentUser") != null);

        String path = request.getRequestURI();

        // Kullanıcı zaten giriş yapmışsa tekrar giriş/kayıt sayfasına girmesini engelle
        if (isLoggedIn && (path.equals("/giris") || path.equals("/kayit"))) {
            response.sendRedirect("/");
            return false;
        }

        // Giriş yapmamışsa sadece /giris ve /kayit sayfalarına izin ver, diğer tüm istekleri /giris'e yönlendir
        if (!isLoggedIn && !path.equals("/giris") && !path.equals("/kayit")) {
            response.sendRedirect("/giris");
            return false;
        }

        return true;
    }
}