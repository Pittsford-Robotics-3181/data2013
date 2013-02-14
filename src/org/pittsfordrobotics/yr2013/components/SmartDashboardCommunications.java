/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class SmartDashboardCommunications extends Thread{
	public void run(){
		while(true){
			SmartDashboard.putNumber("Battery", DriverStation.getInstance().getBatteryVoltage());
			SmartDashboard.putNumber("Timer",(DriverStation.getInstance().getAlliance() == Alliance.kRed ? 32768 : 0) | (120 - (int)DriverStation.getInstance().getMatchTime()));
			Timer.delay(0.005);
		}
	}
}
