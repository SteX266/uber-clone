package com.backend.uberclone.service;

import com.backend.uberclone.dto.FavoriteRouteDTO;
import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.FavouriteRoute;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.FavoriteRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {
    @Autowired
    private FavoriteRouteRepository favoriteRouteRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void saveFavoriteRoute(FavoriteRouteDTO favoriteRouteDTO) {
        Customer customer = customerRepository.findOneByEmail(favoriteRouteDTO.getCustomer());
        FavouriteRoute favouriteRoute = new FavouriteRoute(favoriteRouteDTO, customer);
        this.favoriteRouteRepository.save(favouriteRoute);
    }

    public List<FavoriteRouteDTO> getFavoriteRoutes(Integer customerId) {
        Customer customer = customerRepository.findOneById(customerId);
        if(customer == null) return null;

        return convertFavoriteRouteToDTO(customer.getFavoriteRoutes(), customer.getEmail());
    }
    private List<FavoriteRouteDTO> convertFavoriteRouteToDTO(List<FavouriteRoute> favouriteRoutes, String customer) {
        List<FavoriteRouteDTO> favoriteRouteDTOS = new ArrayList<>();
        for (FavouriteRoute route: favouriteRoutes) {
            favoriteRouteDTOS.add(new FavoriteRouteDTO(route, customer));
        }
        return  favoriteRouteDTOS;
    }
}
