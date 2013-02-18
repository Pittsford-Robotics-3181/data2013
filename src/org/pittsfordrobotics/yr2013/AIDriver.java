/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

/**
 *
 * @author robbiemarkwick
 */
public class AIDriver {

	public boolean[] functionValues = new boolean[7];
	public double driveX;
	public double driveY;
	public double driveRot;
	private boolean movingArmUp = true;

	/*
	 * /indexes of arrays for labeled controls
	 */
	public static final int shootingIndex = 0; //launching a disk
	public static final int spinningIndex = 1; //spinning shooter wheels
	public static final int aimUpIndex = 2;//aim shooter up
	public static final int aimDownIndex = 3;//aim shooter down
	public static final int beginClimbIndex = 4;//set up for climbing
	public static final int climbIndex = 5;//pull the robot up
	public static final int climbExtendIndex = 6;//extend the climbing arm, might be automated

	/**
	 * LIAM: this is the method you can implement for automatic aiming
	 */
	public void drive() {
		/*
		 * To simulate values:
		 * Buttons: if the button should be pushed, set its value in
		 * functionValues to true, otherwise false
		 * driveing joystick: driveX and driveY are the X and Y of the drive
		 * vector
		 * Function constants are listed above
		 */
		//Automatic driveSystem
		//Automatic Shooter Aim
		//Auto-Drive Climber
	/*
		 * boolean newValue = movingArmUp;
		 * if (Hardware.upSwitch.get()) {
		 * newValue = false;
		 * } else if (Hardware.downSwitch.get()) {
		 * newValue = true;
		 * }
		 * if (newValue != movingArmUp) {
		 * movingArmUp = newValue;
		 * functionValues[climbIndex] = false;
		 * functionValues[climbExtendIndex] = false;
		 * } else {
		 * functionValues[climbIndex] = !movingArmUp;
		 * functionValues[climbExtendIndex] = movingArmUp;
		 * }
		 */
	}
}
