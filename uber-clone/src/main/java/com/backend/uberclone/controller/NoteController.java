package com.backend.uberclone.controller;


import com.backend.uberclone.dto.CarDTO;
import com.backend.uberclone.dto.NoteDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping(value = "/getNotesByUserId/{id}")
    public ResponseEntity<ArrayList<NoteDTO>> getNotesByUserId(@PathVariable("id") String id){
        return new ResponseEntity<>(noteService.getNotesByUserId(Integer.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping("/addNote")
    public ResponseEntity<SuccessResponseDTO> updateVehicle(@RequestBody NoteDTO note){

        if (noteService.addNote(note)) {
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
        }
    }


}
