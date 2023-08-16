package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public boolean usernameCheck(String username) {
        // check if username meets requirements
        if (username.length() < 1) {
            return false;
        }
        return true;
    }

    public boolean passwordCheck(String password) {
        // check if password meets requirements
        if (password.length() < 4) {
            return false;
        }
        return true;
    }

    public Account addAccount(Account account) {
        boolean validUsername = usernameCheck(account.getUsername());
        boolean validPassword = passwordCheck(account.getPassword());
        Account duplicateUsername = getAccountByUsername(account.getUsername());

        // check for username and password requirement, and duplicate username
        if (validUsername && validPassword && duplicateUsername == null) {
            return accountDAO.addAccount(account);
        }
        return null;
    }

    public Account verifyLogin(Account account) {
        return accountDAO.verifyLogin(account);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccountByID(int id) {
        return accountDAO.getAccountByID(id);
    }
}
