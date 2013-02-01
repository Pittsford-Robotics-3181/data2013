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
    public boolean[] functionValues=new boolean[9];
    public double driveX;
    public double driveY;
    private boolean movingArmUp=true;
    /**
     * LIAM: this is the method you can implement for automatic aiming
     */
    public void drive(){
	/*
	 * To simulate values:
	 * Buttons: if the button should be pushed, set its value in functionValues to true, otherwise false
	 * driveing joystick: driveX and driveY are the X and Y of the drive vector
	 * Function constants are found in ControlScheme
	 */
	//Automatic driving
	//Automatic Shooter Aim
	//Auto-Drive Climber
	boolean newValue=movingArmUp;
	if(Hardware.upSwitch.get())newValue=false;
	else if(Hardware.downSwitch.get())newValue=true;
	if(newValue!=movingArmUp){
	    movingArmUp=newValue;
	    functionValues[ControlScheme.climbIndex]=false;
	    functionValues[ControlScheme.climbExtendIndex]=false;
	}
	else{
		 functionValues[ControlScheme.climbIndex]=!movingArmUp;
		 functionValues[ControlScheme.climbExtendIndex]=movingArmUp;
	    
	}
	
	
    }
    
}
