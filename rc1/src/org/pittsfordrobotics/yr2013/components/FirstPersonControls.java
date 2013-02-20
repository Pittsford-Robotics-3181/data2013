/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class FirstPersonControls {
	public static boolean isFPR(){
		return SmartDashboard.getBoolean("IsFPR");
	}
	public static boolean getForwardKey(){
		return ((int)(SmartDashboard.getNumber("Keyboard")) & 4) == 4;
	}
	public static boolean getBackwardKey(){
		return ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8;
	}
	public static boolean getLeftKey(){
		return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1;
	}
	public static boolean getRightKey(){
		return ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2;
	}
	public static double getMouseY(){
		return SmartDashboard.getNumber("MouseYVelocity") / 7.0;
	}
	public static double getMouseX(){
		return SmartDashboard.getNumber("MouseYVelocity") / 7.0;
	}
}