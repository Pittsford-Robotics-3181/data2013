/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import org.pittsfordrobotics.yr2013.ControlScheme;
import org.pittsfordrobotics.yr2013.Utils;
import org.pittsfordrobotics.yr2013.Hardware;


/**
 *
 * @author robbiemarkwick
 */
public class Shooter {
    public static void shoot(){
        Utils.ramp (ControlScheme.shotAngle()*.5, Hardware.ShotAngleMotor, Utils.kDefaultTicksPerSecond , Utils.kDefaultRampStepSize);//Adjust Angle
        if(ControlScheme.getTrigger()) Utils.ramp(1, Hardware.ShootingMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//Shoot if needed
    }
}
