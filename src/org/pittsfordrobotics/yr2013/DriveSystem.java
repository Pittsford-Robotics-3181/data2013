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
public class DriveSystem extends Thread{
	private Joystick driveJoystick, auxJoystick;
	private Jaguar frontRight, frontLeft, backRight, backLeft;
	public DriveSystem(Joystick driveJoystick, Joystick auxJoystick, Jaguar frontRight, Jaguar frontLeft, Jaguar backRight, Jaguar backLeft){
		this.driveJoystick = driveJoystick;
		this.auxJoystick = auxJoystick;
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;
	}
	public void run(){
		frontLeft.set(driveJoystick.getX()*Math.abs(driveJoystick.getX()) + driveJoystick.getY()*Math.abs(driveJoystick.getY()) + 0);
        frontRight.set(-driveJoystick.getX()*Math.abs(driveJoystick.getX()) + driveJoystick.getY()*Math.abs(driveJoystick.getY()) - 0);
        backLeft.set(-driveJoystick.getX()*Math.abs(driveJoystick.getX()) + driveJoystick.getY()*Math.abs(driveJoystick.getY()) + 0);
        backRight.set(driveJoystick.getX()*Math.abs(driveJoystick.getX()) + driveJoystick.getY()*Math.abs(driveJoystick.getY()) - 0);
	}
}
