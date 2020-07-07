/**
 * 
 */
package com.mycompany.prueba.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import com.mycompany.prueba.controller.Controller;

/**<h1>ReadThread</h1>
 * This object aims to generate a partial .json file while 4 (in total) instances are doing the same, then this json will be pasted by {@link }
 * and place it on normal json path.
 * @author SERGI
 * @since   2020-07-7
 *
 */
public class ReadThread extends Thread {

	/**
	 * This volatile CountDownLatch variable I think that is not necessary or maybe the {@link Thread#join()} in {@link ModelThreads#read(String[][])} 
	 * is the redundant part.. idk, works pretty well, even reading the same file simultaneously, min x2 faster than regular reading (85s vs 175s), the 
	 * more seconds it reads, the more performance difference it presents.
	 */
	private volatile CountDownLatch latch;
	private String threshold;
	private double segundo_inicial;
	private String frame_step;
	private int segundo_final;
	private int numThread;
	private String champions;
	private Paths paths;
	/**
	 * @param latch Cool thingy thing, maybe not necessary but pretty useful to know, comes from {@link Controller} Class
	 */	
	public ReadThread(CountDownLatch latch, int numThread, String champions, String threshold, double segundo_inicial, String frame_step, int segundo_final, Paths paths) {
		
		this.threshold = threshold;
		this.segundo_inicial = segundo_inicial ;
		this.frame_step = frame_step;
		this.segundo_final = segundo_final;
		this.champions =  champions;
		this.paths = paths;
		this.numThread = numThread;
		this.latch = latch;
	}
	/**
	 * Simple run Method, also handles output coming from python
	 */
	@Override
	public void run() {
		String video = paths.getVideoPath();
		try {				
			Process process = Runtime.getRuntime().exec(paths.getResourcesPath()+"Bats\\read.bat "+video+" "+champions+" "+
					this.threshold+" "+this.segundo_inicial+" "+this.frame_step+" "+this.segundo_final+" "+paths.getJsonPath_temp());
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( process.getInputStream() ));

			String s;
			Controller.printOutputBat("");
			while ((s = stdInput.readLine()) != null) {
				Controller.printOutputBat("from thread: "+this.numThread+" -> "+s);
			}
			Controller.printOutputBat("latch.countDown(); on  thread: "+this.numThread);
			latch.countDown();
			
		} catch (IOException e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
