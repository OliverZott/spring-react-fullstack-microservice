package com.velo.microserviceusermanagement.controller;

import com.velo.microserviceusermanagement.model.Role;
import com.velo.microserviceusermanagement.model.User;
import com.velo.microserviceusermanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        user.setRole(Role.USER);
        User result = userService.save(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(Principal principal) {

        // Principal principal = request.getUserPrincipal();
        if (principal == null || principal.getName() == null) {
            // means that logout will be successful. login?logout
            return new ResponseEntity<>(HttpStatus.OK);
        }

        // username = principal.getName();
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @PostMapping("/service/names")
    public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList) {
        return ResponseEntity.ok(userService.findUsers(idList));
    }

    @GetMapping("/service/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("This is just an API test");
    }


}
