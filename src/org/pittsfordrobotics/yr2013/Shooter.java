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
    private double shooterSpeed=0;
    private static double kAdjust=.05;
    Shooter (Jaguar Fire,Jaguar angle){
        fireMotor=Fire;
        angleMotor=angle;
    }
    Shooter (Jaguar Fire){
        fireMotor=Fire;
    }
    public void shoot(){
        if(angleMotor!=null){
          Utils.ramp (ControlScheme.shotAngle()*.5, angleMotor, Utils.kDefaultTicksPerSecond , Utils.kDefaultRampStepSize);//Adjust Angle
          shooterSpeed=1;
        }
        else{
            shooterSpeed+=(ControlScheme.shotAngle()==0)?0:((ControlScheme.shotAngle()>0)?kAdjust:-kAdjust);
        }
        if(ControlScheme.getTrigger()) Utils.ramp(shooterSpeed, fireMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//Shoot if needed
        else Utils.ramp(0, fireMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//Stop Shooting if needed

    }
}
