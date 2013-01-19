/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Jaguar;


/**
 *
 * @author robbiemarkwick
 */
public class Shooter {
    public Jaguar angleMotor;
    public Jaguar fireMotor;
    Shooter (Jaguar Fire,Jaguar angle){
        fireMotor=Fire;
        angleMotor=angle;
    }
    public void shoot(){
        Utils.ramp (ControlScheme.shotAngle()*.5, angleMotor, Utils.kDefaultTicksPerSecond , Utils.kDefaultRampStepSize);//Adjust Angle
        if(ControlScheme.getTrigger()) Utils.ramp(1, fireMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//Shoot if needed
    }
}
