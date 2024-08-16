package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import org.mandulis.mts.dto.request.UserRequest;
import org.mandulis.mts.service.UserService;
import org.mandulis.mts.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse createdUser = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<UserResponse> foundUser = userService.findUserResponseById(id);
        return foundUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse updatedUser = userService.updateUserById(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fullinfo/{id}")
    public ResponseEntity<UserResponse> getFullUserInfoById(@PathVariable Long id) {
        Optional<UserResponse> foundUser = userService.findUserResponseById(id);
        return foundUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/fullinfo/name/{name}")
    public ResponseEntity<UserResponse> getFullUserInfoByName(@PathVariable String name) {
        Optional<UserResponse> foundUser = userService.findUserResponseByUsername(name);
        return foundUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam(required = false) String username,
                                                          @RequestParam(required = false) String firstName,
                                                          @RequestParam(required = false) String lastName,
                                                          @RequestParam(required = false) String email) {
        List<UserResponse> users = userService.searchUsers(username, firstName, lastName, email);
        return ResponseEntity.ok(users);
    }
}
