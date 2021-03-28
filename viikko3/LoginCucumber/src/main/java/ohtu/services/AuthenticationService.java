package ohtu.services;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        char user[] = username.toCharArray();
        char pasw[] = password.toCharArray();
        if (user.length < 3 || pasw.length < 8) {
            return true;
        }
        for (char u : user) {
            if (!isLetter(u)) {
                return true;
            }
        }
        boolean onlyLetters = true;
        for (char p : pasw) {
            if (!isLetter(p)) {
                onlyLetters = false;
            }
        }
        if (onlyLetters) {
            return true;
        }

        return false;
    }
}
