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
			//Hardware.shooter.discPusher.set(true);
		}
	}, 50), new Action(0, new Performable() {
		public void performAction() {
			//Hardware.shooter.discPusher.set(false);
		}
	}, 0));

	public static Action merge(final Action a1, final Action a2) {
		return new Action(0, new Performable() {
			public void performAction() {
				a1.performAction();
				a2.performAction();
			}
		}, 0);
	}
}
