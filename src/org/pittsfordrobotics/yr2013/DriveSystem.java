/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;


/**
 *
 * @author robbiemarkwick
 */
public class DriveSystem {
    private RobotDrive drive;
    DriveSystem(SpeedController frontRight,SpeedController frontLeft, SpeedController backLeft, SpeedController backRight){
        drive=new RobotDrive(frontLeft,backLeft,frontRight,backRight);
    }
    public void drive(){
        drive.mecanumDrive_Polar(ControlScheme.driveMagnitude(), ControlScheme.driveDirection(), ControlScheme.driveRotation());
        
    }
}
 