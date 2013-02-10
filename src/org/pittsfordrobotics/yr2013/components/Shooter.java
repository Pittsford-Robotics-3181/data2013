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
        double speed = -frontMotor.get();
        
        double speed2 = -backMotor.get();
        if(ControlScheme.doShoot())
        {
            discPusher.set(true);
            Timer.delay(0.01);
            discPusher.set(false);
        }
        if(ControlScheme.doShooterSpin())
        {
            if(speed < maxSpeed)
            {
                speed += 0.01;
            }
            if(speed2 < maxSpeed)
            {
                speed2 += 0.01;
            }
        }else{
            
            if(speed > 0.0)
            {
             speed -= 0.01;   
            }
            if(speed2 > 0.0)
            {
             speed2 -= 0.01;   
            }
        }
        frontMotor.set(-speed);
        backMotor.set(-speed2);
        
    }
    
}
