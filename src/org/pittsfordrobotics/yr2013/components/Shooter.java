/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import org.pittsfordrobotics.yr2013.ControlScheme;
import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.*;
import org.pittsfordrobotics.yr2013.robbieComponents.*;

/**
 *
 * @author liam
 */
public class Shooter extends Thread implements Loggable{

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
			Hardware.shotAngleMotor.set(ControlScheme.angleDown() - ControlScheme.angleUp());
			frontMotor.set(-speed);
			backMotor.set(-speed2);
			Timer.delay(0.005);
		}
	}
    public String logString() {
	String xmlString = "<shooter>\n";
	xmlString = xmlString.concat("<angleMotor>" + angleMotor.get() + "</angleMotor>\n");
	xmlString = xmlString.concat("<fireMotor1>\n"
		+ "<target>" + (ControlScheme.doShooterSpin()?-1:0) + "</target>\n"
		+ "<current>" + -frontMotor.get() + "</current>\n"
		+ "</fireMotor1>\n");
	xmlString = xmlString.concat("<fireMotor2>\n"
		+ "<target>" + (ControlScheme.doShooterSpin()?-1:0) + "</target>\n"
		+ "<current>" + -backMotor.get() + "</current>\n"
		+ "</fireMotor1>\n");
	xmlString = xmlString.concat("<launcher>" + discPusher.get() + "</launcher>\n");
	xmlString = xmlString.concat("</shooter>");
	return xmlString;
    }
}
