/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class AngleSensor {
	AnalogChannel gyro;
	public AngleSensor(int channel){
		gyro = new AnalogChannel(channel);
	}
	public AngleSensor(int module, int channel){
		gyro = new AnalogChannel(module,channel);
	}
	public double getAngle(){
		return MathUtils.asin((gyro.getVoltage()-502.0/1023.0)/(367.0/1023.0-502.0/1023.0));
	}
	public double getAngleInStupidDegrees(){
		return getAngle()*180.0/Math.PI;
	}
}
