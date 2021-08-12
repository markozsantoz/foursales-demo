package br.com.foursales.demo.config.security.filter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Objects;

import static br.com.foursales.demo.config.security.manager.TokenManager.generateToken;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Log4j2
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        var header = request.getHeader(AUTHORIZATION);
        if (nonNull(header) && header.startsWith("Basic ")) {
            log.info("Authenticating by Basic Auth");
            var credentials = getUserBasicAuthentication(header);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]));
        }
        log.info("Authentication credentials not found");
        throw new AuthenticationCredentialsNotFoundException("Authentication credentials not found");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        log.info("Authenticated successfully. Generating Bearer Token");
        response.addHeader(AUTHORIZATION, generateToken(((User) authResult.getPrincipal()).getUsername()));
    }

    public static String[] getUserBasicAuthentication(String authorizationHeader) {

        var split64 = authorizationHeader.split("\\s");
        byte[] decoded64Array;
        try {
            decoded64Array = Base64.getDecoder().decode(split64[1].getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode basic authentication");
        }
        var decoded64String = new String(decoded64Array);
        var split = decoded64String.split(":");
        if (split.length == 2)
            return split;
        return new String[]{"", ""};
    }
}
