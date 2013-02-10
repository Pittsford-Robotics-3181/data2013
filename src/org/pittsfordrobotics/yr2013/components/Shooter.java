/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author liam
 */
public class Shooter extends Thread {
    SpeedController frontMotor, backMotor, angleMotor;
    Solenoid discPusher;
    double maxSpeed = 0.75;
    public Shooter(SpeedController frontMotor, SpeedController backMotor, SpeedController angleMotor, Solenoid discPusher)
     {
         this.frontMotor = frontMotor;
         this.backMotor = backMotor;
         this.angleMotor = angleMotor;
         this.discPusher = discPusher;
     }
    @override
    public void run()
    {
        if(ControlScheme.doShoot())
        {
            discPusher.set(true);
            Timer.delay(0.01);
            discPusher.set(false);
        }
        if(ControlScheme.doShooterSpin())
        {
            double speed = 0.0;
            if(speed < maxSpeed)
            {
                
            }
        }
        
    }
    
}
