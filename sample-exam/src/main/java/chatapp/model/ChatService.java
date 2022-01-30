package chatapp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.security.auth.Subject;

public class ChatService {

	private static final ChatService instance = new ChatService();

	private final List<ChatMessage> messages = new ArrayList<>();
	private int lastId = 0;

	public static ChatService getInstance() {
		return instance;
	}

	private ChatService() {
		try (Scanner scanner = new Scanner(ChatService.class.getResourceAsStream("/messages.txt"))) {
			while (scanner.hasNextLine()) {
				String[] tokens = scanner.nextLine().split(":");
				addMessage(new ChatMessage(tokens[0], tokens[1]));
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public List<ChatMessage> getMessages(String subject) {
		List<ChatMessage> response = new ArrayList<>();
		for (ChatMessage chatMessage : messages) {
			if(chatMessage.getSubject().equals(subject)) {
				response.add(chatMessage);
			}
		}
		return response;
	}


	public Set<String> getSubjects() {
		Set<String> response = new HashSet<>();
		messages.stream().forEach((m) -> {response.add(m.getSubject());});
		return response;
	}

	public void addMessage(ChatMessage message) {
		message.setId(++lastId);
		messages.add(message);
	}

	public boolean deleteMessage(int id) {
		for (ChatMessage chatMessage : messages) {
			if(chatMessage.getId() == id) {
				return messages.remove(chatMessage);
			}
		}
		return false;
	}
}
