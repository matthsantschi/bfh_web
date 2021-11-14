package org.todo.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.todo.model.user.InvalidCredentialsException;
import org.todo.model.user.User;
import org.todo.model.user.UserAdmin;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationFilter
 */
@WebFilter(urlPatterns = "/api/todos/*")
public class AuthenticationFilter extends HttpFilter  {

    private UserAdmin userAdmin = UserAdmin.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = null;
        try {
            String[] credentials = getCredentials(req);
            user = validateCredentials(credentials);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        req.setAttribute("user", user);
        chain.doFilter(req, res);
    }

    private String[] getCredentials(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        String[] tokens = authHeader.split(" ");
        if (!tokens[0].equals("Basic"))
            throw new Exception();
        byte[] decoded = Base64.getDecoder().decode(tokens[1]);
        return new String(decoded, StandardCharsets.UTF_8).split(":");
    }

    private User validateCredentials(String[] credentials) throws InvalidCredentialsException {
        int posOfUserName = 0;
        int posOfPassword = 1;
        return userAdmin.loginUser(credentials[posOfUserName], credentials[posOfPassword]);
    }

}