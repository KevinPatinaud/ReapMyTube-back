package com.reapmytube.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MediaDownloadStompMessage {
    private String idCanal;
    private String status;
    private String urlToDownload;

	public String getUrlToDownload() {
		return urlToDownload;
	}
	public void setUrlToDownload(String urlToDownload) {
		this.urlToDownload = urlToDownload;
	}
	public String getIdCanal() {
		return idCanal;
	}
	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
