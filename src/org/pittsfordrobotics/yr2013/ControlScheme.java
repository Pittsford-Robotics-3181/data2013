
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 * @author liammiddlebrook
 */
public class ControlScheme {

	public static boolean isAutonomous; //Is the Robot autnomous
	/**
	 * should the robot be shooting
	 * @return true if the robot should launch a disk
	 */
	public static boolean doShoot() {
		if(!doSpin()) {//If we are not spinning wheels, we can't shoot
			return false;
		}
		if(isAutonomous) {//If we are in autnomous, read from the AI
			return AIJoystick.shooterShoot;
		}
		if(SmartDashboard.getBoolean("IsFPR")) {//Return value from FPR if that is the case
			return ((int)(SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
		}
		return Hardware.auxJoystick.getTrigger();//Read from Auxiliary Joystick Trigger

	}
	/**
	 * should the shooter wheel spin
	 * @return true if the robot should spin the shooter wheel
	 */
	public static boolean doSpin() {
		if(isAutonomous) {
			return AIJoystick.shooterSpin;//If we are in Autonomous, read from the AI
		}
		if(SmartDashboard.getBoolean("IsFPR")) {
			return ((int)(SmartDashboard.getNumber("MouseButtons")) & 2) == 2;//Read from FPR
		}
		return Hardware.auxJoystick.getRawButton(5) || Hardware.auxJoystick.getRawButton(4);//Button 4 or 5 on Auxiliary Joystick

	}
	/**
	 * What direction should the shooter be aimed in
	 * On Auxiliary Joystick: Button 2 is down, Button 3 is up, they can cancel each other
	 * @return 1 if it should move up, -1 if it should move down, 0 if it should stay still
	 */
	public static double shotAngle() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return -SmartDashboard.getNumber("MouseYVelocity") / 7.0;//Read from FPR
		}
		double num = 0;
		if(!isAutonomous) {
			num += Hardware.auxJoystick.getRawButton(2) ? -1 : 0;//Button 2 on Auxiliary Joystick is down
			num += Hardware.auxJoystick.getRawButton(3) ? 1 : 0;//Button 3 on Auxiliary Joystick is up
		}
		if(num == 0 || isAutonomous) {
			return AIJoystick.shooterAim;//If autonomous or driver isn't aiming, AI will aim
		}
		return num;
	}
/*
	public static boolean shouldStartClimb() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return false;//@TODO IMPLEMENT ME!
		}
		if(isAutonomous) {
			return Data.ai.functionValues[AIJoystick.beginClimbIndex];
		}
		return Hardware.driveJoystick.getTrigger();
	}

	public static double climbDir() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return 0;//@TODO IMPLEMENT ME!
		}
		/*
		 * double num=0;
		 * num+=valueForButtonOnJoystick(joystickMap[climbIndex],buttonMap[climbIndex])?-1:0;
		 * if(isAutonomous)
		 * num+=Hardware.aiDriver.functionValues[climbExtendIndex]?1:0;
		 * else
		 * num+=valueForButtonOnJoystick(joystickMap[climbExtendIndex],buttonMap[climbExtendIndex])?1:0;
		 * return num;
		 *
		double num = 0;
		if(!isAutoClimb) {
			num += Hardware.auxJoystick.getRawButton(5) ? kShotAngleAdjust : 0;
			num += Hardware.auxJoystick.getRawButton(3) ? -kShotAngleAdjust : 0;
		}
		if(isAutoClimb && Hardware.auxJoystick.getRawButton(3)) {
			num += Data.ai.functionValues[AIJoystick.climbExtendIndex] ? kShotAngleAdjust : 0;
			num += Data.ai.functionValues[AIJoystick.climbIndex] ? -kShotAngleAdjust : 0;
		}
		return num;
	}
*/
	/**
	 * How much should the Robot drive in the X direction
	 * Drive Joystick Button 5 is right, Button 4 is left
	 * They can cancel each other
	 * if it isn't in an absolute direction, the joystick itself is read
	 * value is multiplied by Z value if joystick derived
	 * @return X value for Cartesian drive
	 */
	public static double driveX() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {//Read from FPR
			return (((int)SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? -0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? 0.5 : 0;
		}
		if(isAutonomous) {
			return AIJoystick.driveX;//Read from Autnomous
		}
		int num = 0;
		if(Hardware.driveJoystick.getRawButton(4)) {
			num += -1;
		}
		if(Hardware.driveJoystick.getRawButton(5)) {
			num += 1;
		}
		if(isAbsolute()) {//Return value from Absolute direction
			return driveZ() * num;
		}
		return Hardware.driveJoystick.getX()*Math.abs(Hardware.driveJoystick.getX())*driveZ();//Return Sign-Preserved Square Value from Joystick
	}
	/**
	 * How much should the Robot drive in the X direction
	 * Drive Joystick Button 3 is Forward, Button 2 is Backward
	 * They can cancel each other
	 * if it isn't in an absolute direction, the joystick itself is read
	 * value is multiplied by Z value if joystick derived
	 * @return Y value for Cartesian drive
	 */
	public static double driveY() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -0.5 : 0;
		}
		if(isAutonomous) {
			return AIJoystick.driveY;//Read from Autnomous
		}
		int num = 0;
		if(Hardware.driveJoystick.getRawButton(2)) {
			num += -1;
		}
		if(Hardware.driveJoystick.getRawButton(3)) {
			num += 1;
		}
		if(isAbsolute()) {
			return driveZ() * num;//Return Value from Absolute direction
		}
		return Hardware.driveJoystick.getX()*Math.abs(Hardware.driveJoystick.getX())*driveZ(); //Return Sign-Preserved Square Value from Joystick
	}
	/**
	 * Small function to determine if the direction is an Absolute
	 * @return true if any of the absolute buttons are pressed
	 */
	private static boolean isAbsolute(){
	    return Hardware.driveJoystick.getRawButton(2)||Hardware.driveJoystick.getRawButton(3)
		    ||Hardware.driveJoystick.getRawButton(4)||Hardware.driveJoystick.getRawButton(5);
	}
	/**
	 * Rotation value for Driving
	 * Drive Joystick Button 8 is CCW, Button 9 is CW
	 * They can cancel each other
	 * value is multiplied by Z value if joystick derived
	 * @return 
	 */
	public static double driveRotation() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return SmartDashboard.getNumber("MouseXVelocity") / 14.0;//FPR
		}
		double num = 0;
		if(isAutonomous) {
			num = AIJoystick.driveRot;//AI Driver In Autonomus
		}
		else {
			num += Hardware.driveJoystick.getRawButton(8) ? -1 : 0;
			num += Hardware.driveJoystick.getRawButton(9) ? 1 : 0;
			num *= driveZ();
		}//Read from Buttons
		return num;
	}
	/**
	 * Logging Function
	 * @return XML String for Logs
	 */
	public static String logString() {
	    boolean isClimbing=false&&!isAutonomous;
		String xmlString = isAutonomous ? "<controlScheme mode=\"autonomous\">\n" : "<controlScheme mode=\"teleop\">\n";
		xmlString = xmlString.concat("<driveValues>\n"
									 + "<X>" + ControlScheme.driveX() + "</X>\n"
									 + "<Y>" + ControlScheme.driveY() + "</Y>\n"
									 + "<Rotation>" + ControlScheme.driveRotation() + "</Rotation>\n"
									 + "</driveValues>\n");
		xmlString = xmlString.concat("<shotValues>\n"
									 + "<adjustAngle>" + ControlScheme.shotAngle() + "</adjustAngle>\n"
									 + "<shouldSpin>" + (ControlScheme.doSpin() ? "true" : "false") + "</shouldSpin>\n"
									 + "<shouldFire>" + (ControlScheme.doShoot() ? "true" : "false") + "</shouldFire>\n"
									 + "</shotValues>\n");
		xmlString = xmlString.concat((!isClimbing)?"":"<climbValues>\n"
									 + "<shouldBegin>" + (ControlScheme.tiltRobot() ? "true" : "false") + "</shouldBegin>\n"
									 + "<climb>" +(ControlScheme.doClimb() ? "true" : "false") + "</climb>\n"
									 + "</climbValues>\n");
		xmlString = xmlString.concat("</controlSchme>");
		return xmlString;
	}
	/**
	 * Sensitivity of Joystick
	 * Little thingy at bottom of Drive Joystick
	 * Gets cut in half if Drive Trigger is pressed
	 * @return Scale value for Other Drive Functions
	 */
	public static double driveZ() {
		return ((Hardware.driveJoystick.getZ() + 1) / 2 )* (Hardware.driveJoystick.getTrigger()?.5:1);
	}
	/**
	 * @deprecated
	 * @return 
	 */
	public static double getZ() {
		return Hardware.driveJoystick.getZ()* (Hardware.driveJoystick.getTrigger()?.5:1);
	}
	/**
	 * Should the robot tilt itself for climbing
	 * Auxiliary Joystick Button _
	 * @return 
	 */
	public static boolean tiltRobot() {
		if(isAutonomous) {
			return false;//No climbing in Autnomous
		}
		if(SmartDashboard.getBoolean("IsFPR")) {//FPR
			return false;//@TODO IMPLEMENT ME!
		}
		return Hardware.auxJoystick.getRawButton(-1);//Read from a button@TODO Pick A button
	}
	/**
	 * We don't need to switch gears anymore, CIM Motor is fast enough
	 * @deprecated 
	 * @return 
	 */
	public static boolean switchGears() {
		return false; //@TODO implement
	}
	/**
	 * Should the Rot climb
	 * Auxiliary Joystick Button _
	 * Robot will handle all climbing work itself
	 * @return Should the Rot climb
	 */
	public static boolean doClimb() {
		if(isAutonomous) {
			return false;//No climbing in Autnonomous
		}
		if(SmartDashboard.getBoolean("IsFPR")) {//FPR
			return false;//@TODO IMPLEMENT ME!
		}
		return Hardware.auxJoystick.getRawButton(-1);//Joystick value @TODO Pick A button
	}
}