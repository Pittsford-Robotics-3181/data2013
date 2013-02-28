/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components.ai;

import com.sun.squawk.Method;
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
			Hardware.shootingMotor.set(1);
			Hardware.shootingMotor2.set(1);
		}
	},3000);
	
	public static final Action spinDown = new Action(0, new Performable(){
		public void performAction() {
			Hardware.shootingMotor.set(0);
			Hardware.shootingMotor2.set(0);
		}
	},50);

	public static Action merge(final Action a1, final Action a2) {
		return new Action(0, new Performable() {
			public void performAction() {
				a1.performAction();
				a2.performAction();
			}
		}, 0);
	}
}
