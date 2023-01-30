package com.backend.uberclone.controller;


import com.backend.uberclone.dto.*;
import com.backend.uberclone.model.User;
import com.backend.uberclone.service.ProfileService;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {


    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getProfileInfo/{id}")
    public ResponseEntity<UserDTO> getProfileInfo(@PathVariable("id") String id){
            return new ResponseEntity<>(profileService.getUserById(Integer.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping("/updateUserProfileInfo")
    public ResponseEntity<SuccessResponseDTO> updateUserProfile(@RequestBody UserDTO user){
        if (userService.updateUser(user)) {
                return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
            }
        }

    @PostMapping("/changePassword")
    public ResponseEntity<SuccessResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordRequest){

        User user = userService.findOneById(Integer.valueOf(changePasswordRequest.getUserId()));
        System.out.println("OVO JE IDDDDDDD");
        System.out.println(changePasswordRequest.getUserId());

        if (user == null) {
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);

        } else {
            System.out.println(user.getEmail());

            if (userService.changePassword(user, changePasswordRequest.getNewPassword(), changePasswordRequest.getOldPassword())) {
                return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @PostMapping("/addImage")
    public ResponseEntity<InputStreamResource> addImage(@RequestBody ImageDTO imageDTO) throws IOException {
        byte[] data;
        try {
            data = Base64.getDecoder().decode(imageDTO.getData().split(",")[1]);
        } catch(Exception e) {
            return null;
        }
        String imageName = imageDTO.getUserId();

        String[] tokens = imageDTO.getPath().split("\\.");
        String extension = tokens[tokens.length-1];

        System.out.println(imageDTO.getPath());
        String picturePath = "src\\main\\resources\\static\\images\\"+imageName +"."+ extension;
        userService.setUserPicture(picturePath, imageDTO.getUserId());
        try (OutputStream stream = new FileOutputStream(new File(picturePath).getCanonicalFile())) {
            stream.write(data);
            FileSystemResource imgFile = new FileSystemResource("src/main/resources/static/images/" + imageName+"."+extension);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        }

    }

    @GetMapping(value = "/getImage/{userId}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("userId") Integer userId) {
        try {
            User u = userService.findOneById(userId);
            FileSystemResource imgFile = new FileSystemResource(u.getProfilePicture());
            System.out.println("NASAOOOOO SLIKUUUU");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/banUser/{id}")
    public ResponseEntity<Boolean> banUser(@PathVariable("id") String id){
        return new ResponseEntity<>(profileService.banUser(Integer.valueOf(id)), HttpStatus.OK);
    }
}
