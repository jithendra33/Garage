package com.example.garage.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.garage.UserController.UserIsNotFoundException;
import com.example.garage.UserEntity.User;
import com.example.garage.UserRepository.UserRepository;
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EncryptionService encryptionService;

	public User saveUser(String name,String mobileNumber, String emailAddress, String password) throws SerialException, SQLException, IOException {
		Optional<User> emailOptional=userRepository.findByEmailAddress(emailAddress);
		if (emailOptional.isPresent()) {
			throw new UserIsNotFoundException("user is already existed with this email addres .");
		}
		Optional<User> mobileOptional=userRepository.findByMobileNumber(mobileNumber);
		if (mobileOptional.isPresent()) {
			throw new UserIsNotFoundException("Mobile number is already registered");
		}
		User user= new User();
		user.setName(name);
		user.setEmailAddress(emailAddress);
		user.setMobileNumber(mobileNumber);
		
		user.setPassword((password));
		return userRepository.save(user);
	}

	public User loginUser(String mobileNumber, String password) {
        Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);
        if (!userOptional.isPresent()) {
            throw new UserIsNotFoundException("Mobile number not registered");
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            throw new UserIsNotFoundException("Invalid password");
        }
        return user;
    }
}