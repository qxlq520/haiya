package com.haiya.video.controller;

import com.haiya.video.entity.UserProfile;
import com.haiya.video.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfile> getUserProfileByUserId(@PathVariable Long userId) {
        Optional<UserProfile> profile = userProfileService.getUserProfileByUserId(userId);
        if (profile.isPresent()) {
            return ResponseEntity.ok(profile.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfileByUsername(@PathVariable String username) {
        Optional<UserProfile> profile = userProfileService.getUserProfileByUsername(username);
        if (profile.isPresent()) {
            return ResponseEntity.ok(profile.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createUserProfile(userProfile);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfile profileDetails) {
        Optional<UserProfile> updatedProfile = userProfileService.updateUserProfile(userId, profileDetails);
        if (updatedProfile.isPresent()) {
            return ResponseEntity.ok(updatedProfile.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}