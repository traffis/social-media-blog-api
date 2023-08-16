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

    public boolean isValidUsername(String username) {
        if (username.length() < 1) {
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 4) {
            return false;
        }
        return true;
    }

    public boolean isDuplicateUsername(String username) {
        Account duplicateUsername = getAccountByUsername(username);

        if (duplicateUsername == null) {
            return false;
        }
        return true;
    }

    public boolean isExistingUser(int accountId) {
        Account existingUser = accountDAO.getAccountById(accountId);

        if (existingUser == null) {
            return false;
        }
        return true;
    }

    public Account addAccount(Account account) {
        boolean validUsername = isValidUsername(account.getUsername());
        boolean validPassword = isValidPassword(account.getPassword());
        boolean duplicateUsername = isDuplicateUsername(account.getUsername());

        if (validUsername && validPassword && !duplicateUsername) {
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

    public Account getAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }
}
