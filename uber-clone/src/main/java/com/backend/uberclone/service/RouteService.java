package com.backend.uberclone.service;

import com.backend.uberclone.dto.FavoriteRouteDTO;
import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.FavouriteRoute;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.FavoriteRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    @Autowired
    private FavoriteRouteRepository favoriteRouteRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public boolean saveFavoriteRoute(FavoriteRouteDTO favoriteRouteDTO) {
        Customer customer = customerRepository.findOneByEmail(favoriteRouteDTO.getCustomer());
        FavouriteRoute favouriteRoute = new FavouriteRoute(favoriteRouteDTO, customer);
        this.favoriteRouteRepository.save(favouriteRoute);
        return true;
    }
}
