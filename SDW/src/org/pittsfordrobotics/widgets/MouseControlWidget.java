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
import org.jnativehook.*;
import org.jnativehook.keyboard.*;
import org.jnativehook.mouse.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class MouseControlWidget extends StaticWidget{
    public static final String NAME = "Mouse Daemon Widget";
	private byte command = 0b00;
	private ITable table = Robot.getTable();
	private NativeMouseListener nml = new NativeMouseListener(){

		@Override
		public void nativeMouseClicked(NativeMouseEvent nme) {
			nativeMousePressed(nme);
			nativeMouseReleased(nme);
		}

		@Override
		public void nativeMousePressed(NativeMouseEvent nme) {
			if(nme.getButton()==1){
				command |= 0b01;
			}
			if(nme.getButton()==3 || nme.getButton()==2){
				command |= 0b10;
			}
			table.putNumber("MouseButtons",command);
		}

		@Override
		public void nativeMouseReleased(NativeMouseEvent nme) {
			if(nme.getButton()==1){
				command &= ~0b01;
			}
			if(nme.getButton()==3 || nme.getButton()==2){
				command &= ~0b10;
			}
			table.putNumber("MouseButtons",command);
		}
		
	};
	
	@Override
	public void init() {
		GlobalScreen.getInstance().addNativeMouseListener(nml);
		setPreferredSize(new Dimension(32,32));
	}

	@Override
	public void propertyChanged(Property property) {
		
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		try{
		if(DashboardMenu.isEditable()){
			g2.setColor(Color.RED);
			g2.drawString("MOUSE",0,10);
		}
		}
		catch(Exception e){}
	}
}