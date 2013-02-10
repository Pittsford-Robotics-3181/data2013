/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import org.pittsfordrobotics.yr2013.ControlScheme;
import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.*;

/**
 *
 * @author liam
 */
public class Shooter extends Thread {

	SpeedController frontMotor, backMotor, angleMotor;
	Solenoid discPusher;
	double maxSpeed = 0.75;

	public Shooter(SpeedController frontMotor, SpeedController backMotor, SpeedController angleMotor, Solenoid discPusher) {
		this.frontMotor = frontMotor;
		this.backMotor = backMotor;
		this.angleMotor = angleMotor;
		this.discPusher = discPusher;
	}

	public void run() {
		while(DriverStation.getInstance().isEnabled()) {
			double speed = -frontMotor.get();

			double speed2 = -backMotor.get();
			if(ControlScheme.doShoot()) {
				discPusher.set(true);
				Timer.delay(0.01);
				discPusher.set(false);
			}
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
			Hardware.shotAngleMotor.set(Hardware.auxJoystick.getRawButton(3) ? -1 : Hardware.auxJoystick.getRawButton(2) ? 1 : 0);
			frontMotor.set(-speed);
			backMotor.set(-speed2);
			Timer.delay(0.005);
		}
	}
}
