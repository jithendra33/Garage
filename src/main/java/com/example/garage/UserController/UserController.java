package com.example.garage.UserController;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.garage.UserEntity.LoginRequest;
import com.example.garage.UserEntity.ResponseBody;
import com.example.garage.UserEntity.User;
import com.example.garage.UserEntity.UserDTO;
import com.example.garage.UserService.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/Garage/User")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/Registration")
    public ResponseEntity<ResponseBody<?>> saveUser(@Valid @RequestBody UserDTO userDTO) throws SerialException, SQLException, IOException {
        User users = userService.saveUser(userDTO.getName(), userDTO.getMobileNumber(), userDTO.getEmailAddress(), userDTO.getPassword());
        ResponseBody<User> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(users);
        return ResponseEntity.ok(body);
    }
	
	@PostMapping("/login")
    public ResponseEntity<ResponseBody<?>> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest.getMobileNumber(), loginRequest.getPassword());
        ResponseBody<User> body = new ResponseBody<>();
        if (user != null) {
            body.setStatusCode(HttpStatus.OK.value());
            body.setStatus("SUCCESS");
            body.setData(user);
        } else {
            body.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            body.setStatus("FAILURE");
        }
        return ResponseEntity.ok(body);
    }
}