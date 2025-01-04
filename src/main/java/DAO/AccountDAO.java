package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    /**
     * retrieves all the accounts in the account table
     * @param
     * @return List of Accounts.
     */
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> Accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account Account = new Account(rs.getInt("account_id"), 
                                              rs.getString("username"), 
                                              rs.getString("password"));
                Accounts.add(Account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Accounts;
    }

    /**
     * insert Account into account table
     * @param account to be inserted
     * @return Account on success or null on failure
     */
    public Account insertAccount(Account account) {
        System.out.println("insertAccount: " + account.toString());
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES(?,?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = pkeyResultSet.getInt(1);
                return new Account( generated_account_id,
                                    account.getUsername(), 
                                    account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
     
    /**
     * returns an Account given the username
     * @param username
     * @return Account
     * 
     */
    public Account getAccountByUserName(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {

            String sql = "SELECT * FROM account WHERE username = ?";
            
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            ps.getGeneratedKeys();
            if (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                                                rs.getString("username"),
                                                rs.getString("password"));
                return account;
            } 
        }catch(SQLException e) {
            System.out.println(e.getMessage());    
        }
        return null;
    }   
}
