/*s
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.components.*;

/**
 * Contains instances of hardware.
 * <p/>
 * @author Robbie Markwick
 * @author Liam Middlebrook
 */
public class Hardware {
        public static final DSOutput DSOut = new DSOutput();
	/**
	 * The Joystick for driving.
	 */
	public static Joystick driveJoystick = new Joystick(1);
	/**
	 * The Joystick for auxiliary functions.
	 */
	public static Joystick auxJoystick = new Joystick(2);
	/**
	 * Front right Jaguar.
	 */
	public static final Jaguar frontRightJaguar = new Jaguar(1, 1);
	/**
	 * Front left Jaguar.
	 */
	public static final Jaguar frontLeftJaguar = new Jaguar(1, 2);
	/**
	 * Back right Jaguar.
	 */
	public static final Jaguar backRightJaguar = new Jaguar(1, 3);
	/**
	 * Back left Jaguar.
	 */
	public static final Jaguar backLeftJaguar = new Jaguar(1, 4);
	/**
	 * Shooting motor 1 (the larger one).
	 */
	public static final Victor shootingMotor = new Victor(1, 5);
	/**
	 * Shooting motor 2 (the smaller one).
	 */
	public static final Victor shootingMotor2 = new Victor(1, 7);
	/**
	 * Motor to adjust the shooter angle.
	 */
	public static final Jaguar shotAngleMotor = new Jaguar(1, 6);
	/**
	 * Arnold. Need I say more?
	 */
	//public static final Relay arnold = new Relay(1,7,Relay.Direction.kForward);
	public static final Compressor arnold = new Compressor(1,1,1,7);
	/**
	 * Motor for climbing.
	 */
	public static final Victor climbingMotor = new Victor(1, 8);
	/**
	 * The limit switch for the climber.
	 */
	//public static final DigitalInput climberLimit = new DigitalInput(1, 1);
	/**
	 * The angle sensor on the shooter.
	 */
	public static final AngleSensor angleSensor = new AngleSensor(3);
	/**
	 * The main and first hook solenoid.
	 */
	public static final Solenoid hookSolenoid1 = new Solenoid(1, 1);
	/**
	 * The solenoid that tilts the robot.
	 */
	public static final Solenoid tiltSolenoid = new Solenoid(1, 3);
	/**
	 * The solenoid to launch a frisbee.
	 */
	public static final Solenoid shootLaunch = new Solenoid(1, 2);
	/**
	 * The second hook solenoid.
	 */
	public static final Solenoid hookSolenoid2 = new Solenoid(1, 4);
}