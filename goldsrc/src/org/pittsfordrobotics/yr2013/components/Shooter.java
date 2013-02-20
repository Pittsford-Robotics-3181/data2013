/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import org.pittsfordrobotics.yr2013.ControlScheme;
import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.*;

/**
 * Handles shooter functions in a thread.
 * <p/>
 * @author liam
 */
public class Shooter extends Thread {

	/**
	 * The front, back, and angle motors.
	 */
	SpeedController frontMotor, backMotor, angleMotor;
	double maxSpeed = 0.75;
	ShooterRapidFire rapidFire;
	double targetAngle = 0;

	/**
	 * Constructor for the Shooter.
	 * <p/>
	 * @param frontMotor the front shooter motor.
	 * @param backMotor the back shooter motor.
	 * @param angleMotor the motor for adjusting the angle.
	 * @param discPusher the solenoid to push the disc.
	 */
	public Shooter(SpeedController frontMotor, SpeedController backMotor, SpeedController angleMotor, Solenoid discPusher) {
		this.frontMotor = frontMotor;
		this.backMotor = backMotor;
		this.angleMotor = angleMotor;
		this.rapidFire = new ShooterRapidFire(discPusher);
	}

	/**
	 * Tells the shooter thread that if the angle is absolute, set it to this.
	 * <p/>
	 * @param radians where to go in radians.
	 */
	public void setTargetAngle(double radians) {
		targetAngle = radians;
	}

	/**
	 * The main loop for controlling the shooter angle. Starts the rapid fire
	 * thread.
	 */
	public void run() {
		this.rapidFire.start();
		while(DriverStation.getInstance().isEnabled()) {
			if(ControlScheme.getIsAbsolutAngle()) {
				if(Hardware.angleSensor.getAngle() < targetAngle) {
					Hardware.shotAngleMotor.set(-1);
				}
				else if(Hardware.angleSensor.getAngle() > targetAngle) {
					Hardware.shotAngleMotor.set(1);
				}
				else {
					Hardware.shotAngleMotor.set(0);
				}
			}
			double speed = -frontMotor.get();
			double speed2 = -backMotor.get();
			if(ControlScheme.doShooterSpin()) {
				if(speed < maxSpeed) {
					speed += 0.01;
				}
				if(speed2 < maxSpeed) {
					speed2 += 0.01;
				}
			}
			else {

				if(speed > 0.0) {
					speed -= 0.01;
				}
				if(speed2 > 0.0) {
					speed2 -= 0.01;
				}
			}
			Hardware.shotAngleMotor.set((ControlScheme.angleDown() - ControlScheme.angleUp())*(1-Hardware.auxJoystick.getZ())/2);
			frontMotor.set(-speed);
			backMotor.set(-speed2);
			Timer.delay(0.005);
		}
	}
}

/**
 * Thread to handle rapid firing.
 * <p/>
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
class ShooterRapidFire extends Thread {

	Solenoid discPusher;

	public ShooterRapidFire(Solenoid discPusher) {
		this.discPusher = discPusher;
	}

	/**
	 * The main loop for the rapid firing.
	 */
	public void run() {
		while(DriverStation.getInstance().isEnabled()) {
			if(ControlScheme.doShoot()) {
				discPusher.set(true);
				Timer.delay(0.1);
				discPusher.set(false);
				Timer.delay(0.4);
			}
			Timer.delay(0.005);
		}
	}
}