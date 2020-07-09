package com.mycompany.prueba.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import com.mycompany.prueba.controller.Controller;

/**
 * <h1>ModelThreads</h1>
 * This class handles fast reading feature by creating 4 {@link com.mycompany.prueba.model.ReadThread} Objects.
 * <p>
 * <h4>For scaling up performance..</h4>
 * <ul><li>Same_bat_multiple_times.java: (DONE)</li><li>Multiple_bat_at_once.java: synchronized threads making the whole task of reading and writting video, need streams</li></ul>
 * 
 * @author SERGI
 * @version 1.5
 * @since   2020-07-7
 */
public class ModelThreads extends Model {

	volatile CountDownLatch latch;
	
	public ModelThreads() {
		super();
	}
	/**
	 * By now all others methods from {@link Model} super Class remain  the same.. we are not gonna call them anywhere, bat and runtime logic not change
	 * <p>
	 * <h4>Fun part</h4>
	 * This method instantiates 4 {@link ReadThread} Objects as Threads, then {@link Thread#start()} them and finally {@link Thread#join()},
	 * as usual, but there are some tricky differences like running the same instance {@link Runtime} or reading the same file concurrently and
	 * nothing bad happens. Good job java!.. pretty cool. Also 0 modifications done on read.py, it works on both models from the beginning, I'd take the last one(H).
	 */
	@Override
	public void read(String [][] parameters) {
		//threshold, Segundo_inicial,frame_step, segundo_final
		paths.setVideoPath(parameters[0][0]);
		String champions = "";
		for (String champ : parameters[1]) {
			champions += "#"+champ;
		}
		
		String threshold = parameters[2][0];
		int segundo_inicial= Integer.parseInt(parameters[2][1]);
		String frame_step = parameters[2][2];
		int segundo_final = Integer.parseInt(parameters[2][3]);
		
		int totalSeg = (segundo_final - segundo_inicial);
		int a = (totalSeg/4);
		double seg1Frame = 1/30;
		
		//Watch out
		this.latch = new CountDownLatch(4);
		
		Thread thread1 =new Thread( new ReadThread(latch,1,champions,threshold,segundo_inicial,frame_step,segundo_inicial+a,paths));
		Thread thread2 =new Thread( new ReadThread(latch,2,champions,threshold,segundo_inicial+a+seg1Frame,frame_step,segundo_inicial+2*a,paths));
		Thread thread3 =new Thread( new ReadThread(latch,3,champions,threshold,segundo_inicial+2*a+seg1Frame,frame_step,segundo_inicial+3*a,paths));
		Thread thread4 =new Thread( new ReadThread(latch,4,champions,threshold,segundo_inicial+3*a+seg1Frame,frame_step,segundo_final,paths));
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		try {
			thread2.join();
			thread3.join();
			thread4.join();
			thread1.join();
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			//prop not needed
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		joinJsons();
		
	}
	/**
	 * Python will handle this..
	 */
	public void joinJsons() {
		
		try {			
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\join.bat"+" "+paths.getJsonPath());
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
