package com.shikha.learn.MarsRover.service;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shikha.learn.MarsRover.dto.HomeDto;
import com.shikha.learn.MarsRover.repository.PreferenceRepository;
import com.shikha.learn.MarsRover.response.MarsPhotos;
import com.shikha.learn.MarsRover.response.MarsRoverApiResponse;

@Service//two controller functions in one service
public class MarsRoverApiService {
	private static final String API_KEY="DGSk1pWWvXcaDdbiHh2AQm0m4Gb9Xwa1Dik0g8oD";
	
	public Map<String,List<String>> validCameras = new HashMap<>();
	
	@Autowired
	private PreferenceRepository preferencesRepo;
	
	public MarsRoverApiService () {
		validCameras.put("Opportunity", Arrays.asList("FHAZ","RHAZ", "NAVCAM" ,"PANCAM" ,"MINITES"));
		validCameras.put("Curiosity", Arrays.asList("FHAZ" ,"RHAZ" ,"MAST" ,"CHEMCAM", "MAHLI","MARDI" ,"NAVCAM"));
		validCameras.put("Spirit", Arrays.asList("FHAZ","RHAZ", "NAVCAM" ,"PANCAM" ,"MINITES"));

	}
	
	public MarsRoverApiResponse getRoverData(HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
		RestTemplate rt = new RestTemplate();
	    
	    List<String> apiUrlEnpoints = getApiUrlEnpoints(homeDto);
	    List<MarsPhotos> photos = new ArrayList<>();
	    MarsRoverApiResponse response = new MarsRoverApiResponse();
	    
	    apiUrlEnpoints.stream()
	                  .forEach(url -> { 
	                    MarsRoverApiResponse apiResponse = rt.getForObject(url, MarsRoverApiResponse.class);
	                    photos.addAll(apiResponse.getPhotos());
	                  });
	    
	    response.setPhotos(photos);
	    
	    return response;
	}
	
	  public List<String> getApiUrlEnpoints (HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		    List<String> urls = new ArrayList<>();
		    
		    Method[] methods = homeDto.getClass().getMethods();
		    
		    for (Method method: methods) {
		      if(method.getName().indexOf("getCamera") > -1 && Boolean.TRUE.equals(method.invoke(homeDto))) {
		    	  String cameraName = method.getName().split("getCamera")[1].toUpperCase();
		    	if (validCameras.get(homeDto.getMarsApiRoverData()).contains(cameraName)) {
		    		urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=" + cameraName);
		    	
		    		
		    	
		    	}
		    }
	  }
		    
		    	
		   //DONOT NEED ABOVE CODE 
//		    if (Boolean.TRUE.equals(homeDto.getCameraFhaz())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=FHAZ");
//		    } 
//		    if (Boolean.TRUE.equals(homeDto.getCameraChemcam()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=CHEMCAM");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraMahli()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=MAHLI");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraMardi()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=MARDI");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraMast()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=MAST");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraMinites()) && !"curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=MINITES");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraNavcam())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=NAVCAM");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraPancam()) && !"curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=PANCAM");
//		    }
//		    if (Boolean.TRUE.equals(homeDto.getCameraRhaz())) {
//		      urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=RHAZ");
//		    }
		    return urls;
		  }

	public Map<String, List<String>> getValidCameras() {
		return validCameras;
	}

	public HomeDto save(HomeDto homeDto) {
		return preferencesRepo.save(homeDto);
		// TODO Auto-generated method stub
		
	}

	public HomeDto findByUserId(Long userId) {
	      return  preferencesRepo.findByUserId(userId);
		
	}
	  
	  
}
