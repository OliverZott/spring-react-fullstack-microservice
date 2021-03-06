package com.velo.microserviceusermanagement.controller;

import com.velo.microserviceusermanagement.model.Role;
import com.velo.microserviceusermanagement.model.User;
import com.velo.microserviceusermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final DiscoveryClient discoveryClient;
    private final Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    public UserController(UserService userService, DiscoveryClient discoveryClient, Environment env) {
        this.userService = userService;
        this.discoveryClient = discoveryClient;
        this.env = env;
    }

    @GetMapping("/service/port")
    public String getPort() {
        return "Service port number : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances() {
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId), HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices() {
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }

    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        user.setRole(Role.USER);
        User result = userService.save(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
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
