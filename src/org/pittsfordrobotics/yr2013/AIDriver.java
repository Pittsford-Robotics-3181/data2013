/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author robbiemarkwick
 */
public class AIDriver {

	 static double driveX;
	 static double driveY;
	 static double driveRot;
	 static double shooterAim;
	 static boolean shooterShoot;
	 static boolean shooterSpin;
	
	/**
	  * Spins the shooting Wheel
	  */
	public static void spin(){
	    shooterShoot=false;
	}
	/**
	 * Stops the wheel from spinning
	 */
	public static void stopSpining(){
	    shooterSpin=false;
	}
	/**
	 * shoots
	 */
	public static void shoot(){
	    shooterShoot=true;
	    Thread thread=new Thread(){
		public void run(){
		    Timer.delay(.5);
		    shooterShoot=false;
		}
	    };
	    thread.start();
	}
	public static void setDriveVals(double x,double y, double rot){
	    driveX=x;
	    driveY=y;
	    driveRot=rot;
	}
	public static void aimShooter(double upVal){
	    shooterAim=upVal;
	}
}