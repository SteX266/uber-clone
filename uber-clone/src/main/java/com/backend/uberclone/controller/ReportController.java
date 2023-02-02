package com.backend.uberclone.controller;

import com.backend.uberclone.dto.ReportDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/getUserReport")
    public ResponseEntity<List<ReportDTO>> updateUserProfile(@RequestBody ReportDTO reportRequest){
        List<ReportDTO> reports = this.reportService.getUserReport(reportRequest);
        return new ResponseEntity<>(reports,HttpStatus.OK);

    }


}
