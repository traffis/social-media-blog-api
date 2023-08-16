package Service;

import Model.Message;
import DAO.MessageDAO;
import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public boolean messageCheck(String messageText) {
        int messageLength = messageText.length();

        if (messageLength < 1 || messageLength >= 255) {
            return false;
        }
        return true;
    }

    public Message addMessage(Message message) {
        String messageText = message.getMessage_text();
        int posterID = message.getPosted_by();
        Account existingUser = accountDAO.getAccountByID(posterID);

        if (messageCheck(messageText) && existingUser != null) {
            return messageDAO.addMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int messageID) {
        return messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessageByID(int messageID) {
        return messageDAO.deleteMessageByID(messageID);
    }

    public Message updateMessageByID(int messageID, String messageText) {
        Message existingMessageID = getMessageByID(messageID);

        if (messageCheck(messageText) && existingMessageID != null) {
            return messageDAO.updateMessageByID(messageID, messageText);
        }
        return null;
    }

    public List<Message> getAllMessagesFromUser(int userID) {
        return messageDAO.getAllMessagesFromUser(userID);
    }
}
