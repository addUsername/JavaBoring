package com.mycompany.prueba.view;

import java.util.Scanner;

import com.mycompany.prueba.controller.Controller;

/**
 * <h1>View</h1>
 * Handles printf and scanf, nothing to view here.. but all of this methods could become independent .java files in a little bit more sophisticated view
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
	   * This method get path, champions and timming info from user input 
	   * See ./prueba/src/main/resources/names_changed.txt
	   * @return String[][] 
	   */
	public String[][] showMenuRead() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("++++++++++\n MENU Leer video\n++++++++++");
		System.out.println("-Introduzca el path del video ");
		String [] path = {sc.nextLine()};
		
		System.out.println("-Introduzca los campeones a seguir separados por comas ( , ) max=5\nEx: Amumu,Caitlyn,Darius,Fizz,Morgana");
		System.out.println("los ( ' ) presentes en algunos nombres son ignorados: Cho'gath -> Chogath \n");
		System.out.println("Nombre real -> identificador");
		
		//Esta lista se saca tal cual de un .txt que genera el web_scrap.py
		System.out.println("Aurelion Sol -> Aurelion | Dr. Mundo -> Mundo | Jarvan IV -> Jarvan\n" + 
				"Lee Sin -> Sin | Maestro Yi -> Maestro | Miss Fortune -> Fortune | Nunu y Willump -> Willump\n" + 
				"Tahm Kench -> Kench | Twisted Fate -> Twisted | Xin Zhao -> Zhao");
		String [] champions = sc.nextLine().split(",");
		
		System.out.println("-Introduzca los valores de configuracion separados por comas (,)");
		System.out.println("threshold (recomendado=0.33), segundo inicial, frame step (analizar todos los frames=1), segundo stop (Si es una lectura rapida, este valor ha de ser menor que la duracion del video)");
		String [] timming = sc.nextLine().split(",");
		
		String[][] toReturn = {path, champions, timming};
		return toReturn;
	}
	/**
	   * This method get path and timming info
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
	/**
	   * This method get video path and json title
	   * from user input
	   * @return String[][] 
	   */	
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
	/**
	   * This method get condaPath
	   * from user input
	   * @return String[][] 
	   */	
	public String showMenuEnvironment() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("++++++++++\n MENU Entorno\n++++++++++");
		System.out.println("Este proceso eliminara archivos temporales (.bat y .json) y generara nuevos");
		System.out.println("Introduzca el path de instalacion de conda, \npor defecto: C:\\Users\\USER\\Anaconda3\\");
		return sc.nextLine();
	}
	/** This methods should be rewrite on a single method:
	   * public void printView (String string)
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
	/** Static method called from {@link Controller#printOutputBat(String))} method.
	   * 
	   * @param output The string comming from python.
	   */
	public static void printOutputBat(String output) {
		//quizas halla que crear una clase estatica
		if (output == "") System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		else System.out.println(output);
		
	}


}
