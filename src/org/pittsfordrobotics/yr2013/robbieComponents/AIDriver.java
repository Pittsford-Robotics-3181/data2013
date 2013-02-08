/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.robbieComponents;
import org.pittsfordrobotics.yr2013.*;
/**
 *
 * @author robbiemarkwick
 */
public class AIDriver {
    

    public boolean[] functionValues = new boolean[9];
    public double driveX;
    public double driveY;
    private boolean movingArmUp = true;

    /*/indexes of arrays for labeled controls*/
    private static final int shootingIndex = 0; //launching a disk
    private static final int spinningIndex = 1; //spinning shooter wheels
    private static final int driveLeftSideIndex = 2;//rotate left
    private static final int driveRightSideIndex = 3;//rotate right
    private static final int aimUpIndex = 10;//aim shooter up
    private static final int aimDownIndex = 11;//aim shooter down
    private static final int beginClimbIndex = 6;//set up for climbing
    private static final int climbIndex = 7;//pull the robot up
    private static final int climbExtendIndex = 8;//extend the climbing arm, might be automated
    
    /**
     * LIAM: this is the method you can implement for automatic aiming
     */
    public void drive() {
	/*
	 * To simulate values:
	 * Buttons: if the button should be pushed, set its value in functionValues to true, otherwise false
	 * driveing joystick: driveX and driveY are the X and Y of the drive vector
	 * Function constants are listed above
	 */
	//Automatic driveSystem
	//Automatic Shooter Aim
	//Auto-Drive Climber
	boolean newValue = movingArmUp;
	if (Hardware.upSwitch.get()) {
	    newValue = false;
	} else if (Hardware.downSwitch.get()) {
	    newValue = true;
	}
	if (newValue != movingArmUp) {
	    movingArmUp = newValue;
	    functionValues[climbIndex] = false;
	    functionValues[climbExtendIndex] = false;
	} else {
	    functionValues[climbIndex] = !movingArmUp;
	    functionValues[climbExtendIndex] = movingArmUp;
	}
    }
}
