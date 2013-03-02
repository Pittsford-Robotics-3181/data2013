/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components.ai;

import com.sun.squawk.Method;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.pittsfordrobotics.yr2013.*;
import org.pittsfordrobotics.yr2013.components.Shooter;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class CommonActions {

	public static final Action shoot = merge(new Action(0, new Performable() {
		public void performAction() {
			Hardware.shootLaunch.set(true);
		}
	}, 500), new Action(0, new Performable() {
		public void performAction() {
			Hardware.shootLaunch.set(false);
		}
	}, 1000));
	
	public static final Action spinUp = new Action(0, new Performable(){
		public void performAction() {
			Hardware.shootingMotor.set(-1);
			Hardware.shootingMotor2.set(-1);
		}
	},3000);
	
	public static final Action spinDown = new Action(0, new Performable(){
		public void performAction() {
			Hardware.shootingMotor.set(0);
			Hardware.shootingMotor2.set(0);
		}
	},50);
        public static final Action moveToKinectAngle = new Action(0, new Performable(){
		public void performAction() {
                    final double TOLERANCE = 2;
                    final double MIN = 20;
                    final double MAX = 40;
                    final double DEFAULT = 29.6;
                    for(;;)
                    {   
                      double desiredAngle = DEFAULT;/*
                     if(SmartDashboard.getNumber("targetangle",DEFAULT) > MIN && SmartDashboard.getNumber("targetangle",DEFAULT) < MAX )
                     {
                         desiredAngle = SmartDashboard.getNumber("targetangle",DEFAULT);
                     }//*/
                     desiredAngle = Math.min(MAX, Math.max(desiredAngle, MIN));
                     Hardware.DSOut.say(5,"Desired Angle: " + desiredAngle);
                        if(Hardware.angleSensor.getAngleInStupidDegrees() > desiredAngle && Math.abs(desiredAngle - Hardware.angleSensor.getAngleInStupidDegrees()) > TOLERANCE)
                        {
                            Hardware.shotAngleMotor.set(0.5);
                        }
                        if(Hardware.angleSensor.getAngleInStupidDegrees() < desiredAngle && Math.abs(desiredAngle - Hardware.angleSensor.getAngleInStupidDegrees()) > TOLERANCE)
                        {
                            Hardware.shotAngleMotor.set(-0.5);
                        }
                        if(Math.abs(desiredAngle - Hardware.angleSensor.getAngleInStupidDegrees()) < TOLERANCE)
                        {
                            Hardware.shotAngleMotor.set(0);
                            return;
                        }
                    }
                
		}
	},0);
        
        
        
        
        
        
	public static final Action GomoveToKinectAngle = new Action(0, new Performable(){
		public void performAction() {
                    Hardware.DSOut.say(1, "" + Math.max(25,Math.min(35,SmartDashboard.getNumber("targetangle",27.5))));
			while(Math.abs(Hardware.angleSensor.getAngleInStupidDegrees() - Math.max(25,Math.min(35,SmartDashboard.getNumber("targetangle",27.5)))) > 3){
                            Hardware.DSOut.say(3, "" + (Hardware.angleSensor.getAngleInStupidDegrees() < Math.max(25,Math.min(35,SmartDashboard.getNumber("targetangle",27.5))) ? -1 : 1));
                            Hardware.shotAngleMotor.set(Hardware.angleSensor.getAngleInStupidDegrees() < Math.max(25,Math.min(35,SmartDashboard.getNumber("targetangle",27.5))) ? -1 : 1);
                            if(Hardware.angleSensor.getAngleInStupidDegrees() > 35)
                            {
                                Hardware.DSOut.say(2,"Over Up");
                                Hardware.shotAngleMotor.set(1);
                                Timer.delay(1);
                                return;
                            }
                            if(Hardware.angleSensor.getAngleInStupidDegrees()<17 )
                            {
                                Hardware.DSOut.say(2, "Over Down");
                                Hardware.shotAngleMotor.set(-1);
                                Timer.delay(1);
                                return;
                            }
                        }
		}
	},0);

	public static Action merge(final Action a1, final Action a2) {
		return new Action(0, new Performable() {
			public void performAction() {
				a1.performAction();
				a2.performAction();
			}
		}, 0);
	}
}
