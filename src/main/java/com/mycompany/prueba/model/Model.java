package com.mycompany.prueba.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.mycompany.prueba.controller.Controller;

/**
 * <h1>Model</h1>
 * This class handles paths and .bat files.
 * <p>
 * Also this package is wrong, we need min. 4 files:
 * 1 Paths.java (just getters and setters), 2 ProcessBats.java, 3 WriteBats.java and Model.java
 * <p>
 * For scaling up performance..<ul> <li>Same_bat_multiple_times.java
 * (multithreading on same task, no sync needed)<li> 
 * <li>Multiple_bat_at_once.java 
 * (synchronized threads making the whole task of reading and writting video)<li> <ul>
 * 
 * @author SERGI
 * @version 1.0
 * @since   2020-07-1
 */
public class Model {
	
	private String rootPath;
	public String resourcesPath;
	private String pathPath;
	private String videoPath;
	private String jsonPath;
	/**
	   * Constructor from user input
	   */
	public Model() {
		
		File dir = new File (".");
		this.rootPath = dir.getAbsolutePath().split("prueba")[0] + "prueba\\";
		this.resourcesPath = this.rootPath + "src\\main\\resources\\";
		this.jsonPath = this.rootPath + "target\\json\\";
	}
	/**
	   * This method writes custom lines on bats files by calling {@link Model#writePyPath()} and
	   * {@link Model#writeConda()} methods
	   * from user input
	   * @param String Represents anaconda installation path
	   */
	public void prepareBats(String pathPath) {
		
		this.pathPath = pathPath;
		//adds conda path
		writeConda();
		//adds paths to call python
		writePyPath();	
		
	}
	/**
	   * This method writes "call python" lines on .bats, this method should
	   * be "chopped", there are a lot of repetitive code in there
	   */
	private void writePyPath() {
		
		String add;
		File bats = new File (this.resourcesPath+"Bats\\");
		FileWriter fw;
		Scanner sc;
		String content = "";
		int line = 0;
		
		try {
			
			for (File file: bats.listFiles()) {
				switch(file.getName()) {
				
				case "read.bat":
					line = 0;
					sc = new Scanner(file);
					add = "\n Call ";
					add += "python.exe " + this.resourcesPath + "pys\\readVideo.py %path% %champions% %threshold% %second_inicial% %frame_step% %frame_stop% %json_path%";
					while(sc.hasNextLine()) {
						content += sc.nextLine()+"\n";
						if (line == 15) content += add+"\n";
						line++;
					}
					
					fw = new FileWriter(file);
					fw.write(content);
					fw.close();
					content = "";
					break;
					
				case "flatPlot.bat":
					line = 0;
					sc = new Scanner(file);
					add = "\n Call ";
					add += "python.exe " + this.resourcesPath + "pys\\flatPlot.py %path% %champions% %title%";
					add += "\n exit";
					while(sc.hasNextLine()) {
						content += sc.nextLine()+"\n";
						if (line == 12) content += add+"\n";
						line++;
					}
					
					fw = new FileWriter(file);
					fw.write(content);
					fw.close();
					content = "";
					break;
					
				case "3dPlot.bat":
					line = 0;
					sc = new Scanner(file);
					add = "\n Call ";
					add += "python.exe " + this.resourcesPath + "pys\\3dPlot.py %path% %champions% %title%";
					add += "\n exit";
					while(sc.hasNextLine()) {
						content += sc.nextLine()+"\n";
						if (line == 12) content += add+"\n";
						line++;
					}
					
					fw = new FileWriter(file);
					fw.write(content);
					fw.close();
					content = "";
					break;
					
				case "write.bat":
					line = 0;
					sc = new Scanner(file);
					add = "\n Call ";
					add += "python.exe " + this.resourcesPath + "pys\\writeVideo.py %rootpath% %videopath% %title%";
					add += "\n exit";
					while(sc.hasNextLine()) {
						content += sc.nextLine()+"\n";
						if (line == 12) content += add+"\n";
						line++;
					}
					
					fw = new FileWriter(file);
					fw.write(content);
					fw.close();
					content = "";
					break;
					
				default:
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	/**
	   * This method writes Path line on .bats, but it should
	   * be deprecated since Conda path is going to be set by
	   * the user.
	   */	
	public void writeConda() {		
		
		File bats = new File (this.resourcesPath+"Bats\\");
		FileWriter fw;
		Scanner sc;
		String content;
		
		try {
			for (File file: bats.listFiles()) {
				content = "@PATH=%PATH%;"+this.pathPath+";"+this.pathPath+"Scripts\\;\n";
				sc = new Scanner(file);
				while(sc.hasNextLine()) {
					content += sc.nextLine()+"\n";
				}
				fw = new FileWriter(file);
				fw.write(content);
				fw.close();
				content = "";
			sc.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	   * This method should be place in a separate file, prob. as static method bc no params
	   * <p>
	   * A better way to implement this code could be calling the comun processBuilder(String params = null, String nameFile)
	   * method
	   */
	public void createCondaEnvironment() {
				
		
		try {
			
			ProcessBuilder processBuilder =	new ProcessBuilder(this.resourcesPath+"Bats\\create.bat");
			Process process = processBuilder.start();
			//Process process = Runtime.getRuntime().exec(this.resourcesPath+"Bats\\create.bat");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( process.getInputStream() ));

			String s;
			Controller.printOutputBat("");
			while ((s = stdInput.readLine()) != null) {
				Controller.printOutputBat(s);
			}
			Controller.printOutputBat("");
			
		} catch (IOException e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	   * This method should be place in a separate file, in a way that allow work on multithreading.
	   * <p>
	   * A better way to implement this code could be make multiples method for params-logic and then
	   * all of them calling  a single processBuilder(String params, String nameFile)
	   */	
	public void read(String [][] parameters) {
		
		this.videoPath = parameters[0][0];
		String champions = "";
		for (String champ : parameters[1]) {
			champions += "#"+champ;
		}
		
		try {				
			Process process = Runtime.getRuntime().exec(this.resourcesPath+"Bats\\read.bat "+this.videoPath+" "+champions+" "+parameters[2][0]+" "+parameters[2][1]+" "+parameters[2][2]+" "+parameters[2][3]+" "+this.jsonPath);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( process.getInputStream() ));

			String s;
			Controller.printOutputBat("");
			while ((s = stdInput.readLine()) != null) {
				Controller.printOutputBat(s);
			}
			Controller.printOutputBat("");
			
		} catch (IOException e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
 	   * @see Model#read(String[][])
	   * @param parameters
	   */
	public void flatPlot(String [][] parameters) {
		
		String champions = "";
		for (String champ : parameters[0]) {
			champions += "#"+champ;
		}
		
		try {				
			Process process = Runtime.getRuntime().exec(this.resourcesPath+"Bats\\flatPlot.bat "+" "+this.rootPath+" "+champions+" "+parameters[1][0]);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( process.getInputStream() ));

			String s;
			Controller.printOutputBat("");
			while ((s = stdInput.readLine()) != null) {
				Controller.printOutputBat(s);
			}
			Controller.printOutputBat("");
			
		} catch (IOException e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	   * @see Model#read(String[][])
	   * @param parameters
	   */
	public void threedPlot(String [][] parameters) {
		
		String champions = "";
		for (String champ : parameters[0]) {
			champions += "#"+champ;
		}
		
		try {				
			Process process = Runtime.getRuntime().exec(this.resourcesPath+"Bats\\3dPlot.bat "+" "+this.rootPath+" "+champions+" "+parameters[1][0]);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( process.getInputStream() ));
	
			String s;
			Controller.printOutputBat("");
			while ((s = stdInput.readLine()) != null) {
				Controller.printOutputBat(s);
			}
			Controller.printOutputBat("");
			
		} catch (IOException e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	   * @see Model#read(String[][])
	   * @param parameters
	   */
	public void writeVideo(String [][] parameters) {
		
		String videoPath = parameters[0][0];
		String title = parameters[1][0];
		
		try {				
			Process process = Runtime.getRuntime().exec(this.resourcesPath+"Bats\\write.bat "+" "+this.rootPath+" "+videoPath+" "+title);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( process.getInputStream() ));
	
			String s;
			Controller.printOutputBat("");
			while ((s = stdInput.readLine()) != null) {
				Controller.printOutputBat(s);
			}
			Controller.printOutputBat("");
			
		} catch (IOException e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
