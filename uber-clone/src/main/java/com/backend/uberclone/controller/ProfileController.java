package com.backend.uberclone.controller;


import com.backend.uberclone.dto.ChangePasswordDTO;
import com.backend.uberclone.dto.ResetPasswordDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.model.User;
import com.backend.uberclone.service.ProfileService;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(user);
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
}
