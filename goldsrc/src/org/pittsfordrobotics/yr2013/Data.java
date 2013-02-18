/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.pittsfordrobotics.yr2013;


import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.components.*;

/**
 *  @author LiamMiddlebrook
 */
public class Data extends IterativeRobot {
	DriveSystem robotDrive = new DriveSystem(Hardware.frontRightJaguar,Hardware.frontLeftJaguar,Hardware.backRightJaguar,Hardware.backLeftJaguar);
    Shooter shooter = new Shooter(Hardware.shootingMotor,Hardware.shootingMotor2,Hardware.shotAngleMotor,Hardware.shootLaunch);
	Climber climber = new Climber();
	SmartDashboardCommunications dsComm = new SmartDashboardCommunications();
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		dsComm.start();
    }
	
	public void disabledInit(){
		climber = new Climber();
		robotDrive = new DriveSystem(Hardware.frontRightJaguar,Hardware.frontLeftJaguar,Hardware.backRightJaguar,Hardware.backLeftJaguar);
		shooter = new Shooter(Hardware.shootingMotor,Hardware.shootingMotor2,Hardware.shotAngleMotor,Hardware.shootLaunch);
	}

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
		System.out.println("teleop");
        robotDrive.start();
		shooter.start();
		climber.start();
		Hardware.arnold.set(1);    }
	public void teleopPeriodic(){
		//Hardware.solenoid1.set(Hardware.auxJoystick.getRawButton(6));
		//Hardware.solenoid3.set(Hardware.auxJoystick.getRawButton(11));
		//Hardware.solenoid4.set(Hardware.auxJoystick.getRawButton(10));
		//Hardware.shootLaunch.set(Hardware.auxJoystick.getRawButton(7));
	
}
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
