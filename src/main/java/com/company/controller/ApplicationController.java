package com.company.controller;

import com.company.bean.LocationUser;
import com.company.dao.*;
import com.company.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ciprian on 6/8/2016.
 */

@RestController
public class ApplicationController {


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getUserInformation", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUserInformation(@RequestParam(value = "username") String username,
                            @RequestParam(value = "token") String token) {

        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {

            User user = null;
            try {
                user = UserDao.getUserByUsername(username).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
            return user;
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllFriendsLocation", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LocationUser> getAllFriendsLocation(@RequestParam(value = "username") String username,
                                             @RequestParam(value = "token") String token) {
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            List<LocationUser> locationUsersList = new ArrayList<LocationUser>();
            List<User> users = UserContactDao.getAllFriendsByUsername(username);
            for (User user : users) {
                Location location = LocationDao.getLastUserLocation(user.getId());
                LocationUser locationUser = new LocationUser();
                locationUser.setUser_id(user.getUsername());
                locationUser.setLongitude(location.getLongitude());
                locationUser.setLatitude(location.getLatitude());
                locationUser.setSpeed(location.getSpeed());
                locationUser.setTimestamp(location.getTimestamp());
                locationUsersList.add(locationUser);
            }
            return locationUsersList;
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllUserJourneys", method = RequestMethod.GET)
    public @ResponseBody List<Journey> getAllJourneysForUser(@RequestParam(value = "username") String username,
                                 @RequestParam(value = "token") String token) {
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            return JourneyDao.getJourneyByUsername(username);
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllUserJourneyData",method = RequestMethod.GET)
    public @ResponseBody List<JourneyData> getAllJourneyDataForUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ){
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            return JourneyDataDao.getAllJourneyData(username);
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllUserJourneyWithJourneyData",method = RequestMethod.GET)
    public @ResponseBody Map<Journey,List<JourneyData>> getAllUserJourneyWithJourneyDataForUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ){
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            List<Journey> journeyList = JourneyDao.getJourneyByUsername(username);
            Map<Journey,List<JourneyData>> journeyListMap = new HashMap<Journey, List<JourneyData>>();
            for(Journey journey : journeyList){
                journeyListMap.put(journey,JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName()));
            }
            return journeyListMap;
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllJourneyData",method = RequestMethod.GET
    )
    public @ResponseBody List<JourneyData> getAllJourneyData(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            return JourneyDataDao.getFullJourneyDataForAllUsers();
        } else {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllJourneyDataByTravel",method = RequestMethod.GET
    )
    public @ResponseBody
    List<List<JourneyData>> getAllJourneyDataByTravel(
                    @RequestParam(value = "username") String username,
                    @RequestParam(value = "token") String token
            ) {
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            return JourneyDataDao.getFullJourneyDataForAllUsersByTravel(username);
        } else {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAllUserFavorites",method = RequestMethod.GET
    )
    public @ResponseBody
    List<Favorite> getUserFavorites(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username) &&
                AuthentificationController.authentificationTokens.containsValue(token)) {
            //User user = UserDao.getUserByUsername(username).get(0);
           // return FavoriteDao.getAllUserFavorites(user.getId());
            return new ArrayList<>();
        } else {
            return null;
        }
    }
}


