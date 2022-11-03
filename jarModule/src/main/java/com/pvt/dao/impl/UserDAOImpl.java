package com.pvt.dao.impl;

import com.pvt.dao.AbstractJPADAO;
import com.pvt.model.User;
import java.util.HashMap;
import java.util.Map;

public class UserDAOImpl extends AbstractJPADAO<User, Long> {

    private static final long serialVersionUID = 1L;

    public UserDAOImpl() {
        super(User.class);
    }

    public User getUserByEmail(String email) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", email);
        return super.findOneResult("User.getUserByEmail", parameters);
    }

    public User getUserByUserName(String name) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        return super.findOneResult("User.getUserByUserName", parameters);
    }
}
