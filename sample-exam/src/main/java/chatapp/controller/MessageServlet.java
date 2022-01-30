package chatapp.controller;

import chatapp.model.ChatMessage;
import chatapp.model.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/api/messages/*"})
public class MessageServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(MessageServlet.class.getName());
	private final ChatService chatService = ChatService.getInstance();
	private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Getting messages");
		String subject = request.getParameter("subject");
		List<ChatMessage> messages = new ArrayList<>();
		if(subject != null) {
			messages = chatService.getMessages(subject);
		} else {
			messages = chatService.getMessages();
		}	
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		objectMapper.writeValue(response.getOutputStream(), messages);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Adding message");
		try {
			// parse and validate chat message
			ChatMessage message = objectMapper.readValue(request.getInputStream(), ChatMessage.class);
			if (message.getId() != null || message.getText() == null || message.getText().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			// add chat message
			chatService.addMessage(message);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setContentType("application/json");
			objectMapper.writeValue(response.getOutputStream(), message);
		} catch (JsonProcessingException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = req.getRequestURL().toString();
		String[] urlSegments = url.split("/");
		int id =  Integer.valueOf(urlSegments[6]);
		if(chatService.deleteMessage(id)) {
			resp.setStatus(HttpServletResponse.SC_OK);
			objectMapper.writeValue(resp.getOutputStream(), "Message " + id +" delted");
		} {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		};
		
	}
}
