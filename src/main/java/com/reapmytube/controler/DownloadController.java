package com.reapmytube.controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.reapmytube.model.DownloadParamter;


@RestController
public class DownloadController {



	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping( "/download/media/{id}/file" )
	public ResponseEntity<Resource> downloadMediaFile(@PathVariable String id) throws IOException {

		File tempDir = new File("TMP/" + id + "/");

		if(! tempDir.exists() )
		{
			return null;
		}
		

		String[] pathnames = tempDir.list();


		if(pathnames.length == 0 )
		{
			return null;
		}


		File fToDownload = null;
		
		for (int i = 0 ; i < pathnames.length  ; i++ )
		{
			if ( ! ( new File( tempDir + "/" + pathnames[i] )).isDirectory() )
			{
				fToDownload = new File(tempDir + "/" + pathnames[i]);
			}
		}
		
		
		
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fToDownload.getName().replaceAll("[^A-Za-z0-9 .]", ""));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
		
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(fToDownload.getAbsolutePath())));

	    return ResponseEntity.ok()
	            .headers(header)
	            .contentLength(fToDownload.length())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);

	}
	
}
