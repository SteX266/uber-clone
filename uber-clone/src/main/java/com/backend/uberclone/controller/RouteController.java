package com.backend.uberclone.controller;

import com.backend.uberclone.dto.FavoriteRouteDTO;
import com.backend.uberclone.dto.RideHistoryDTO;
import com.backend.uberclone.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/route", produces = MediaType.APPLICATION_JSON_VALUE)
public class RouteController {

    RouteService routeService;

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/getFavoriteRoutes/{id}")
    public ResponseEntity<List<FavoriteRouteDTO>> getFavoriteRoutes(@PathVariable("id") Integer customerId){
        List<FavoriteRouteDTO> routes = this.routeService.getFavoriteRoutes(customerId);
        return new ResponseEntity<>(routes, HttpStatus.OK);

    }
}
