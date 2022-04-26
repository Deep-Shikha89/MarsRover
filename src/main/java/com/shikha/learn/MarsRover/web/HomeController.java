package com.shikha.learn.MarsRover.web;


import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shikha.learn.MarsRover.dto.HomeDto;
import com.shikha.learn.MarsRover.repository.PreferenceRepository;
import com.shikha.learn.MarsRover.response.MarsRoverApiResponse;
import com.shikha.learn.MarsRover.service.MarsRoverApiService;



@Controller //controller listens request and then it(points) executes the code eg. get,post
//CONTROLLERS--listen-execute a bit of code-call a service-get the response-send it back to view
public class HomeController {
	
	@Autowired  // with spring framework it instantiate objects automatically
	private MarsRoverApiService roverService;
	
	@GetMapping("/")//Annotation is pointing to the code (which is below) and is going to execute//
	public String getHomeView(ModelMap model, Long userId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
		HomeDto homeDto= createDefaultHomeDto();
		
		if(userId == null) {
			homeDto = roverService.save(homeDto);
			
		} else {
			 homeDto = roverService.findByUserId(userId);
			
		}
		
		
		
		//if request param is empty,then set a default value
	
		if(StringUtils.isEmpty(homeDto.getMarsApiRoverData())) {
			homeDto.setMarsApiRoverData("Opportunity");
			
		}
		if(homeDto.getMarsSol() == null) {
			homeDto.setMarsSol(1);
		}
		
		MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
		model.put("roverData", roverData);
		model.put("homeDto", homeDto);
		model.put("validCameras", roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));
		
	    return "index";
	}


	private HomeDto createDefaultHomeDto() {
		HomeDto homeDto=  new HomeDto();
		homeDto.setMarsApiRoverData("Opportunity");
		homeDto.setMarsSol(1);
		return homeDto;
	}
	    
	   
	    @PostMapping("/")
	    public String postHomeView(HomeDto homeDto) {
	    	
	    	if(Boolean.TRUE.equals(homeDto.getRememberPreferences())) {
	    		homeDto = roverService.save(homeDto);
	    		
	    	}else {
	    		 homeDto = createDefaultHomeDto();
	    		
	    		
	    	}
	   
	    
	    	return "redirect:/?userId="+homeDto.getUserId();
	    	
	    
}  
		
//	}
//	@GetMapping("/testing")
//	public String getestingView(ModelMap model) {
//		model.put("name","John Doe");
//		model.put("address","123 Fake St.");
//		return "index";
//	}
	
	
//	@PostMapping("/")
//	public String postHomeView(ModelMap model,@RequestParam String marsApiRoverData) {
//		MarsRoverApiResponse roverData = roverService.getRoverData(marsApiRoverData);
//		model.put("roverData", roverData);
//	    return "index";
//		
//		
//		
//	}
//	
	
}