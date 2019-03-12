package com.adioconsultancy.recruitment.controller;

import com.adioconsultancy.recruitment.domain.User;
import com.adioconsultancy.recruitment.exception.ResourceNotFoundException;
import com.adioconsultancy.recruitment.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    IUserRepository userRepository;

    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();



    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity getbyId(
            @PathVariable(value = "id") final Long id) throws ResourceNotFoundException{
        logger.info("Fetching User with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ id));;
        return ResponseEntity.ok().body(user);
    }


    @RequestMapping(value="/users/signup", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(
            @RequestBody User user, UriComponentsBuilder ucBuilder){
        logger.info("Creating User : {}", user);
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("Unable to create. A User with email already exist", user.getUsername());
            return new ResponseEntity<>("Unable to create. A User with name "
                    + user.getUsername() + " already exist.", HttpStatus.CONFLICT);
        }
        User newUser = new User(user.getUsername(),user.getEmail(),bcryptEncoder.encode(user.getPassword()));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
    }




}
