/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * Provides a unified interface to any controls from the driver(s).
 * <p/>
 * @author liammiddlebrook
 */
public class ControlScheme {

	/**
	 *
	 * @return If the robot is rotating CCW.
	 */
	public static boolean driveRotateCCW() {
		return Hardware.driveJoystick.getRawButton(8);
	}

	/**
	 *
	 * @return If the robot is rotating CW.
	 */
	public static boolean driveRotateCW() {
		return Hardware.driveJoystick.getRawButton(9);
	}

	/**
	 *
	 * @return The robot should shoot.
	 */
	public static boolean doShoot() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return ((int)(SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
		}
		return Hardware.auxJoystick.getTrigger();
	}

	/**
	 *
	 * @return The robot should be spinning up the shooter.
	 */
	public static boolean doShooterSpin() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return ((int)(SmartDashboard.getNumber("MouseButtons")) & 2) == 2;
		}
		return Hardware.auxJoystick.getRawButton(4) || Hardware.auxJoystick.getRawButton(5);
	}

	/**
	 *
	 * @return The velocity that the shooter should be angling up.
	 */
	public static double angleUp() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return Math.max(0, SmartDashboard.getNumber("MouseYVelocity") / 7.0);
		}
		return Hardware.auxJoystick.getRawButton(3) ? 1 : 0;
	}

	/**
	 *
	 * @return The velocity that the shooter should be angling down.
	 */
	public static double angleDown() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return Math.min(0, SmartDashboard.getNumber("MouseYVelocity") / 7.0);
		}
		return Hardware.auxJoystick.getRawButton(2) ? 1 : 0;
	}

	/**
	 *
	 * @return The velocity that the robot should strafe forward and backward.
	 */
	public static double strafeY() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 1 : ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -1 : 0;
		}
		return -(Hardware.driveJoystick.getY() + (Hardware.driveJoystick.getRawButton(3) ? 1 : 0) + (Hardware.driveJoystick.getRawButton(2) ? -1 : 0));
	}

	/**
	 *
	 * @return The velocity that the robot should strafe left and right.
	 */
	public static double strafeX() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return (((int)SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? 1 : ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? -1 : 0;
		}
		return -(Hardware.driveJoystick.getX() + (Hardware.driveJoystick.getRawButton(4) ? -1 : 0) + (Hardware.driveJoystick.getRawButton(5) ? 1 : 0));
	}

	/**
	 *
	 * @return The Z axis, normalized to [0,1]
	 */
	public static double getZ() {
		return (1 - Hardware.driveJoystick.getZ()) / 2.0;
	}

	/**
	 *
	 * @return If the shooter should be moving to an absolute angle.
	 */
	public static boolean getIsAbsolutAngle() {
		return false; //@TODO implement actually
	}
}
