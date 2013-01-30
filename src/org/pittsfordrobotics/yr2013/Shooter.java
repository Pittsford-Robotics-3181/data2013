/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;


/**
 *
 * @author robbiemarkwick
 */
public class Shooter implements Loggable{
    public SpeedController angleMotor;
    public SpeedController fireMotor1;
    public SpeedController fireMotor2;
    public Solenoid bump;
    private boolean isAngled;
    private double fireSpeed1=.6;
    private double fireSpeed2=.6;
    private static final double kAdjust=.05;
    private boolean hasShot=false;
    Shooter (SpeedController Fire,SpeedController Fire2,SpeedController angle,Compressor comp,Solenoid launch){
        fireMotor1=Fire;
        fireMotor2=Fire2;
        angleMotor=angle;
	bump=launch;
        isAngled=true;
	comp.start();
    }
    Shooter (SpeedController Fire,SpeedController Fire2,Compressor comp,Solenoid launch){
        fireMotor1=Fire;
        fireMotor2=Fire2;
	bump=launch; 
	isAngled=false;
	comp.start();
    }
    public void shoot(){
        if(isAngled){
          Utils.ramp (ControlScheme.shotAngle()*.5, angleMotor, Utils.kDefaultTicksPerSecond , Utils.kDefaultRampStepSize);//Adjust Angle
          fireSpeed1=1;fireSpeed2=1;
        }
        else{
            fireSpeed1+=(ControlScheme.shotAngle()==0)?0:((ControlScheme.shotAngle()>0)?kAdjust:-kAdjust);
            fireSpeed2+=(ControlScheme.shotAngle()==0)?0:((ControlScheme.shotAngle()>0)?kAdjust:-kAdjust);
        }
        if(ControlScheme.shouldSpin()) {
            Utils.ramp(fireSpeed1, fireMotor1, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
            Utils.ramp(fireSpeed2, fireMotor2, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//Shoot if needed
        else {
            Utils.ramp(0, fireMotor1, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
            Utils.ramp(0, fireMotor2, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//Stop Shooting if needed
	if(!ControlScheme.shouldShoot()){
	    bump.set(!hasShot);//Launch Disks if and only if the driver says so
	    hasShot=true;//Don't  shoot again until trigger is released and pressed again
	}
	else hasShot=false;//Allow another shot as soon as trigger is presed
    }

    public String logString() {
	String xmlString="<shooter>\n";
        xmlString=xmlString.concat("<angleMotor>"+angleMotor.get()+"</angleMotor>\n");
        xmlString=xmlString.concat("<fireMotor1>\n"+
                "<target>"+fireSpeed1+"</target>\n"+
                "<current>"+fireMotor1.get()+"</current>\n"+
                "</fireMotor1>\n");
	xmlString=xmlString.concat("<fireMotor2>\n"+
                "<target>"+fireSpeed2+"</target>\n"+
                "<current>"+fireMotor2.get()+"</current>\n"+
                "</fireMotor1>\n");
	xmlString=xmlString.concat("<servo>"+bump.get()+"</servo>\n");
        xmlString=xmlString.concat("</shooter>");
        return xmlString;
    }
}
