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

	public static boolean isAutonomous;
	public static final boolean isAutoClimb = false;
	private static final int kShotAngleAdjust = 1;
	public static boolean doShoot() {
		if(!doSpin()) {
			return false;
		}
		if(isAutonomous) {
			return Data.ai.functionValues[AIDriver.shootingIndex];
		}
		if(SmartDashboard.getBoolean("IsFPR")) {
			return ((int)(SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
		}
		return Hardware.auxJoystick.getTrigger();

	}

	public static boolean doSpin() {
		if(isAutonomous) {
			return Data.ai.functionValues[AIDriver.spinningIndex];
		}
		if(SmartDashboard.getBoolean("IsFPR")) {
			return ((int)(SmartDashboard.getNumber("MouseButtons")) & 2) == 2;
		}
		return Hardware.auxJoystick.getRawButton(5) || Hardware.auxJoystick.getRawButton(4);

	}

	public static double shotAngle() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return -SmartDashboard.getNumber("MouseYVelocity") / 7.0;
		}
		double num = 0;
		if(!isAutonomous) {
			num += Hardware.auxJoystick.getRawButton(2) ? -kShotAngleAdjust : 0;
			num += Hardware.auxJoystick.getRawButton(3) ? kShotAngleAdjust : 0;
		}
		if(num == 0 || isAutonomous) {
			num += Data.ai.functionValues[AIDriver.aimDownIndex] ? -kShotAngleAdjust : 0;
			num += Data.ai.functionValues[AIDriver.aimUpIndex] ? kShotAngleAdjust : 0;
		}
		return num;
	}
