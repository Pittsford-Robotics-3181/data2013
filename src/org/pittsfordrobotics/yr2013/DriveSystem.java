/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;


/**
 *
 * @author robbiemarkwick
 */
public class DriveSystem implements Loggable{
    private RobotDrive drive;
    private static  Jaguar frontRightJaguar;
    private static  Jaguar frontLeftJaguar;
    private static  Jaguar backRightJaguar;
    private static  Jaguar backLeftJaguar;
    DriveSystem(SpeedController frontRight,SpeedController frontLeft, SpeedController backLeft, SpeedController backRight){
        frontRightJaguar=(Jaguar) frontRight;
	frontLeftJaguar=(Jaguar) frontLeft;
	backLeftJaguar=(Jaguar) backLeft;
	backRightJaguar=(Jaguar) backRight;
    }
    DriveSystem(SpeedController left,SpeedController right){
        drive=new RobotDrive(left,right);
    }
    public void drive(){
	frontLeftJaguar.set(ControlScheme.driveX() + ControlScheme.driveY() + ControlScheme.driveRotation());
        frontRightJaguar.set(-ControlScheme.driveX() + ControlScheme.driveY() - ControlScheme.driveRotation());
        backLeftJaguar.set(-ControlScheme.driveX() + ControlScheme.driveY() + ControlScheme.driveRotation());
        backRightJaguar.set(ControlScheme.driveX() + ControlScheme.driveY() - ControlScheme.driveRotation());
       // drive.mecanumDrive_Polar(ControlScheme.driveMagnitude(), ControlScheme.driveDirection(), ControlScheme.driveRotation());
    }
    public void tankDrive(){
        drive.tankDrive(ControlScheme.tankLeft(), ControlScheme.tankRight());
    }
    public String logString() {
	String xmlString="<driveSystem>\n";
        xmlString=xmlString.concat("<frontRight>"+Hardware.frontRightJaguar.get()+"</frontRight>\n");
        xmlString=xmlString.concat("<frontLeft>"+Hardware.frontLeftJaguar.get()+"</frontLeft>\n");
        xmlString=xmlString.concat("<backLeft>"+Hardware.backRightJaguar.get()+"</backLeft>\n");
        xmlString=xmlString.concat("<backRight>"+Hardware.backLeftJaguar.get()+"</backRight>\n");
        xmlString=xmlString.concat("</driveSystem>");
        return xmlString;
    }
}
 