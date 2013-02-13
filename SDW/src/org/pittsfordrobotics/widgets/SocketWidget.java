/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import java.awt.*;
import java.net.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class SocketWidget extends StaticWidget{
	private Socket sock = null;
	String read;
	@Override
	public void init() {
		try{
			sock = new Socket(InetAddress.getByAddress(new byte[]{10,31,81,18}),3181);
		} catch(Exception e){}
	}

	@Override
	public void propertyChanged(Property property) {
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		try{
			read += (char)sock.getInputStream().read();
		} catch(Exception e){}
		g2.drawString(read,0,0);
	}
	
}
