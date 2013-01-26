/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Preferences;
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
    public void autonomousInit(){
	ControlScheme.isAutonomous=true;
	Logging.init();
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
	Hardware.aiDriver.drive();
	Loggable items[]={Hardware.driving,Hardware.shooter};
	Logging.logItems(items, true, true);
    }
    public void teleopInit(){
        ControlScheme.isAutonomous=false;
	Logging.init();//comment entire if you want to log autonomus
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Hardware.driving.drive();
        Hardware.shooter.shoot();
        Hardware.climber.climb();
	Loggable items[]={Hardware.driving,Hardware.shooter,Hardware.climber};
	Logging.logItems(items, true, false);
    // ^ again... DAFUQ IS THIS?
	}
    
    
    public void disabledInit(){
         Logging.export();
    }
    public void disabledPeriodic(){
	/*check and see if controls need remaping*/
	if (NetworkTable.getTable("Controls").getBoolean("NeedsToRemap")) {
	    /*Remap the controls*/
	    Preferences pref=Preferences.getInstance();
	    pref.putBoolean("SAVE",true);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("ShootingStick",1),
		    pref.getInt("ShootingButt",0), ControlScheme.shootingIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("SpinningStick",1),
		    pref.getInt("SpinningButt",7), ControlScheme.spinningIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("DriveLeftStick",0),
		    pref.getInt("DriveLeftButt",6), ControlScheme.driveLeftSideIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("DriveRightStick",0),
		    pref.getInt("DriveRightButt",7), ControlScheme.driveRightSideIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("AimUpStick",1),
		    pref.getInt("AimUpButt",4), ControlScheme.aimUpIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("AimDownStick",1),
		    pref.getInt("AimDownButt",5), ControlScheme.aimDownIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("BeginClimbStick",1),
		    pref.getInt("BeginClimbButt",1), ControlScheme.beginClimbIndex);
	    ControlScheme.setJoystickAndButtonForFunction(pref.getInt("ClimbStick",1),
		    pref.getInt("ClimbButt",2), ControlScheme.climbIndex);

	}
    }
}
