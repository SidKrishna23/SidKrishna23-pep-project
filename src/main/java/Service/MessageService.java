package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;


public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
   
    public Message addMessage(Message message) {
       if (message.getMessage_text().isEmpty() == true ||
            messageDAO.getMessageByID(message.getMessage_id()) != null) {
            return null;
       }
        return messageDAO.insertMessage(message);
    }
    
    public List<Message> getAllAvailableMessages() {
        List<Message> allMessages = messageDAO.getAllMessages();

        return allMessages;
    }

    public Message delMessage(int id) {
        Message m = messageDAO.getMessageByID(id);
        if ( m == null) {
            return null;            
        }
        messageDAO.deleteMessage(id);
        return m;
    } 
    public Message updateMessage(int message_id, Message message) {
        Message m = messageDAO.getMessageByID(message_id);
        if ( m == null) {
            return null;            
        }
        return messageDAO.updateMessage(message_id, message);   
    }
    
    public Message getMessageByID(int message_id) {
        Message m = messageDAO.getMessageByID(message_id);
        return m;              
    }
    public List<Message> getAllMessagesByAccountID(int account_id) {
        List<Message> allMessages = messageDAO.getMessageByAccountID(account_id);
        return allMessages;
    } 
}
