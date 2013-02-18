/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.*;
import org.pittsfordrobotics.yr2013.Hardware;

/**
 * Handles some communication with the smartdashboard. Also makes sure the robot
 * doesn't implode when there is no FPD attached.
 * <p/>
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class SmartDashboardCommunications extends Thread {

	/**
	 * Main loop for the threading.
	 */
	public void run() {
		try {
			SmartDashboard.getBoolean("IsFPR");
		}
		catch(Exception e) {
			SmartDashboard.putBoolean("IsFPR", false);
		}
		while(true) {
			SmartDashboard.putNumber("Battery", DriverStation.getInstance().getBatteryVoltage());
			SmartDashboard.putNumber("Timer", (DriverStation.getInstance().getAlliance() == Alliance.kRed ? 32768 : 0) | (int)DriverStation.getInstance().getMatchTime());
			SmartDashboard.putNumber("Angle", Hardware.angleSensor.getAngleInStupidDegrees());
			Timer.delay(0.005);
		}
	}
}
