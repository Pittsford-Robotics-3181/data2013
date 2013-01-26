/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
// @author Liam Middlebrook
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Data extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit() {
        Hardware.dsOutput.say(1, "Initializing...");
		
		Hardware.dsOutput.say(1, "Done.");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(NetworkTable.getTable("Controls").getBoolean("NetworkIdiot"))NetworkTable.getTable("Controls").putBoolean("NetworkIdiot",false);//make sure the network is not being an idiot
		// ^Dafuq?
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Hardware.driving.drive();
        Hardware.shooter.shoot();
        Hardware.climber.climb();
	if(NetworkTable.getTable("Controls").getBoolean("NetworkIdiot"))NetworkTable.getTable("Controls").putBoolean("NetworkIdiot",false);//make sure the network is not being an idiot
    // ^ again... DAFUQ IS THIS?
	}
    public void autonomousInit(){
    }
    public void teleopInit(){
        
    }
    public void disabledInit(){
         
    }
    public void disabledPeriodic(){
	/*check and see if controls need remaping*/
	if (NetworkTable.getTable("Controls").getBoolean("NeedsToRemap")) {
	    /*Remap the controls*/
		//OK Robbie we need to go over networking... You're doing it wrong!
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("ShootingStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("ShootingButt"), ControlScheme.shootingIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("SpinningStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("SpinningButt"), ControlScheme.spinningIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("DriveLeftStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("DriveLeftButt"), ControlScheme.driveLeftSideIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("DriveRightStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("DriveRightButt"), ControlScheme.driveRightSideIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("AimUpStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("AimUpButt"), ControlScheme.aimUpIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("AimDownStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("AimDownButt"), ControlScheme.aimDownIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("BeginClimbStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("BeginClimbButt"), ControlScheme.beginClimbIndex);
	    ControlScheme.setJoystickAndButtonForFunction((int) NetworkTable.getTable("Controls").getNumber("ClimbStick"),
		    (int) NetworkTable.getTable("Controls").getNumber("ClimbButt"), ControlScheme.climbIndex);
	    NetworkTable.getTable("Controls").putBoolean("NeedsToRemap", false);//don't remap stuff until necessary
	}
    	if(NetworkTable.getTable("Controls").getBoolean("NetworkIdiot"))NetworkTable.getTable("Controls").putBoolean("NetworkIdiot",false);//make sure the network is not being an idiot
    }
}
