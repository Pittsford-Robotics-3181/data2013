/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;


/**
 *
 * @author robbiemarkwick
 */
public class Shooter {
    public Jaguar angleMotor;
    public Jaguar fireMotor;
    public Jaguar fireMotor2;
    public Servo bump;
    private boolean isAngled;
    private double FireSpeed1=.6;
    private double FireSpeed2=.6;
    private static final double kAdjust=.05;
    Shooter (Jaguar Fire,Jaguar Fire2,Jaguar angle,Servo launch){
        fireMotor=Fire;
        fireMotor2=Fire2;
        angleMotor=angle;
        bump=launch;
        isAngled=true;
    }
    Shooter (Jaguar Fire,Jaguar Fire2,Servo launch){
        fireMotor=Fire;
        fireMotor2=Fire2;
        bump=launch;
        isAngled=false;
    }
    public void shoot(){
        if(isAngled){
          Utils.ramp (ControlScheme.shotAngle()*.5, angleMotor, Utils.kDefaultTicksPerSecond , Utils.kDefaultRampStepSize);//Adjust Angle
          FireSpeed1=1;FireSpeed2=1;
        }
        else{
            FireSpeed1+=(ControlScheme.shotAngle()==0)?0:((ControlScheme.shotAngle()>0)?kAdjust:-kAdjust);
            FireSpeed2+=(ControlScheme.shotAngle()==0)?0:((ControlScheme.shotAngle()>0)?kAdjust:-kAdjust);
        }
        if(ControlScheme.shouldSpin()) {
            Utils.ramp(FireSpeed1, fireMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
            Utils.ramp(FireSpeed2, fireMotor2, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//Shoot if needed
        else {
            Utils.ramp(0, fireMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
            Utils.ramp(0, fireMotor2, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//Stop Shooting if needed
        bump.set(ControlScheme.getTrigger()?1:0);//Launch Disks if and only if the driver says so
        

    }
}
