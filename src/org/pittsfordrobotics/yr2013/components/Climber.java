/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import org.pittsfordrobotics.yr2013.ControlScheme;
import org.pittsfordrobotics.yr2013.Loggable;
/**
 *
 * @author liam
 */
public class Climber extends Thread implements Loggable{

	Solenoid preClimbSolenoid;
	//Solenoid switchGearSolenoid;
	Solenoid tiltRobotSolenoid;
	SpeedController climbMotor;
	DigitalInput up;
	DigitalInput down;
	boolean robotTilted;
	boolean isMovingUp=true;//should the arm be extending

	public Climber(Solenoid tiltRobotSolenoid, Solenoid preClimbSolenoid, SpeedController climbMotor,DigitalInput up, DigitalInput down) {
		this.climbMotor = climbMotor;
		this.preClimbSolenoid = preClimbSolenoid;
		this.tiltRobotSolenoid = tiltRobotSolenoid;
		this.up=up;
		this.down=down;
	}

	public void run() {
		preClimbSolenoid.set(ControlScheme.tiltRobot());
		tiltRobotSolenoid.set(ControlScheme.tiltRobot());
		robotTilted = ControlScheme.tiltRobot()||robotTilted;
		/*if(robotTilted && ControlScheme.switchGears()) {
			preClimbSolenoid.set(true);
			Timer.delay(0.1);
			switchGearSolenoid.set(true);
			gearsSwitched = true;
		}*/
		if(ControlScheme.doClimb()) {
			if(isMovingUp&&up.get())isMovingUp=false;
			else if (!isMovingUp&&down.get())isMovingUp=true;
			climbMotor.set(1.0*(isMovingUp?1:-1));
		}
	}
	public String logString() {
	String xmlString="<climber>\n";
        xmlString=xmlString.concat("<tilt>\n"+(tiltRobotSolenoid.get()?"true":"false")+"</current>\n"+
                "</tilt>\n");
	xmlString=xmlString.concat("<climbMotor>\n"+
                "<target>"+(ControlScheme.doClimb()?1.0*(isMovingUp?1:-1):0)+"</target>\n"+
                "<current>"+climbMotor.get()+"</current>\n"+
                "</climbMotor>\n");
        xmlString=xmlString.concat("</climber>");
        return xmlString;
    }
	
}
