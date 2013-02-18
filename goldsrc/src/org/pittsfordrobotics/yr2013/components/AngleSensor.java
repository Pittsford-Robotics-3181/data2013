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
	AnalogChannel x,y;
	public AngleSensor(int xChannel, int yChannel){
		x = new AnalogChannel(xChannel);
		y = new AnalogChannel(yChannel);
	}
	public double getAngle(){
		return MathUtils.atan2(y.getVoltage()-2.5,x.getVoltage()-2.5)+Math.PI;
	}
	public double getAngleInStupidDegrees(){
		return getAngle()*180.0/Math.PI;
	}
	
}
