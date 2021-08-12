package br.com.foursales.demo.config.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static br.com.foursales.demo.config.security.manager.TokenManager.TOKEN_PREFIX;
import static br.com.foursales.demo.config.security.manager.TokenManager.parseToken;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Log4j2
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        var header = request.getHeader(AUTHORIZATION);

        if (isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        log.info("Authenticating by Bearer Token");
        var authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated successfully.");
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        try {
            var username = parseToken(request.getHeader(AUTHORIZATION));
            return new UsernamePasswordAuthenticationToken(username, null, List.of());
        } catch (ExpiredJwtException e) {
            throw new AccessDeniedException("Expired token");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new AccessDeniedException("Unsupported token");
        } catch (Exception e) {
            throw new AccessDeniedException("User authorization not resolved");
        }
    }
}
