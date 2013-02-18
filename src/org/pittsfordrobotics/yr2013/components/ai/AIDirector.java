/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components.ai;

import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class AIDirector extends Thread{
	private Performable[] actionQueue = new Performable[10];
	private boolean running = true;
	private Performable[] arraySizeChange(Performable[] src, int changeBy){
		Performable[] newArray = new Performable[src.length+changeBy];
		System.arraycopy(src, 0, newArray, 0, Math.min(src.length,newArray.length));
		return newArray;
	}
	public void queueAction(Performable action){
		if(action == null){
			throw new NullPointerException();
		}
		if(actionQueue[actionQueue.length-1] != null){
			actionQueue = arraySizeChange(actionQueue,10);
		}
		for(int i = actionQueue.length-1; i >= 0; i++){
			if(actionQueue[i] != null){
				actionQueue[i+1] = action;
				i = -1;
			}
		}
	}
	public void stop(){
		running=false;
	}
	public void run(){
		while(running){
			if(actionQueue[0] != null){
				actionQueue[0].performAction();
				System.arraycopy(actionQueue, 1, actionQueue, 0, actionQueue.length-1);
			}
			Timer.delay(0.005);
		}
	}
}
