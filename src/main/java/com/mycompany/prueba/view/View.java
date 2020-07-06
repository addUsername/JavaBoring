package com.mycompany.prueba.view;

import java.util.Scanner;

/**
 * <h1>View</h1>
 * Handles printf and scanf
 * 
 * @author SERGI
 * @version 1.0
 * @since   2020-07-1
 */
public class View {

	private int option;
	   /**
	   * This method print main menu
	   * @param menu This represents all possible options
	   * @return int This returns choosed option.
	   */
	public int showMenu(String menu) {		
		
		System.out.println("++++++++++\n MENU PPAL\n++++++++++");
		System.out.println(menu);
		validateInput(new Scanner(System.in));
		
		return option;
	}
		/**
	   * This method check in user input is an int.
	   * Using recursion thats why there is no need to
	   * return anything
	   * @param sc scanner
	   */
	private void validateInput(Scanner sc) {
		
		String input = sc.nextLine();
		
		try {			
			option = Integer.parseInt(input);
		} catch (Exception e){			
			System.out.println("Input erroneo!, Escriba un numero");
			validateInput(sc);			
		}
	}
	/**
	   * This method get path, champions and timming info
	   * from user input
	   * @return String[][] 
	   */
	public String[][] showMenuRead() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("++++++++++\n MENU Leer video\n++++++++++");
		System.out.println("-Introduzca el path del video ");
		String [] path = {sc.nextLine()};
		
		System.out.println("-Introduzca los campeones a seguir separados por comas (,) max=5");		
		String [] champions = sc.nextLine().split(",");
		
		System.out.println("-Introduzca los valores de configuracion separados por comas (,)");
		System.out.println("threshold (recomendado=0.33), segundo inicial, frame step (analizar todos los frames=1), frame stop (=segundos*30fps)");
		String [] timming = sc.nextLine().split(",");
		
		String[][] toReturn = {path, champions, timming};
		return toReturn;
	}
	/**
	   * This method get path, champions and timming info
	   * from user input
	   * @return String[][] 
	   */	
	public String[][] showMenuPlot() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("++++++++++\n MENU Graficas\n++++++++++");

		System.out.println("-Introduzca el nombre del archivo nombre.json generado.\n(../prueba/target/json)/ \n(sin extension)");
		String[] name = {sc.nextLine()};
		
		String[][] toReturn = {name};
		return toReturn;
	}
	
	public String[][] showMenuWrite() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("++++++++++++++\n MENU Escribir video\n++++++++++++++");
		
		System.out.println("-Introduzca el path del video");		
		String [] videoPath = {sc.nextLine()};		

		System.out.println("-Introduzca el nombre del archivo nombre.json generado anteriormente.\n(../prueba/target/json)/ \n(sin extension)");
		String[] title = {sc.nextLine()};
		
		System.out.println("this may take a while..");
		String[][] toReturn = {videoPath, title};
		return toReturn;
	}
	
	public String showMenuEnvironment() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("++++++++++\n MENU Entorno\n++++++++++");
		System.out.println("Introduzca el path de instalacion de conda, \npor defecto: C:\\Users\\USER\\Anaconda");
		return sc.nextLine();
	}
	/** This methods should be rewrite on a single method:
	   * public void printView (String string)
	   * @param String
	   */
	public void closing() { 	System.out.println("Cerrando.. xao"); }
	/**
	   * @see View#closing()
	   */
	public void showBat() {		System.out.println("Configurando archivos .bat..");	}
	/**
	   * @see View#closing()
	   */
	public void showCreate() {	System.out.println("Creando entorno virtual.."); }
	/**
	   * @see View#closing()
	   */
	public void showFlatPlot() {System.out.println("Creado grafico 2D."); }
	/**
	   * @see View#closing()
	   */
	public void show3dPlot() {	System.out.println("Creado grafico 3D."); }
	/** Static method from {@link com.mycompany.prueba.controller.Controller#printOutputBat(java.lang.String[])} method.
	   * 
	   * @param output
	   */
	public static void printOutputBat(String output) {
		//quizas halla que crear una clase estatica
		if (output == "") System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		else System.out.println(output);
		
	}


}
