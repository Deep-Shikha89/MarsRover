package com.shikha.learn.MarsRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shikha.learn.MarsRover.dto.HomeDto;

public interface PreferenceRepository extends JpaRepository<HomeDto,Long> {
	
	@Query(value="select * from mars_api dto where userid= :userId",nativeQuery=true)  
	HomeDto findByUserId(Long userId);
	

}
