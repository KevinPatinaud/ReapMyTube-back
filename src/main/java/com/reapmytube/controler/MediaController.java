package com.reapmytube.controler;

import java.io.File;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.reapmytube.model.MediaDownloadStompMessage;

@Controller
public class MediaController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;


	@MessageMapping("/generateMedia")
	public MediaDownloadStompMessage recMessage(@Payload MediaDownloadStompMessage message){
		Thread t = new Thread() {
			public void run() {
				System.out.println(message.toString());
				System.out.println(message.getUrlToDownload());

				String tmpDirURI = "TMP/" + message.getIdCanal() ;

				// execute the download async function
				try {
					Runtime.getRuntime().exec("youtube-dl " + message.getUrlToDownload() + " -o " + tmpDirURI + "/%(title)s.%(ext)s");
				} catch (IOException e) {
					e.printStackTrace();
				}

				boolean loading = true;

				while ( loading )
				{

					try {Thread.sleep( 3 * 1000);} catch (InterruptedException e) {		}


					File tempDir = new File(tmpDirURI);
					if ( tempDir.exists())
					{
						String[] pathnames = tempDir.list();

						if ( pathnames.length > 0)
						{
							loading = false;
							for (String pathname : pathnames) {
								System.out.println("pathname : " + pathname);
								if ( pathname.endsWith(".part"))
								{
									loading = true;
								}
							}

							System.out.println("loading : " + loading);
							System.out.println("pathnames.length : " + pathnames.length);
						}
					}
					else
					{
						System.out.println(" tempDir.exists() : " +  tempDir.exists() + " : " + tmpDirURI);
					}
				}



				System.out.println("Mon traitement");
				message.setStatus("OK");
				simpMessagingTemplate.convertAndSendToUser(message.getIdCanal(),"/mediaGenerationEnd",message );

			}
		};
		t.start();
		return message;
	}
}
