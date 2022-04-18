package com.vicras.abaclib.use.security;

import static java.util.Objects.isNull;

import com.vicras.abaclib.use.model.CommonUser;
import com.vicras.abaclib.use.security.service.JWTService;
import com.vicras.abaclib.use.service.UserService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends GenericFilterBean {

    private static final String AUTH_TOKEN = "Authorization";
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String token = getToken((HttpServletRequest) request);

        if (isNull(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Long userId = jwtService.getUserIdIfTokenValid(token);

            CommonUser user = userService.findUserById(userId);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            user.getLogin(), "", null
                    ));
        } catch (Exception ignore) {
        }

        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        final String header = request.getHeader(AUTH_TOKEN);
        try {
            return header.split(" ")[1];
        } catch (Exception ignore) {
        }
        return null;
    }
}