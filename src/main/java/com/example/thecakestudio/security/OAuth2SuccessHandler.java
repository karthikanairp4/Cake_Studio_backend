package com.example.thecakestudio.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

    	OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

    	String email = oauthUser.getAttribute("email");
    	String name = oauthUser.getAttribute("name");

    	User user = userService.processGoogleUser(email, name);
    	String token = jwtUtil.generateToken(user);

    	String redirectUrl =
    	        "https://paprika-bakes.netlify.app/oauth-success"
    	        + "?token=" + token
    	        + "&userId=" + user.getId()
    	        + "&email=" + URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8);

    	response.sendRedirect(redirectUrl);
    }
}
