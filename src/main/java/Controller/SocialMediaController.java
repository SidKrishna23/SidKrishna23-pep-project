package Controller;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;



public class SocialMediaController {
    
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessagesHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.get("/messages/{message_id}", this::getMessageHandlerById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesHandlerByAccountID);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts", this::getAllAccountsHandler);
        app.post("/accounts", this::postAccountHandler);
        app.get("/accounts/available", this::getAvailableMessagesHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/register", this::postRegisterHandler);
        return app;
    }

    
    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount!=null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else{
            ctx.status(400);
        }
    }

    public void getAllAccountsHandler(Context ctx) {
        List<Account> accounts = accountService.getAccounts();
        ctx.json(accounts);
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        System.out.println("postMessageHandler: message " + message.toString());
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage!=null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else{
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.delMessage(message_id);
        if(deletedMessage == null){
            ctx.status(200);
        }else{
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }

    }
    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage == null){
            ctx.status(400);
        } else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }

    private void getMessageHandlerById(Context ctx) throws JsonProcessingException {
     
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByID(message_id);
        if (message != null) {
            ctx.json(message);
        }
    }

    private void getAllMessagesHandlerByAccountID(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountID(account_id);
        ctx.json(messages);            
    }
    
    private void getAvailableMessagesHandler(Context context) {
        context.json(messageService.getAllAvailableMessages());
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account validAccount = accountService.validAccount(account);
        if (validAccount != null) {
            ctx.json(mapper.writeValueAsString(validAccount));
        } else {
            ctx.status(401);
        }
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registerAccount = accountService.registerAccount(account);
        
        if (registerAccount != null) {
            ctx.json(mapper.writeValueAsString(registerAccount));
        } else {
            ctx.status(400);
        }
    }
    
}