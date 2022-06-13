package com.reapmytube.controler; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class YoutbeAPIController {

	@Autowired
	Environment env;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/youtube/search")
	public String youtubeSearch(@RequestParam(required = true) String query) {

	    final String uri = 
	    	    "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=48&q=" +   query + "&type=video&key=" +env.getProperty("youtube.apikey");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);

		return result;
	}
	

	@GetMapping("/")
	public String defaultController() {

	    return "Coucou";
	}

}
