/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class DriveSystem extends Thread implements Loggable {

	private Jaguar frontRight, frontLeft, backRight, backLeft;

	public DriveSystem(Jaguar frontRight, Jaguar frontLeft, Jaguar backRight, Jaguar backLeft) {

		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;
	}

	public void run() {
		RobotDrive r;
		while(DriverStation.getInstance().isEnabled()) {
			frontLeft.set(-(ControlScheme.driveX() + ControlScheme.driveY() + ControlScheme.driveRotation()));
			 frontRight.set(-ControlScheme.driveX() + ControlScheme.driveY() - ControlScheme.driveRotation());
			backLeft.set(-(-ControlScheme.driveX() + ControlScheme.driveY() + ControlScheme.driveRotation()));
			backRight.set(ControlScheme.driveX() + ControlScheme.driveY() - ControlScheme.driveRotation());
		/*	frontLeft.set(-(-ControlScheme.driveX() - ControlScheme.driveY() - ControlScheme.driveRotation()));
			frontRight.set(-(-ControlScheme.driveX() + ControlScheme.driveY() - ControlScheme.driveRotation()));
			backLeft.set(-(ControlScheme.driveX() - ControlScheme.driveY() - ControlScheme.driveRotation()));
			backRight.set(-(ControlScheme.driveX() + ControlScheme.driveY() - ControlScheme.driveRotation()));
			*///drive.mecanumDrive_Polar(ControlScheme.driveMagnitude(), ControlScheme.driveDirection(), ControlScheme.driveRotation());
			Timer.delay(0.005);
		}
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);
	}

	public String logString() {
		String xmlString = "<driveSystem>\n";
		xmlString = xmlString.concat("<frontRight>" + frontRight.get() + "</frontRight>\n");
		xmlString = xmlString.concat("<frontLeft>" + frontLeft.get() + "</frontLeft>\n");
		xmlString = xmlString.concat("<backLeft>" + backRight.get() + "</backLeft>\n");
		xmlString = xmlString.concat("<backRight>" + backLeft.get() + "</backRight>\n");
		xmlString = xmlString.concat("</driveSystem>");
		return xmlString;
	}
}
