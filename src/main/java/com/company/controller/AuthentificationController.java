package com.company.controller;

import com.company.dao.UserDao;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ciprian on 6/8/2016.
 */
@RestController
public class AuthentificationController {

    public static Map<String, String> authentificationTokens = new HashMap<String, String>();


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String validateSimpleLogin(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {
        if (UserDao.validateLogin(username, password)) {
            if (authentificationTokens.containsKey(username))
                return authentificationTokens.get(username);
            else {
                String uiToken = UUID.randomUUID().toString();
                authentificationTokens.put(username, uiToken);
                return uiToken;
            }
        } else {
            return "Nok";
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/facebookLogin", method = RequestMethod.POST)
    public
    @ResponseBody
    String validateFacebookLogin(
            @RequestParam(value = "uuid", defaultValue = "") String uuid
    ) {
        String username = UserDao.validateFacebookLogin(uuid);
        if (username == null)
            return "Nok";
        else {
            String uiToken = UUID.randomUUID().toString();
            authentificationTokens.put(username, uiToken);
            return uiToken + ":" + username;
        }
    }

}