/*
	public static boolean shouldStartClimb() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return false;//@TODO IMPLEMENT ME!
		}
		if(isAutonomous) {
			return Data.ai.functionValues[AIDriver.beginClimbIndex];
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
			num += Data.ai.functionValues[AIDriver.climbExtendIndex] ? kShotAngleAdjust : 0;
			num += Data.ai.functionValues[AIDriver.climbIndex] ? -kShotAngleAdjust : 0;
		}
		return num;
	}
*/
	public static double driveX() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return (((int)SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? -0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? 0.5 : 0;
		}
		if(isAutonomous) {
			return Data.ai.driveX;
		}
		int num = 0;
		if(Hardware.driveJoystick.getRawButton(4)) {
			num += -1;
		}
		if(Hardware.driveJoystick.getRawButton(5)) {
			num += 1;
		}
		if(isAbsolute()) {
			return driveZ() * num;
		}
		return Hardware.driveJoystick.getX()*Math.abs(Hardware.driveJoystick.getX())*driveZ();
	}

	public static double driveY() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -0.5 : 0;
		}
		if(isAutonomous) {
			return Data.ai.driveY;
		}
		int num = 0;
		if(Hardware.driveJoystick.getRawButton(3)) {
			num += -1;
		}
		if(Hardware.driveJoystick.getRawButton(2)) {
			num += 1;
		}
		if(isAbsolute()) {
			return driveZ() * num;
		}
		return Hardware.driveJoystick.getX()*Math.abs(Hardware.driveJoystick.getX())*driveZ();
	}
	private static boolean isAbsolute(){
	    return Hardware.driveJoystick.getRawButton(2)||Hardware.driveJoystick.getRawButton(3)
		    ||Hardware.driveJoystick.getRawButton(4)||Hardware.driveJoystick.getRawButton(5);
	}
	public static double driveRotation() {
		if(SmartDashboard.getBoolean("IsFPR")&&!isAutonomous) {
			return SmartDashboard.getNumber("MouseXVelocity") / 14.0;
		}
		double num = 0;
		if(isAutonomous) {
			num = Data.ai.driveRot;
		}
		else {
			num += Hardware.driveJoystick.getRawButton(8) ? -1 : 0;
			num += Hardware.driveJoystick.getRawButton(9) ? 1 : 0;
			num *= driveZ();
		}
		return num;
	}
	public static String logString(boolean Autonomous) {
	    boolean isClimbing=false&&!Autonomous;
		String xmlString = Autonomous ? "<controlScheme mode=\"autonomous\">\n" : "<controlScheme mode=\"teleop\">\n";
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

	public static double driveZ() {
		return (Hardware.driveJoystick.getZ() + 1) / 2;
	}
	/*
	 * public static AbsoluteDirection perfectStrafe()
	 * {
	 * if(Hardware.driveJoystick.getRawButton(3))
	 * {
	 * return AbsoluteDirection.FORWARD;
	 * }
	 * if(Hardware.driveJoystick.getRawButton(2))
	 * {
	 * return AbsoluteDirection.BACKWARD;
	 * }
	 * if(Hardware.driveJoystick.getRawButton(4))
	 * {
	 * return AbsoluteDirection.STRAFE_LEFT;
	 * }
	 * if(Hardware.driveJoystick.getRawButton(5))
	 * {
	 * return AbsoluteDirection.STRAFE_RIGHT;
	 * }
	 * return AbsoluteDirection.UNSPECIFIED;
	 * }
	 * public static boolean driveRotateCCW()
	 * {
	 * return Hardware.driveJoystick.getRawButton(8);
	 * }
	 * public static boolean driveRotateCW()
	 * {
	 * return Hardware.driveJoystick.getRawButton(9);
	 * }
	 * static final class AbsoluteDirection{
	 * public static final AbsoluteDirection FORWARD = new AbsoluteDirection(0);
	 * public static final AbsoluteDirection BACKWARD = new
	 * AbsoluteDirection(1);
	 * public static final AbsoluteDirection STRAFE_LEFT = new
	 * AbsoluteDirection(2);
	 * public static final AbsoluteDirection STRAFE_RIGHT = new
	 * AbsoluteDirection(3);
	 * public static final AbsoluteDirection UNSPECIFIED = new
	 * AbsoluteDirection(5);
	 * public static final int DIR_FORWARD = 0;
	 * public static final int DIR_BACKWARD = 1;
	 * public static final int DIR_STRAFE_LEFT = 2;
	 * public static final int DIR_STRAFE_RIGHT = 3;
	 * public static final int ERROR_UNSPECIFIED = 5;
	 * private int id = 0;
	 * private AbsoluteDirection(int id){this.id=id;}
	 * }
	 * public static boolean doShoot()
	 * {
	 * if(SmartDashboard.getBoolean("IsFPR")){
	 * return ((int)(SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
	 * }
	 * return Hardware.auxJoystick.getTrigger();
	 * }
	 * public static boolean doShooterSpin()
	 * {
	 * if(SmartDashboard.getBoolean("IsFPR")){
	 * return ((int)(SmartDashboard.getNumber("MouseButtons")) & 2) == 2;
	 * }
	 * return Hardware.auxJoystick.getRawButton(4) ||
	 * Hardware.auxJoystick.getRawButton(5);
	 * }
	 * public static double angleUp()
	 * {
	 * if(SmartDashboard.getBoolean("IsFPR")){
	 * return 0;//Math.max(0,SmartDashboard.getNumber("MouseYVelocity")/14.0);
	 * }
	 * return Hardware.auxJoystick.getRawButton(3) ? 1 : 0;
	 * }
	 * public static double angleDown()
	 * {
	 * if(SmartDashboard.getBoolean("IsFPR")){
	 * return SmartDashboard.getNumber("MouseYVelocity")/7.0;
	 * }
	 * return Hardware.auxJoystick.getRawButton(2) ? 1 : 0;
	 * }
	 * public static double strafeY(){
	 * if(SmartDashboard.getBoolean("IsFPR")){
	 * return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 0.5 :
	 * ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -0.5 : 0;
	 * }
	 * return Hardware.driveJoystick.getY();
	 * }
	 * public static double strafeX(){
	 * if(SmartDashboard.getBoolean("IsFPR")){
	 * return (((int)SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? 0.5 :
	 * ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? -0.5 : 0;
	 * }
	 * return Hardware.driveJoystick.getX();
	 * }
	 *
	 */

	public static double getZ() {
		return Hardware.driveJoystick.getZ()* (Hardware.driveJoystick.getTrigger()?.5:1);
	}

	public static boolean tiltRobot() {
		if(isAutonomous) {
			return false;
		}
		if(SmartDashboard.getBoolean("IsFPR")) {
			return false;//@TODO IMPLEMENT ME!
		}
		return Hardware.auxJoystick.getRawButton(-1);//@TODO Pick A button
	}
	/**
	 * We don't need to switch gears anymore, CIM Motor is fast enough
	 * @deprecated 
	 * @return 
	 */
	public static boolean switchGears() {
		return false; //@TODO implement
	}

	public static boolean doClimb() {
		if(isAutonomous) {
			return false;
		}
		if(SmartDashboard.getBoolean("IsFPR")) {
			return false;//@TODO IMPLEMENT ME!
		}
		return Hardware.auxJoystick.getRawButton(-1);//@TODO Pick A button
	}
}
