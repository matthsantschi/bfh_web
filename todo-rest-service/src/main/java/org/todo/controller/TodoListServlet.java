package org.todo.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.todo.model.todo.Todo;
import org.todo.model.todo.TodoList;
import org.todo.model.todo.TodoNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/api/todos/*")
public class TodoListServlet extends HttpServlet {

    private static TodoList globalTodoList = new TodoList();
    private static ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

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
        resp.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(resp.getOutputStream(), globalTodoList.getTodos());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Todo todoFromReq = null;
        try {
            todoFromReq = objectMapper.readValue(req.getReader(), Todo.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Todo todoToResp = null;
        if (todoFromReq != null) {
            todoToResp = globalTodoList.addTodo(todoFromReq);
        }
        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        // return todo with id as success message
        objectMapper.writeValue(resp.getOutputStream(), todoToResp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String todoId = req.getPathInfo().split("/")[1];
        Todo todoFromReq = objectMapper.readValue(req.getReader(), Todo.class);
        if(todoFromReq.getId() !=  Integer.parseInt(todoId)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        try {
            globalTodoList.updateTodo(todoFromReq);
        } catch (TodoNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        resp.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(resp.getOutputStream(), todoFromReq);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] todoId = req.getPathInfo().split("/");
        if(todoId.length < 1 ||  todoId[1] == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            globalTodoList.removeTodo(Integer.parseInt(todoId[1]));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
