/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Relay.Direction;
import org.pittsfordrobotics.yr2013.components.*;
import org.pittsfordrobotics.yr2013.components.ai.*;

/**
 * Main class for the robot that handles all of the threads.
 * <p/>
 * @author LiamMiddlebrook
 */
public class Data extends IterativeRobot {

	DriveSystem robotDrive = new DriveSystem(Hardware.frontRightJaguar, Hardware.frontLeftJaguar, Hardware.backRightJaguar, Hardware.backLeftJaguar);
	Shooter shooter = new Shooter(Hardware.shootingMotor, Hardware.shootingMotor2, Hardware.shotAngleMotor, Hardware.shootLaunch);
	Climber climber = new Climber();
	SmartDashboardCommunications dsComm = new SmartDashboardCommunications();
	AIDirector ai = new AIDirector();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		dsComm.start();
	}

	/**
	 * Stuff that needs to be reinitialized whenever the robot is disabled.
	 */
	public void disabledInit() {
		climber = new Climber();
		robotDrive = new DriveSystem(Hardware.frontRightJaguar, Hardware.frontLeftJaguar, Hardware.backRightJaguar, Hardware.backLeftJaguar);
		shooter = new Shooter(Hardware.shootingMotor, Hardware.shootingMotor2, Hardware.shotAngleMotor, Hardware.shootLaunch);
	}
	public void autonomousInit(){
		ai.queueAction(CommonActions.spinUp);
		ai.queueAction(CommonActions.shoot);
		ai.queueAction(CommonActions.shoot);
		ai.queueAction(CommonActions.shoot);
		ai.queueAction(CommonActions.spinDown);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		
	}

	/**
	 * Starts all the threads and turns Arnold on.
	 */
	public void teleopInit() {
		ai.stop();//we don't need to do this, it's just so we don't take up so much cpu time.
		robotDrive.start();
		shooter.start();
		climber.start();
		Hardware.arnold.setDirection(Relay.Direction.kForward);
		Hardware.arnold.set(Relay.Value.kOn);
	}
}
