/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.ITable;
import org.jnativehook.*;
import org.jnativehook.keyboard.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class KeyboardControlWidget extends StaticWidget{
    public static final String NAME = "Keyboard Daemon Widget";
	public final CharProperty forward = new CharProperty(this,"Move Forward",'w');
	public final CharProperty backward = new CharProperty(this, "Move Backward", 's');
	public final CharProperty left = new CharProperty(this, "Move Left", 'a');
	public final CharProperty right = new CharProperty(this, "Move Right", 'd');
	private byte command = 0b0;
	private NetworkTable keyTable = NetworkTable.getTable("keyboard");
	private NativeKeyListener nke = new NativeKeyListener(){

		@Override
		public void nativeKeyPressed(NativeKeyEvent nke) {
			if(forward.getValue().equals(nke.getKeyChar())){
				command |= 0b0001;
			}
			if(backward.getValue().equals(nke.getKeyChar())){
				command |= 0b0010;
			}
			if(left.getValue().equals(nke.getKeyChar())){
				command |= 0b0100;
			}
			if(right.getValue().equals(nke.getKeyChar())){
				command |= 0b1000;
			}
			keyTable.putNumber("keyboard", command);
		}
		

		@Override
		public void nativeKeyReleased(NativeKeyEvent nke) {
			if(forward.getValue().equals(nke.getKeyChar())){
				command &= ~0b1;
			}
			if(backward.getValue().equals(nke.getKeyChar())){
				command &= ~0b10;
			}
			if(left.getValue().equals(nke.getKeyChar())){
				command &= ~0b100;
			}
			if(right.getValue().equals(nke.getKeyChar())){
				command &= ~0b1000;
			}
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent nke) {
			
			
		}
		
	};
	

	@Override
	public void init() {
		GlobalScreen.getInstance().addNativeKeyListener(nke);
	}

	@Override
	public void propertyChanged(Property property) {
		if(property!=(forward) && property.equals(forward)){
			forward.setValue('\000');
		}
		else if(property!=(backward) && property.equals(backward)){
			backward.setValue('\000');
		}
		else if(property!=(left) && property.equals(left)){
			left.setValue('\000');
		}
		else if(property!=(right) && property.equals(right)){
			right.setValue('\000');
		}
	}
	
}
