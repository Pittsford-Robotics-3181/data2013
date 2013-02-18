/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components.ai;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class Action implements Performable{
	private Performable perf;
	private long millisBefore, millisAfter;
	public Action(long millisBeforeAction, Performable toPerform, long millisAfterAction){
		perf=toPerform;
		millisBefore=millisBeforeAction;
		millisAfter=millisAfterAction;
	}
	public void performAction() {
		try {
			Thread.sleep(millisBefore);
		}
		catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		perf.performAction();
		try {
			Thread.sleep(millisAfter);
		}
		catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
}
