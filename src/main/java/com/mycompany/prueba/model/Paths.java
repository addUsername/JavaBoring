package com.mycompany.prueba.model;

import java.io.File;
/**
 * <h1>Paths</h1>
 * This class handles paths
 * @author SERGI
 * @version 1.0
 * @since   2020-07-7
 */
public class Paths {
	
	private String rootPath;
	private String resourcesPath;
	private String jsonPath;
	private String jsonPath_temp;
	protected String pathPath;
	protected String videoPath;
	
	public Paths() {
		
		File dir = new File (".");
		this.rootPath = dir.getAbsolutePath().split("prueba")[0] + "prueba\\";
		this.resourcesPath = this.rootPath + "src\\main\\resources\\";
		this.jsonPath = this.rootPath + "target\\json\\";
		this.jsonPath_temp = this.jsonPath+"temp\\";
		
	}
	public String getRootPath() {
		return rootPath;
	}

	public String getResourcesPath() {
		return resourcesPath;
	}

	public String getJsonPath() {
		return jsonPath;
	}

	public String getPathPath() {
		return pathPath;
	}
	public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}
	public void setPathPath(String pathPath) {
		this.pathPath = pathPath;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getJsonPath_temp() {
		return jsonPath_temp;
	}
	public void setJsonPath_temp(String jsonPath_temp) {
		this.jsonPath_temp = jsonPath_temp;
	}
	
}
