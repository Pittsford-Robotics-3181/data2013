/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;

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
    
    public static final Victor ShootingMotor=new Victor(5);
    public static final Victor ShootingMotor2=new Victor(6);

    public static final Victor ShotAngleMotor=new Victor(7);
    public static final Servo ShootLaunch=new Servo(8);
    public static Shooter shooter=new Shooter(ShootingMotor,ShootingMotor2,ShotAngleMotor,ShootLaunch);
    
    public static final Victor ClimbMotor1=new Victor(9);//Climbing
    public static final Victor ClimbMotor2=new Victor(10);//Setup for climbing 
    public static Climber climber=new Climber(ClimbMotor1,ClimbMotor2);
    
    public static AIDriver aiDriver=new AIDriver();
}
