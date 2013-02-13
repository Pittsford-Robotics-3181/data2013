/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import org.pittsfordrobotics.yr2013.robbieComponents.AIDriver;

/**
 *
 * @author liammiddlebrook
 */
public class ControlScheme {
    /* public static AbsoluteDirection perfectStrafe()
    {
    if(Hardware.driveJoystick.getRawButton(3))
    {
    return AbsoluteDirection.FORWARD;
    }
    if(Hardware.driveJoystick.getRawButton(2))
    {
    return AbsoluteDirection.BACKWARD;
    }
    if(Hardware.driveJoystick.getRawButton(4))
    {
    return AbsoluteDirection.STRAFE_LEFT;
    }
    if(Hardware.driveJoystick.getRawButton(5))
    {
    return AbsoluteDirection.STRAFE_RIGHT;
    }
    return AbsoluteDirection.UNSPECIFIED;
    }*/

    public static boolean driveRotateCCW() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.driveRotateCCWIndex];
	}
	return Hardware.driveJoystick.getRawButton(8);
    }

    public static boolean driveRotateCW() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.driveRotateCWIndex];
	}
	return Hardware.driveJoystick.getRawButton(9);
    }/*
    public static double driveX(){
    if(ControlScheme.strafeX()!=0)return ControlScheme.strafeX();
    return Hardware.driveJoystick.getX();
    }
    public static double driveY(){
    if(ControlScheme.strafeY()!=0)return ControlScheme.strafeY();
    return Hardware.driveJoystick.getY();
    }*/


    static final class AbsoluteDirection {

	public static final AbsoluteDirection FORWARD = new AbsoluteDirection(0);
	public static final AbsoluteDirection BACKWARD = new AbsoluteDirection(1);
	public static final AbsoluteDirection STRAFE_LEFT = new AbsoluteDirection(2);
	public static final AbsoluteDirection STRAFE_RIGHT = new AbsoluteDirection(3);
	public static final AbsoluteDirection UNSPECIFIED = new AbsoluteDirection(5);
	public static final int DIR_FORWARD = 0;
	public static final int DIR_BACKWARD = 1;
	public static final int DIR_STRAFE_LEFT = 2;
	public static final int DIR_STRAFE_RIGHT = 3;
	public static final int ERROR_UNSPECIFIED = 5;
	private int id = 0;

	private AbsoluteDirection(int id) {
	    this.id = id;
	}
    }

    public static boolean doShoot() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.shootingIndex];
	}
	if (SmartDashboard.getBoolean("IsFPR")) {
	    return ((int) (SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
	}
	return Hardware.auxJoystick.getTrigger();
    }

    public static boolean doShooterSpin() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.spinningIndex];
	}
	if (SmartDashboard.getBoolean("IsFPR")) {
	    return ((int) (SmartDashboard.getNumber("MouseButtons")) & 2) == 2;
	}
	return Hardware.auxJoystick.getRawButton(4) || Hardware.auxJoystick.getRawButton(5);
    }

    public static double angleUp() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.aimUpIndex]?1:0;
	}
	if (SmartDashboard.getBoolean("IsFPR")) {
	    return Math.max(0, SmartDashboard.getNumber("MouseYVelocity") / 14.0);
	}
	return Hardware.auxJoystick.getRawButton(3) ? 1 : 0;
    }

    public static double angleDown() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.aimDownIndex]?1:0;
	}
	if (SmartDashboard.getBoolean("IsFPR")) {
	    return Math.min(0, SmartDashboard.getNumber("MouseYVelocity") / 14.0);
	}
	return Hardware.auxJoystick.getRawButton(2) ? 1 : 0;
    }

    public static boolean doSetForClimb() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.climbIndex];
	}
	return false;// IMPLEMENT ME!
    }

    public static boolean climbUp() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.climbIndex];
	}
	return false;// IMPLEMENT ME!
    }
    public static boolean climbDown() {
	if (DriverStation.getInstance().isAutonomous()) {
	    return Data.aiDriver.functionValues[AIDriver.climbExtendIndex];
	}
	return false;// IMPLEMENT ME!
    }
    public static String logString(boolean Autonomous) {
	String xmlString = Autonomous ? "<controlScheme mode=\"autonomous\">\n" : "<controlScheme mode=\"teleop\">\n";
	xmlString = xmlString.concat("<driveValues>\n"
		+ "<X>" + ControlScheme.strafeX() + "</X>\n"
		+ "<Y>" + ControlScheme.strafeY() + "</Y>\n"
		+ "<Rotation>" + ((ControlScheme.driveRotateCCW() ? -1 : 0) + (ControlScheme.driveRotateCW() ? 1 : 0)) + "</Rotation>\n"
		+ "</driveValues>\n");
	xmlString = xmlString.concat("<shotValues>\n"
		+ "<adjustAngle>" + ((ControlScheme.angleUp()) - (ControlScheme.angleDown())) + "</adjustAngle>\n"
		+ "<shouldSpin>" + (ControlScheme.doShooterSpin() ? "true" : "false") + "</shouldSpin>\n"
		+ "<shouldFire>" + (ControlScheme.doShoot() ? "true" : "false") + "</shouldFire>\n"
		+ "</shotValues>\n");
	xmlString = xmlString.concat("<climbValues>\n"
		+ "<shouldBegin>" + (ControlScheme.doSetForClimb() ? "true" : "false") + "</shouldBegin>\n"
		+ "<climb>" + ((ControlScheme.climbUp()?1:0)-(ControlScheme.climbDown()?1:0))+ "</climb>\n"
		+ "</climbValues>\n");
	xmlString = xmlString.concat("</controlSchme>");
	return xmlString;
    }

    public static double strafeY() {
	if (SmartDashboard.getBoolean("IsFPR")) {
	    return (((int) SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 0.5 : ((int) (SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -0.5 : 0;
	}
	return Hardware.driveJoystick.getY();
    }

    public static double strafeX() {
	if (SmartDashboard.getBoolean("IsFPR")) {
	    return (((int) SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? 0.5 : ((int) (SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? -0.5 : 0;
	}
	return Hardware.driveJoystick.getX();
    }

    public static double getZ() {
	return Hardware.driveJoystick.getZ();
    }
}
