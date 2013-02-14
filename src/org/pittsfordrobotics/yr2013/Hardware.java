/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import org.pittsfordrobotics.yr2013.components.Shooter;
import org.pittsfordrobotics.yr2013.components.SmartDashboardCommunications;

/**
 * @author Robbie Markwick
 * @author Liam Middlebrook
 */
public class Hardware {

    public static Joystick driveJoystick = new Joystick(1);
    public static Joystick auxJoystick = new Joystick(2);
    //public static DSOutput dsOutput = new DSOutput();
    public static final Jaguar frontRightJaguar = new Jaguar(2,1);
    public static final Jaguar frontLeftJaguar = new Jaguar(2,2);
    public static final Jaguar backRightJaguar = new Jaguar(2,3);
    public static final Jaguar backLeftJaguar = new Jaguar(2,4);
//    public static DriveSystem driveSystem=new DriveSystem(frontRightJaguar,frontLeftJaguar,backLeftJaguar,backRightJaguar);
    //public  static DriveSystem driveSystem=new DriveSystem(driveMotor2,DriveMotor1); //uncomment this for tank drive
   
    public static final Victor shootingMotor=new Victor(2, 5);
    public static final Victor shootingMotor2=new Victor(2, 7);

    public static final Jaguar shotAngleMotor=new Jaguar(2, 6);
    //climbing pneumatics
   //public static final Compressor shootCompressor=new Compressor(8,17);
	public static final Solenoid solenoid1 = new Solenoid(1,1);
	public static final Solenoid solenoid3 = new Solenoid(1,3);
    public static final Solenoid shootLaunch = new Solenoid(1,2);
	public static final Solenoid solenoid4 = new Solenoid(1,4);
//    public static final Shooter shooter=new Shooter(shootingMotor,shootingMotor2,shotAngleMotor,shootLaunch);
   
    //climbing motors
    //public static final Victor ClimbMotor1=new Victor(9);
    //public static final Victor ClimbMotor2=new Victor(10);
    //climbing pneumatics
    //public static final Compressor climbCompressor=new Compressor(11,12);
    //public static final Solenoid pistonUp = new Solenoid(1, 3);
    //climbing switches
    //public static final DigitalInput upSwitch= new DigitalInput(5);
    //public static final DigitalInput downSwitch= new DigitalInput(6);
    //climber object
//    public static Climber climber=new Climber(ClimbMotor1,ClimbMotor2,pistonUp,upSwitch,downSwitch);
   
//    public static AIDriver aiDriver=new AIDriver();
        
        /// COMPONENTS
        public static DriveSystem robotDrive = new DriveSystem(Hardware.frontRightJaguar,Hardware.frontLeftJaguar,Hardware.backRightJaguar,Hardware.backLeftJaguar);
	public static Shooter shooter = new Shooter(Hardware.shootingMotor,Hardware.shootingMotor2,Hardware.shotAngleMotor,Hardware.shootLaunch);
	public static SmartDashboardCommunications dsComm = new SmartDashboardCommunications();
	public static DSOutput dsOut = new DSOutput();
	
}