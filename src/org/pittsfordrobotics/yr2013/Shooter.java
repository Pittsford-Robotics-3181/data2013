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
public class Shooter implements Loggable{
    public Jaguar angleMotor;
    public Jaguar fireMotor1;
    public Jaguar fireMotor2;
    public Servo bump;
    private boolean isAngled;
    private double fireSpeed1=.6;
    private double fireSpeed2=.6;
    private static final double kAdjust=.05;
    Shooter (Jaguar Fire,Jaguar Fire2,Jaguar angle,Servo launch){
        fireMotor1=Fire;
        fireMotor2=Fire2;
        angleMotor=angle;
        bump=launch;
        isAngled=true;
    }
    Shooter (Jaguar Fire,Jaguar Fire2,Servo launch){
        fireMotor1=Fire;
        fireMotor2=Fire2;
        bump=launch;
        isAngled=false;
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
        bump.set(ControlScheme.getTrigger()?1:0);//Launch Disks if and only if the driver says so
        

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
