package com.example.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dto.RestaurantDTO;
import com.example.service.RestaurantService;

public class RestaurantControllerTest {

	@InjectMocks
	RestaurantController restaurantController;

	@Mock
	private RestaurantService restaurantService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	
	
	@Test
	public void testGetAllREstaurants() {

		// Arrange

		List<RestaurantDTO> mockRestaurants = Arrays.asList(
				new RestaurantDTO(1, "REstaurant 1", "Address 1", "Delhi", "REstaurant 1 DEscri[tion"),
				new RestaurantDTO(2, "REstaurant 2", "Address 2", "Jaipur", "REstaurant 2 DEscri[tion"));

		// Act
		when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);
		ResponseEntity<List<RestaurantDTO>> response = restaurantController.getAllREstaurants();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurants, response.getBody());

		verify(restaurantService, times(1)).findAllRestaurants();
	}

	@Test
	public void testSaveRestaurant() {

		// Arrange

		RestaurantDTO mockRestaurant = new RestaurantDTO(1, "REstaurant 1", "Address 1", "Delhi",
				"REstaurant 1 DEscri[tion");

		// Act
		when(restaurantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

		ResponseEntity<RestaurantDTO> response = restaurantController.saveRestaurant(mockRestaurant);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(mockRestaurant, response.getBody());

		verify(restaurantService, times(1)).addRestaurantInDB(mockRestaurant);

	}

	@Test
	public void testFindRestaurantById() {


		// Arrange
		Integer mockRestaurantId = 1;
		RestaurantDTO mockRestaurant = new RestaurantDTO(mockRestaurantId, "REstaurant 1", "Address 1", "Delhi",
				"REstaurant 1 DEscri[tion");

		// Act
		when(restaurantService.fetchRestaurantById(mockRestaurantId)).thenReturn(new ResponseEntity<RestaurantDTO>(mockRestaurant,HttpStatus.OK));

		ResponseEntity<RestaurantDTO> response = restaurantController.findRestaurantById(mockRestaurantId);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurant, response.getBody());

		verify(restaurantService, times(1)).fetchRestaurantById(mockRestaurantId);
	}

}
