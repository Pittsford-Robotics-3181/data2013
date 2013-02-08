/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.robbieComponents;
import org.pittsfordrobotics.yr2013.*;
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
    public DigitalInput upSwitch;
    public DigitalInput downSwitch;
    public Climber (SpeedController climb,SpeedController angle,Solenoid uPist, DigitalInput uSwitch, DigitalInput dSwitch){
        climber=climb;
        angler=angle;
	up=uPist;//Piston that switches to gear for extending arm
	upSwitch=uSwitch;//Switch if we have reached max extent
	downSwitch=dSwitch;//Switch if we need to extend
	compress.start();
    }
    public void climb(){ 
	   Utils.ramp(ControlScheme.shouldStartClimb()?1:0, angler, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//position for climbing if needed
	   double climbDir=ControlScheme.climbDir();
	   if(climbDir>0){
	       Utils.ramp(upSwitch.get()?0:climbDir, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//move the climbing arm
	       up.set(true);
	   }
	   else if(climbDir<0){
	       Utils.ramp(downSwitch.get()?0:climbDir, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);//move the climbing arm
	       up.set(false);
	   }
	   else Utils.ramp(0, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
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

