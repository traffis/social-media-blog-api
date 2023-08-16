package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if (addedAccount != null) {
            context.json(mapper.writeValueAsString(addedAccount));
        } else {
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifiedLogin = accountService.verifyLogin(account);

        if (verifiedLogin != null) {
            context.json(mapper.writeValueAsString(verifiedLogin));
        } else {
            context.status(401);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage != null) {
            context.json(mapper.writeValueAsString(addedMessage));
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    private void getMessageByIDHandler(Context context) {
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(messageID);

        if (message != null) {
            context.json(message);
        }
    }

    private void deleteMessageByIDHandler(Context context) {
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByID(messageID);

        if (deletedMessage != null) {
            context.json(deletedMessage);
        }
    }

    private void updateMessageByIDHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        String messageText = message.getMessage_text();
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageByID(messageID, messageText);

        if (updatedMessage != null) {
            context.json(updatedMessage);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesFromUserHandler(Context context) {
        int userID = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getAllMessagesFromUser(userID));
    }
}