package org.todo.controller;


import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.todo.model.user.User;
import org.todo.model.user.UserAdmin;
import org.todo.model.user.UserAlreadyExistsException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/api/users")
public class UserAdminServlet extends HttpServlet {

    private UserAdmin userAdmin = UserAdmin.getInstance();
    private ObjectMapper objectMapper;

 
    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = ObjectMapperFactory.createObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = objectMapper.readValue(req.getReader(), User.class);
        try {
            // we need to create a new User() instance if we use the objectMapper the User has no TodoList instance. 
            userAdmin.registerUser(user.getName(), user.getPassword());
        } catch (UserAlreadyExistsException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        resp.addIntHeader("User Created", HttpServletResponse.SC_CREATED);
    }
    

}
