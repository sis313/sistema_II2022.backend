package com.example.mslogin.api;

import com.example.mslogin.bl.UserBl;
import com.example.mslogin.dto.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPI {
    private UserBl userBl;
    Logger LOGGER = LoggerFactory.getLogger(UserAPI.class);

    public UserAPI(UserBl userBl) {
        this.userBl = userBl;
    }


    @PostMapping("user")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity){
        LOGGER.info("addUser from UserAPI");
        String response = this.userBl.createUser(userEntity);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
}