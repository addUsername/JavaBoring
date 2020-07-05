package com.mycompany.prueba;

import com.mycompany.prueba.controller.Controller;
/**
 * <h1>Prueba</h1>
 * The prueba program implements an application that
 * simply handles 3 .py files, these files will generate 
 * the path made by the lol champions that are
 * visible on minimap and export it as .json, 
 * .png and .mp4 files
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
