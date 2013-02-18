/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.communication.Semaphore;

/**
 * @author LiamMiddlebrook
 */
public class Data extends IterativeRobot {
	
	AnalogChannel gyro = new AnalogChannel(1, 1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		Hardware.dsComm.start();
		Hardware.arnold.set(1);
	}

	public void disabledInit() {
		Hardware.driveSystemInit();
		Logging.export();
	}

	public void autonomousInit() {
		ControlScheme.isAutonomous = true;
		Logging.init();
		Hardware.driveSystemStart();
		Hardware.shooterStart();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Loggable items[] = {Hardware.robotDrive, Hardware.shooter};
		Logging.logItems(items, true, true);
	}

	public void teleopInit() {
		ControlScheme.isAutonomous = false;
		Logging.init();//begins logging. If this is commented, Autonomous will also be logged
		if(!Hardware.robotDrive.isAlive()) Hardware.driveSystemStart();
		if(!Hardware.shooter.isAlive()) Hardware.shooterStart();
		Hardware.climberStart();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Hardware.arnold.set(1);
		/*
		Hardware.shooter.shoot();
		Hardware.solenoid1.set(Hardware.auxJoystick.getRawButton(6));
		Hardware.solenoid3.set(Hardware.auxJoystick.getRawButton(11));
		Hardware.solenoid4.set(Hardware.auxJoystick.getRawButton(10));
		 */
		//Hardware.shootLaunch.set(Hardware.auxJoystick.getRawButton(7)); Shooter takes care of this
		Hardware.dsOut.say(0, "" + gyro.getVoltage());
		//Logging
		Loggable items[] = {Hardware.robotDrive, Hardware.shooter,Hardware.climber};
		Logging.logItems(items, true, false);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	}
}