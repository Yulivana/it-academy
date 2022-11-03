package com.pvt.facade;

import com.pvt.dao.impl.UserDAOImpl;
import com.pvt.exceptions.LoginException;
import com.pvt.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    private UserDAOImpl userDAO = new UserDAOImpl();

    public User isValidLogin(String name, String password) {
        userDAO.beginTransaction();
        User user = userDAO.getUserByUserName(name);
        userDAO.commit();
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public void createUser(User user) throws LoginException {
        validateNewData(user);
        userDAO.beginTransaction();
        userDAO.save(user);
        userDAO.commit();
    }

    public void updateUser(User user) throws LoginException {
        userDAO.beginTransaction();
        User persistedUser = userDAO.find(user.getId());
        validateExistUser(persistedUser, user);
        userDAO.update(user);
        userDAO.commit();
    }

    public User findUser(long Id) {
        userDAO.beginTransaction();
        User user = userDAO.find(Id);
        userDAO.commit();
        return user;
    }

    public List<User> listAll() {
        userDAO.beginTransaction();
        List<User> users = userDAO.findAll("User.getAll");
        userDAO.commit();
        return users;
    }

    public void deleteUser(long id) {
        userDAO.beginTransaction();
        userDAO.delete(id);
        userDAO.commit();
    }


    public void validateNewData(User user) throws LoginException {
        if (userDAO.getUserByUserName(user.getUserName()) != null) {
            throw new LoginException("We have User with this UserName");
        } else if (userDAO.getUserByEmail(user.getEmail() ) != null) {
            throw new LoginException("We have User with this Email");
        } else if (isPasswordValidate(user.getPassword())) {
            throw new LoginException("Password is not valid");
        }
    }

    public void validateExistUser(User userOld, User user) throws LoginException {
        List<String> errors = new ArrayList<>();

        if (!userOld.getUserName().equals(user.getUserName())) {
            User userByName = userDAO.getUserByUserName(user.getUserName());
            if (userByName != null && !userByName.equals(userOld)) {
                errors.add("We have User with this UserName");
            } else {
                userOld.setUserName(user.getUserName());
            }
        }
        if (!userOld.getPassword().equals(user.getPassword())) {
            if (isPasswordValidate(user.getPassword())) {
                errors.add("Password is not valid");
            } else {
                userOld.setPassword(user.getPassword());
            }
        }
        if (!userOld.getEmail().equals(user.getEmail())) {
            User userByEmail = userDAO.getUserByEmail(user.getEmail());
            if (userByEmail != null && !userByEmail.equals(userOld)) {
                errors.add("We have User with this Email");
            } else {
                userOld.setEmail(user.getEmail());
            }
        }

        if(errors.size()>0) {
            throw new LoginException(errors.get(0));
        }
    }

    public boolean isPasswordValidate(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}";
        return !password.matches(pattern);
    }

}
