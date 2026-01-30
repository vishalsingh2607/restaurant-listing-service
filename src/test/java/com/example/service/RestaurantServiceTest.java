package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dto.RestaurantDTO;
import com.example.entity.Restaurant;
import com.example.mapper.RestaurantMapper;
import com.example.repo.RestaurantRepo;

public class RestaurantServiceTest {

	@InjectMocks
	private RestaurantService restaurantService;

	@Mock
	private RestaurantRepo repo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAllRestaurants() {
		// Arrange

		List<Restaurant> mockRestaurants = Arrays.asList(
				new Restaurant(1, "REstaurant 1", "Address 1", "Delhi", "REstaurant 1 DEscri[tion"),
				new Restaurant(2, "REstaurant 2", "Address 2", "Jaipur", "REstaurant 2 DEscri[tion"));

		// Act
		when(repo.findAll()).thenReturn(mockRestaurants);
		List<RestaurantDTO> restaurantDTOList = restaurantService.findAllRestaurants();

		// Assert
		assertEquals(mockRestaurants.size(), restaurantDTOList.size());
		for(int i=0;i<mockRestaurants.size();i++) {
			RestaurantDTO expectedDTO = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(mockRestaurants.get(i));
			assertEquals(expectedDTO, restaurantDTOList.get(i));
		}

		verify(repo, times(1)).findAll();
	}

	@Test
	public void testAddRestaurantInDB() {

		// Arrange

		RestaurantDTO mockRestaurantDTO = new RestaurantDTO(2, "REstaurant 2", "Address 2", "Jaipur", "REstaurant 2 DEscri[tion");

		Restaurant restaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(mockRestaurantDTO);
		// Act
		when(repo.save(restaurant)).thenReturn(restaurant);
		RestaurantDTO savedrestaurantDTO = restaurantService.addRestaurantInDB(mockRestaurantDTO);

		// Assert
		assertEquals(mockRestaurantDTO,savedrestaurantDTO);

		verify(repo, times(1)).save(restaurant);
	}

	@Test
	public void testFetchRestaurantById_existingId() {
		// Arrange
		Integer mockRestaurantId = 2;
		RestaurantDTO mockRestaurantDTO = new RestaurantDTO(mockRestaurantId, "REstaurant 2", "Address 2", "Jaipur", "REstaurant 2 DEscri[tion");
		Restaurant mockRestaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(mockRestaurantDTO);

		// Act
		when(repo.findById(mockRestaurantId)).thenReturn(Optional.of(mockRestaurant));

		ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurantDTO, response.getBody());

		verify(repo, times(1)).findById(mockRestaurantId);
	}
	
	@Test
	public void testFetchRestaurantById_NonexistingId() {
		// Arrange
		Integer mockRestaurantId = 2;
		RestaurantDTO mockRestaurantDTO = new RestaurantDTO(mockRestaurantId, "REstaurant 2", "Address 2", "Jaipur", "REstaurant 2 DEscri[tion");
		Restaurant mockRestaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(mockRestaurantDTO);

		// Act
		when(repo.findById(mockRestaurantId)).thenReturn(Optional.empty());

		ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(null, response.getBody());

		verify(repo, times(1)).findById(mockRestaurantId);
	}

}
