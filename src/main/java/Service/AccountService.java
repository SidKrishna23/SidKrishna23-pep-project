package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;


public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
  
    public List<Account> getAccounts() {
        return accountDAO.getAllAccounts();
    }
   
    public Account validAccount(Account account) {
        Account a = accountDAO.getAccountByUserName(account.getUsername());
        if (a == null) {
           return null;
        }
        if(a.getPassword().equals(account.getPassword())) {
            return a;
        }
        else{
            return null;
        }
        
    }

    public Account registerAccount(Account account) {
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }
        Account a = accountDAO.getAccountByUserName(account.getUsername());
        if (a == null) {
           return accountDAO.insertAccount(account);
        }

        return null;
    }

    public Account addAccount(Account account) {
        System.out.println("Account: " + accountDAO.getAccountByUserName(account.getUsername()));
        if (accountDAO.getAccountByUserName(account.getUsername()) == null) {
            return accountDAO.insertAccount(account);
        }
        return null;
    }

}
