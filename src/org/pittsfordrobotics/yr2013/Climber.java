/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author robbiemarkwick
 */
public class Climber implements Loggable{
    public SpeedController climber;
    public SpeedController angler;
    public Compressor compress;
    public Solenoid up;
    public Solenoid down;
    public DigitalInput upSwitch;
    public DigitalInput downSwitch;
    boolean didSwitchGears=false;
    Climber (SpeedController climb,SpeedController angle,Compressor gear,Solenoid uPist, Solenoid dPist, DigitalInput uSwitch, DigitalInput dSwitch){
        climber=climb;
        angler=angle;
	compress=gear;
	up=uPist;//Piston that switches to gear for extending arm
	down=dPist;//Piston that switches to gear for climbing up
	upSwitch=uSwitch;//Switch if we have reached max extent
	downSwitch=dSwitch;//Switch if we need to extend
	compress.start();
    }
    public void climb(){ 
	   Utils.ramp(ControlScheme.shouldStartClimb()?1:0, angler, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//position for climbing if needed
	   double climbDir=ControlScheme.climbDir();
	   if(climbDir>0&&!upSwitch.get()){
	       Utils.ramp(climbDir, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//move the climbing arm
	       didSwitchGears=false;
	   }
	   else if(climbDir<0&&!downSwitch.get()){
	       Utils.ramp(climbDir, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//move the climbing arm
	       didSwitchGears=false;
	   }
	   up.set(downSwitch.get()&&!didSwitchGears);
	   down.set(upSwitch.get()&&!didSwitchGears);
	   didSwitchGears=true;


    }
    public String logString() {
	String xmlString="<climber>\n";
        xmlString=xmlString.concat("<beginMotor>\n"+
                "<target>"+(ControlScheme.shouldStartClimb()?1:0)+"</target>\n"+
                "<current>"+angler.get()+"</current>\n"+
                "</beginMotor>\n");
	xmlString=xmlString.concat("<climbMotor>\n"+
                "<target>"+(ControlScheme.climbDir())+"</target>\n"+
                "<current>"+climber.get()+"</current>\n"+
                "</climbMotor>\n");
        xmlString=xmlString.concat("</climber>");
        return xmlString;
    }
}
