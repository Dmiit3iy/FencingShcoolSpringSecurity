package org.dmiit3iy.controllers;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import org.dmiit3iy.model.UserDetailsImp;
import org.dmiit3iy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FencingSchool/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<User>> add(@RequestBody User user) {
        try {
            this.userService.add(user);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<User>> getAuth(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
            User user = userService.get(userDetails.getId());
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        }
        return null;

    }

//    @GetMapping
//    public ResponseEntity<ResponseResult<User>> get(@RequestParam String login, @RequestParam String password) {
//        try {
//            User user = userService.get(login, password);
//            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<User>> get(@PathVariable long id) {
        try {
            User user = this.userService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<User>> delete(@PathVariable long id) {

        try {
            User user = this.userService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


}