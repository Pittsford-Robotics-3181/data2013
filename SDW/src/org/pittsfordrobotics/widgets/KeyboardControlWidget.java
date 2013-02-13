/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.*;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.ITable;
import java.awt.*;
import java.io.*;
import java.util.logging.*;
import org.jnativehook.*;
import org.jnativehook.keyboard.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class KeyboardControlWidget extends StaticWidget{
    public static final String NAME = "Keyboard Daemon Widget";
	public final CharProperty forward = new CharProperty(this,"Move Forward",'W');
	public final CharProperty backward = new CharProperty(this, "Move Backward", 'S');
	public final CharProperty left = new CharProperty(this, "Move Left", 'A');
	public final CharProperty right = new CharProperty(this, "Move Right", 'D');
	public final CharProperty up = new CharProperty(this, "Angle Up", 'E');
	public final CharProperty down = new CharProperty(this, "Angle Down", 'C');
	private byte command = 0b000000;
	private ITable table = Robot.getTable();
	private NativeKeyListener nke = new NativeKeyListener(){
		@Override
		public void nativeKeyPressed(NativeKeyEvent nke) {
			if(forward.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command |= 0b000001;
			}
			if(backward.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command |= 0b000010;
			}
			if(left.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command |= 0b000100;
			}
			if(right.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command |= 0b001000;
			}
			if(up.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command |= 0b010000;
			}
			if(down.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command |= 0b100000;
			}
			table.putNumber("Keyboard", command);
		}
		

		@Override
		public void nativeKeyReleased(NativeKeyEvent nke) {
			if(forward.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command &= ~0b000001;
			}
			if(backward.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command &= ~0b000010;
			}
			if(left.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command &= ~0b000100;
			}
			if(right.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command &= ~0b001000;
			}
			if(up.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command &= ~0b010000;
			}
			if(down.getValue().toString().equalsIgnoreCase(""+(char)nke.getKeyCode())){
				command &= ~0b100000;
			}
			table.putNumber("Keyboard", command);
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent nke) {
		}
		
	};
	

	@Override
	public void init() {
		GlobalScreen.getInstance().addNativeKeyListener(nke);
		setPreferredSize(new Dimension(32,32));
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
		else if(property!=(up) && property.equals(up)){
			up.setValue('\000');
		}
		else if(property!=(down) && property.equals(down)){
			down.setValue('\000');
		}
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		try{
		if(DashboardMenu.isEditable()){
			g2.setColor(Color.RED);
			g2.drawString("KEYBOARD",0,10);
		}
		} catch(Exception e){}
	}
}
