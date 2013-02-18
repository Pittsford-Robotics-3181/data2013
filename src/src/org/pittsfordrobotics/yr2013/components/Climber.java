/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class Climber extends Thread{
	ToggleTiltSolenoid toggler;
	public void run(){
		toggler = new ToggleTiltSolenoid();
		toggler.start();
		while(DriverStation.getInstance().isEnabled()){
			Hardware.climbingMotor.set(/*Math.max(Hardware.climberLimit.get() ? 0 : -1 , */Hardware.auxJoystick.getRawButton(6) ? -1 : Hardware.auxJoystick.getRawButton(7) ? 1 : 0)/*)*/;
			
				Hardware.solenoid1.set(Hardware.auxJoystick.getRawButton(10));
				Timer.delay(0.1);
				Hardware.solenoid4.set(Hardware.auxJoystick.getRawButton(10));
			Timer.delay(0.005);
		}
	}
	private class ToggleTiltSolenoid extends Thread{
		private boolean isToggling = false;
		public void run(){
			while(DriverStation.getInstance().isEnabled()){
				if(Hardware.auxJoystick.getRawButton(11)){
					isToggling = !isToggling;
					Hardware.tiltSolenoid.set(isToggling);
					Timer.delay(0.1);
				}
				Timer.delay(0.005);
			}
		}
	}
}
