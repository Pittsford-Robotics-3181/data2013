/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.Timer;

/**
 * This class acts as a way for the AI code to interact with the Components (via ControlScheme)
 * @author robbiemarkwick
 */
public class AIJoystick {
	
	 static double driveX;//X value for driving
	 static double driveY;//Y value for driving
	 static double driveRot;//Rotation value for driving 
	 static double shooterAim; //The adjustment the shooter should be making to its angle
	 static boolean shooterShoot;//Should the shooter be shooting
	 static boolean shooterSpin;//Should the shooter be spinning its wheels
	
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
	 * shoots a disc
	 */
	public static void shoot(){
	    shooterShoot=true;
	    //leave it on for 1/2 second and turn it off
	    Thread thread=new Thread(){
		public void run(){
		    Timer.delay(.5);
		    shooterShoot=false;
		}
	    };
	    thread.start();
	}
	/**
	 * adjust how the Robot Drives
	 * @param x X value
	 * @param y Y value
	 * @param rot Rotation
	 */
	public static void setDriveVals(double x,double y, double rot){
	    driveX=x;
	    driveY=y;
	    driveRot=rot;
	}
	/**
	 * Adjust the shooter's Angle
	 * @param upVal the amount it should aim up or down (+ for up, - for down)
	 */
	public static void aimShooter(double upVal){
	    shooterAim=upVal;
	}
}