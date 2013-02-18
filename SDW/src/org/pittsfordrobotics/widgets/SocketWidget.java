/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *1 Client: HELLO
 *2 Server: HELLO
 *3 Client: GIVE
 *4 Server: *TRAP[]*
 *5 Client: *angle*
 *6 GOTO 3
 *7 Client: DISKINECT
 *8 Server: DISKINECT
 *9 GOTO 1
 * END
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class SocketWidget extends StaticWidget {

	private Socket sock = null;
	String read = "";
	Scanner socketScanner;

	@Override
	public void init() {
		try {
			sock = new Socket(InetAddress.getByAddress(new byte[]{10, 31, 81, 16}), 3181);
			new PrintStream(sock.getOutputStream()).println("HELLO");
		//sock.getInputStream().read(raw);
		System.out.println("read image");
		sock.close();
		}
		catch(Exception e) {
		}
	}
	
	public String read(Socket sock) throws IOException{
		char current;
		String read = "";
		while((current = (char)sock.getInputStream().read()) != '\000'){
			read += current;
		}
		return read;
	}

	@Override
	public void propertyChanged(Property property) {
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
	}
}
