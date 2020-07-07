/**
 * 
 */
package com.mycompany.prueba.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import com.mycompany.prueba.controller.Controller;

/**
 * <h1>ModelThreads</h1>
 * This class handles fast reading. by creating 4  {@link com.mycompany.prueba.model.ReadThread} Objects.
 * <p>
 * Also this package is wrong, we need min. 4 files:
 * 1 Paths.java (just getters and setters), 2 ProcessBats.java, 3 WriteBats.java and Model.java
 * <p>
 * For scaling up performance..<ul> <li>Same_bat_multiple_times.java: (WORKING ON)<li>
 * 							<li>Multiple_bat_at_once.java: synchronized threads making the whole task of reading and writting video)<li> <ul>
 * @author SERGI
 * @version 1.0
 * @since   2020-07-7
 */
public class ModelThreads extends Model {

	volatile CountDownLatch latch;
	
	public ModelThreads() {
		super();
	}
	/**
	 * All others methods remain the same.. we are not gonna call it here
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
		
		//vale hay que pasar frames. o NO. y modificar el .py.. xd, a parte crear otro para que junte los 4 json
		//runnable interface no tiene join.. usamos este objeto, cada hilo llamara a latch.countDown() latch.await();
		this.latch = new CountDownLatch(4);
		
		ReadThread thread1 = new ReadThread(latch,1,champions,threshold,segundo_inicial,frame_step,segundo_inicial+a,paths);
		ReadThread thread2 = new ReadThread(latch,2,champions,threshold,segundo_inicial+a+seg1Frame,frame_step,segundo_inicial+2*a,paths);
		ReadThread thread3 = new ReadThread(latch,3,champions,threshold,segundo_inicial+2*a+seg1Frame,frame_step,segundo_inicial+3*a,paths);
		ReadThread thread4 = new ReadThread(latch,4,champions,threshold,segundo_inicial+3*a+seg1Frame,frame_step,segundo_final,paths);
		
		thread1.run();
		thread2.run();
		thread3.run();
		thread4.run();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void joinJsons() {
		
	}
	
}
