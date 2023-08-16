package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public MessageService(MessageDAO messageDAO, AccountService accountService) {
        this.messageDAO = messageDAO;
        this.accountService = accountService;
    }

    public boolean isValidMessage(String messageText) {
        int messageLength = messageText.length();

        if (messageLength < 1 || messageLength >= 255) {
            return false;
        }
        return true;
    }

    public boolean isExistingMessageId(int messageId) {
        Message existingMessageId = getMessageById(messageId);

        if (existingMessageId == null) {
            return false;
        }
        return true;
    }

    public Message addMessage(Message message) {
        String messageText = message.getMessage_text();
        boolean validMessage = isValidMessage(messageText);
        int accountId = message.getPosted_by();
        boolean existingUser = accountService.isExistingUser(accountId);

        if (validMessage && existingUser) {
            return messageDAO.addMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessageById(int messageId, String messageText) {
        boolean existingMessageId = isExistingMessageId(messageId);
        boolean validMessage = isValidMessage(messageText);

        if (validMessage && existingMessageId) {
            return messageDAO.updateMessageById(messageId, messageText);
        }
        return null;
    }

    public List<Message> getAllMessagesFromUser(int accountId) {
        return messageDAO.getAllMessagesFromUser(accountId);
    }
}
