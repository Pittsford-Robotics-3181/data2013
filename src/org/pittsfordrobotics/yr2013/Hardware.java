/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Jaguar;

/**
 * @author Robbie Markwick
 * @author Liam Middlebrook
 */
public class Hardware {
    public static DSOutput dsOutput = new DSOutput();
    public static Jaguar driveMotor1=new Jaguar(1);//Forward Right
    public static Jaguar driveMotor2=new Jaguar(2);//Forward Left
    public static Jaguar driveMotor3=new Jaguar(3);//Backward Left
    public static Jaguar driveMotor4=new Jaguar(4);//Backward Right
    public static DriveSystem driving=new DriveSystem(driveMotor1,driveMotor2,driveMotor3,driveMotor4);
    
    public static Jaguar ShootingMotor=new Jaguar(5);
   // public static Jaguar ShotAngleMotor=new Jaguar(6);
    public static Shooter shooter=new Shooter(ShootingMotor);

    
}
