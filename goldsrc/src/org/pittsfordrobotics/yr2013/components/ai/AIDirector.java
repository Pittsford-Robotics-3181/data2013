/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components.ai;

//import edu.wpi.first.wpilibj.Timer;

import java.util.Timer;


/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class AIDirector extends Thread {

	private ActionQueue actionQueue = new ActionQueue(128);
	private boolean running = true;
	public AIDirector(){
		start();
	}
	public void queueAction(Performable action) {
		if(action == null) {
			throw new NullPointerException();
		}
		actionQueue.insert(action);
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while(running) {
			try{
			actionQueue.remove().performAction();
			} catch(NullPointerException npe){}
			try {
				Thread.sleep(5);
			}
			catch(InterruptedException ex) {}
		}
	
}
class ActionQueue{
    protected Performable[] array;
    protected int start,end;
    protected boolean full;

    public ActionQueue(int maxsize){
        array = new Performable[maxsize];
        start = end = 0;
        full = false;
    }

    public boolean isEmpty(){
        return ((start == end) && !full);
    }

    public void insert(Performable o){
        if(!full) {
			array[start = (++start % array.length)] = o;
		}
        if(start == end) {
			full = true;
		}
    }

    public Performable remove(){
        if(full) {
			full = false;
		}
        else if(isEmpty()) {
			return null;
		}
        return array[end = (++end % array.length)];
    }
}
	}