/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;

/**
 * @author Robbie Markwick
 * @author Liam Middlebrook
 */
public class Hardware {
    
    public static DSOutput dsOutput = new DSOutput();
    public static final Jaguar frontRightJaguar = new Jaguar(1);
    public static final Jaguar frontLeftJaguar = new Jaguar(2);
    public static final Jaguar backRightJaguar = new Jaguar(3);
    public static final Jaguar backLeftJaguar = new Jaguar(4);
    public static DriveSystem driving=new DriveSystem(frontRightJaguar,frontLeftJaguar,backLeftJaguar,backRightJaguar);
    //public  static DriveSystem driving=new DriveSystem(driveMotor2,DriveMotor1); //uncomment this for tank drive
    public static final Jaguar ShootingMotor=new Jaguar(5);
    public static final Jaguar ShootingMotor2=new Jaguar(6);

   // public static Jaguar ShotAngleMotor=new Jaguar(7);
    public static Servo ShootLaunch=new Servo(8);
    public static Shooter shooter=new Shooter(ShootingMotor,ShootingMotor2,ShootLaunch);
    
    public static final Jaguar ClimbMotor1=new Jaguar(9);//Climbing
    public static final Jaguar ClimbMotor2=new Jaguar(10);//Setup for climbing 
    public static Climber climber=new Climber(ClimbMotor1,ClimbMotor2);
}
