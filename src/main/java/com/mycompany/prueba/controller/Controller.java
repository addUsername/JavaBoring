package com.mycompany.prueba.controller;

import com.mycompany.prueba.model.Model;
import com.mycompany.prueba.model.ModelThreads;
import com.mycompany.prueba.view.View;
/**
 * <h1>Controller</h1>
 * handles M V instances
 * 
 * @author SERGI
 * @version 1.0
 * @since   2020-07-1
 */
public class Controller {

	private View view;
	private Model model;
	private ModelThreads fastModel;
	
	private String menu = "1. Leer video\n10. Leer video faster\n2. Generar plots\n3. Generar video\n4. Crear entorno\n5. Borrar temps\n0. Salir";
	/**
	   * Constructor, instantiates  {@link Model} . {@link ModelThreads}  and  {@link View} Classes
	   */
	public Controller() {
		
		this.view = new View();
		this.model = new Model();
		this.fastModel = new ModelThreads();
	}
	/**
	 * This method starts the program, you write here if new options are made
	 * 
     * @return Boolean false break {@link com.mycompany.prueba.Prueba#main(String[])} method
	 */
	public Boolean start() {
		
		String [][] parameters;
		int option = view.showMenu(this.menu);
		switch(option) {
		
			case 1:
				parameters = view.showMenuRead();				
				model.read(parameters);
				break;
			case 10:
				parameters = view.showMenuRead();
				fastModel.read(parameters);
				break;
			case 2:
				parameters = view.showMenuPlot();
				model.flatPlot(parameters);
				view.showFlatPlot();
				model.threedPlot(parameters);
				view.show3dPlot();
				break;
			case 3:
				parameters = view.showMenuWrite();				
				model.writeVideo(parameters);
				break;
			case 4:
				String condaPath = view.showMenuEnvironment();
				view.showBat();
				model.delTempFiles();
				model.prepareBats(condaPath);
				view.showCreate();
				model.createCondaEnvironment();
				break;
			case 5:
				String pathPath = view.showMenuEnvironment();
				model.delTempFiles();
				model.prepareBats(pathPath);
				break;
			case 0:
				//Exit, break while-bucle in Prueba.java.main
				view.closing();
				return false;
		}
		return true;
	}
	/**
	 * This method sends generated outputs from actual running bats to the view
	 * 
	 * @param The String comming from python.
	 */
	public static void printOutputBat(String output) {		
		View.printOutputBat(output);		
	}
}
