package org.todo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.todo.model.todo.Todo;
import org.todo.model.todo.TodoList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/api")
public class TodoListServlet extends HttpServlet {

    private static TodoList globalTodoList = new TodoList();

    @Override
    public void init() throws ServletException {
        Todo todo1 = new Todo("Todo1", "Cat1", LocalDate.now());
        Todo todo2 = new Todo("Todo2", "Cat1", LocalDate.now());
        Todo todo3 = new Todo("Todo3", "Cat2", LocalDate.now());
        globalTodoList.addTodo(todo1);
        globalTodoList.addTodo(todo2);
        globalTodoList.addTodo(todo3);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();
        String json = objectMapper.writeValueAsString(globalTodoList.getTodos());
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.print(json);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPut(req, resp);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doDelete(req, resp);
    }
    
}
