package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.RestaurantDTO;
import com.example.entity.Restaurant;
import com.example.mapper.RestaurantMapper;
import com.example.repo.RestaurantRepo;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepo repo;
	
	
	public List<RestaurantDTO> findAllRestaurants() {
		
		List<Restaurant> restaurants = repo.findAll();
		List<RestaurantDTO> dtos = restaurants.stream().map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
							.collect(Collectors.toList());
		return dtos;
	}


	public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
		
		Restaurant savedRestaurant = repo.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
		return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(savedRestaurant);
	}


	public ResponseEntity<RestaurantDTO> fetchRestaurantById(Integer id) {
		Optional<Restaurant> restaurant =  repo.findById(id);
		
		if(restaurant.isPresent()) {
			
			return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}

}
