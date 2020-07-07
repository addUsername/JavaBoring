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
		
	public Paths paths;
	/**
	   * Constructor from user input, instantiates {@link com.mycompany.prueba.model.Paths} class.
	   */
	public Model() {
		
		this.paths = new Paths();
	}
	/**
	   * This method writes custom lines on bats files by calling {@link Model#writePyPath()} and
	   * {@link Model#writeConda()} methods
	   * from user input
	   * @param String Represents anaconda installation path
	   */
	public void prepareBats(String pathPath) {
		
		paths.setPathPath(pathPath);
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
		File bats = new File (paths.getResourcesPath()+"Bats\\");
		
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
					add += paths.getPathPath()+"python.exe " + paths.getResourcesPath() + "pys\\readVideo.py %path% %champions% %threshold% %second_inicial% %frame_step% %frame_stop% %json_path%";
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
					add += paths.getPathPath()+"python.exe " + paths.getResourcesPath() + "pys\\flatPlot.py %path% %champions% %title%";
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
					add += paths.getPathPath()+"python.exe "+ paths.getResourcesPath() + "pys\\3dPlot.py %path% %champions% %title%";
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
					add += paths.getPathPath()+"python.exe "+ paths.getResourcesPath() + "pys\\writeVideo.py %rootpath% %videopath% %title%";
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
	   *This method write conda activate [env] function on each .bat
	   */	
	public void writeConda() {		
		
		File bats = new File (paths.getResourcesPath()+"Bats\\");
		FileWriter fw;
		Scanner sc;
		String content;
		
		try {
			for (File file: bats.listFiles()) {
				content = "@echo activando entorno virtual\n" + 
						  "@call "+paths.getPathPath()+"Scripts\\activate " + isBaseEnvironment(file.getName());
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
	 * This method should be a lambda inside {@link Model#writeConda()}. its return the name of the conda environment to activate
	 * @param name File's name, we just need (base) environment in create.bat
	 * @return String
	*/
	private String isBaseEnvironment(String name) {
		
		return (name.equals("create.bat"))? "\n":"myenv\n";
	}
	/**
	   * This method runs create.bat
	   * <p>
	   *  which create a conda environment from ..\resources\condaEnv\conda-env.txt 
	   * A better way to implement this code could be calling the comun processBuilder(this.resourcesPath, "create.bat" + params) method
	   */
	public void createCondaEnvironment() {
				
		
		try {//+" "+this.resourcesPath+"condaEnv\\conda-env.txt"
			
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\create.bat"+" "+paths.getResourcesPath()+"condaEnv\\conda-env.txt");
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
	   * This (and below) method should be place in a separate file, in a way that allow work on multithreading thing.
	   * (.py files should be rewrite, not much..)
	   * <p>
	   * A better way to implement this code could be make multiples methods for params-logic and then
	   * all of them calling  a single processBuilder(String params, String nameFile), yep
	   */	
	public void read(String [][] parameters) {
		
		paths.setVideoPath(parameters[0][0]);
		String champions = "";
		for (String champ : parameters[1]) {
			champions += "#"+champ;
		}
		
		try {				
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\read.bat "+paths.getVideoPath()+" "+champions+" "+parameters[2][0]+" "+parameters[2][1]+" "+parameters[2][2]+" "+parameters[2][3]+" "+paths.getJsonPath());
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
				
		try {				
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\flatPlot.bat "+" "+paths.getRootPath()+" "+parameters[0][0]);
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
				
		try {				
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\3dPlot.bat "+" "+paths.getRootPath()+" "+parameters[0][0]);
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
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\write.bat "+" "+paths.getRootPath()+" "+videoPath+" "+title);
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
