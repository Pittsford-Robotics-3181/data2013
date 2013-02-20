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
	AnalogChannel x;
        double a,b,h;
        /**
         * @deprecated
         * @param xChannel
         * @param yChannel 
         */
        
	public AngleSensor(int xChannel, int yChannel){
		x = new AnalogChannel(xChannel);
		//y = new AnalogChannel(yChannel);
	}
        public AngleSensor(int voltsInChan)
        {
            x= new AnalogChannel(voltsInChan);
            a = 4347.1851889408;
            b = -0.9313153512;
            h = 72.398;
        }
        double map(double x, double in_min, double in_max, double out_min, double out_max)
{
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}

        /**
         * deprecated
         * @return 
         *
         */
        /*
	public double getAngle(){
		//return MathUtils.atan2(y.getVoltage()-2.5,x.getVoltage()-2.5)+Math.PI;
            return 0.0;
	}
        //*/
        public double getAngle(){
            
            return MathUtils.asin(MathUtils.pow(map(x.getVoltage(),0,1023,0,5)/a,1/b)/h);
        }
        
	public double getAngleInStupidDegrees(){
		return getAngle()*180.0/Math.PI;
	}
	
}
