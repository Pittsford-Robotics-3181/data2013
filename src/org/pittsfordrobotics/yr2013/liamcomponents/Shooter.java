/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.liamcomponents;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import org.pittsfordrobotics.yr2013.Hardware;


/**
 *
 * @author liammiddlebrook
 */
public class Shooter{
    public SpeedController angleMotor;
    public SpeedController frontMotor;
    public SpeedController backMotor;
    public Solenoid discPusher;
    private boolean isAngled;
    private double frontSpeed=1;
    private double backSpeed=1;
    private static final double kAdjust=.05;
    private boolean hasShot=false;
    public Shooter (SpeedController Fire,SpeedController Fire2,SpeedController angle,Solenoid launch){
        frontMotor=Fire;
        backMotor=Fire2;
        angleMotor=angle;
	discPusher=launch;
        isAngled=true;
    }
    public Shooter (SpeedController Fire,SpeedController Fire2,Solenoid launch){
        frontMotor=Fire;
        backMotor=Fire2;
	discPusher=launch;
	isAngled=false;
    }
    public void shoot(){
        if(angleMotor.get()!=0){
        angleMotor.set(0);
        }
        if(ControlScheme.angleDown())
        {
            angleMotor.set(.25);
        }
        if(ControlScheme.angleUp())
        {
            angleMotor.set(-.25);
        }
       if(angleMotor.get() > 0)
           Hardware.dsOutput.say(3, "Going Up");
        if(angleMotor.get() < 0)
           Hardware.dsOutput.say(3, "Going Down");
        Hardware.dsOutput.say(3, ": " + frontMotor.get() + " " + backMotor.get());
        if(ControlScheme.doShooterSpinUp()) {
            Hardware.dsOutput.say(2, "SPINNING" + frontMotor.toString());
            frontMotor.set(-1*Math.min(-1*frontMotor.get() + kAdjust, frontSpeed));
            backMotor.set(-1*Math.min(-1*backMotor.get() + kAdjust, backSpeed));
        }//Shoot if needed
        else {
           frontMotor.set(-1*Math.max(-1*frontMotor.get() - kAdjust, 0));
           backMotor.set(-1*Math.max(-1*backMotor.get() - kAdjust, 0));
        }//Stop Shooting if needed
	if(ControlScheme.doDiscPush()){
	    discPusher.set(true);
	    hasShot=true;//Don't  shoot again until trigger is released and pressed again
	}
	else hasShot=false;//Allow another shot as soon as trigger is presed
    }
}
