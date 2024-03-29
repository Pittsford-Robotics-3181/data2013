/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * Handles Mecanum driving as a thread.
 * <p/>
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class DriveSystem extends Thread {

	private Jaguar frontRight, frontLeft, backRight, backLeft;

	/**
	 * Initializes the drive system.
	 * <p/>
	 * @param frontRight kinda obvious.
	 * @param frontLeft also obvious.
	 * @param backRight I think you know by now.
	 * @param backLeft yep.
	 */
	public DriveSystem(Jaguar frontRight, Jaguar frontLeft, Jaguar backRight, Jaguar backLeft) {

		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;
	}

	/**
	 * Main thread loop that sets the motors.
	 */
	public void run() {
		while(DriverStation.getInstance().isEnabled() && !DriverStation.getInstance().isAutonomous()) {
			frontLeft.set((-ControlScheme.strafeX() * Math.abs(ControlScheme.strafeX()) * ControlScheme.getZ() + ControlScheme.strafeY() * Math.abs(ControlScheme.strafeY()) * ControlScheme.getZ() + rotation()));
			frontRight.set((-ControlScheme.strafeX() * Math.abs(ControlScheme.strafeX()) * ControlScheme.getZ() - ControlScheme.strafeY() * Math.abs(ControlScheme.strafeY()) * ControlScheme.getZ() + rotation()));
			backLeft.set((ControlScheme.strafeX() * Math.abs(ControlScheme.strafeX()) * ControlScheme.getZ() + ControlScheme.strafeY() * Math.abs(ControlScheme.strafeY()) * ControlScheme.getZ() + rotation()));
			backRight.set((ControlScheme.strafeX() * Math.abs(ControlScheme.strafeX()) * ControlScheme.getZ() - ControlScheme.strafeY() * Math.abs(ControlScheme.strafeY()) * ControlScheme.getZ() + rotation()));
			Timer.delay(0.005);
		}
	}

	/**
	 * gets the rotation for the robot.
	 * <p/>
	 * @TODO put into ControlScheme.
	 * @return rotation
	 */
	public double rotation() {
		if(SmartDashboard.getBoolean("IsFPR")) {
			return SmartDashboard.getNumber("MouseXVelocity") / 14.0;
		}
		return (ControlScheme.driveRotateCW() ? 1 : ControlScheme.driveRotateCCW() ? -1 : 0) * ControlScheme.getZ() * Math.abs(ControlScheme.getZ());
	}
}
