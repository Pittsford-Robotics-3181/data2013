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
public class Climber implements Loggable{
    public Jaguar climber;
    public Jaguar angler;
    Climber (Jaguar climb,Jaguar angle){
        climber=climb;
        angler=angle;
    }
    public void climb(){ 
        if(ControlScheme.getClimbStart()) {
            Utils.ramp(1, angler, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//climb if needed
       else {
            Utils.ramp(0, angler, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//climb if needed
        if(ControlScheme.getClimbStart()) {
           Utils.ramp(1, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//climb if needed
       else {
            Utils.ramp(0, climber, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
        }//climb if needed
    }
    public String logString() {
        return"";
    }

}
