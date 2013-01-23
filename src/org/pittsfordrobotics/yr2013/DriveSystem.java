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
public class DriveSystem implements Loggable{
    private RobotDrive drive;
    DriveSystem(SpeedController frontRight,SpeedController frontLeft, SpeedController backLeft, SpeedController backRight){
        drive=new RobotDrive(frontLeft,backLeft,frontRight,backRight);
    }
    DriveSystem(SpeedController left,SpeedController right){
        drive=new RobotDrive(left,right);
    }
    public void drive(){
        drive.mecanumDrive_Polar(ControlScheme.driveMagnitude(), ControlScheme.driveDirection(), ControlScheme.driveRotation());
    }
    public void tankDrive(){
        drive.tankDrive(ControlScheme.tankLeft(), ControlScheme.tankRight());
    }
    public String logString() {
        return"";
    }
}
 