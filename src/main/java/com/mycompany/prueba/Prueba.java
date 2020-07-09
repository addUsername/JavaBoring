package com.mycompany.prueba;

import com.mycompany.prueba.controller.Controller;
/**
 * <h1>Prueba</h1>
 * The main task is generate the path made by the lol champions that are visible on minimap and export it as:
 * .json, .png and .mp4 files.
 * <p> * 
 * python and bat docs are not written yet and maybe never will be.
 * 
 * @author SERGI
 * @version 1.0
 * @since   2020-07-1
 */
public class Prueba {

	/**
	 * main method, just start and close the program
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		boolean flag = true;
		
		while(flag) {			
			flag = controller.start();
		}		
	}
}
