/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.*;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.smartdashboard.robot.Robot;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 *1 Client: HELLO
 *2 Server: HELLO
 *3 Client: GIVE
 *4 Server: *angle*
 *6 GOTO 3
 *7 Client: DISKINECT
 *8 Server: DISKINECT
 *9 GOTO 1
 * END
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class SocketWidget extends StaticWidget {

	private Socket sock = null;
	String angle  = "";
	String points = "";
	PrintStream socketPrinter;
	Scanner socketScanner;
	Thread socketCommunications;
	public final NumberProperty target = new NumberProperty(this, "Point value to target", 3);
	
	@Override
	public void init() {
		//setPreferredSize(new Dimension(DashboardFrame.getInstance().getWidth(),DashboardFrame.getInstance().getHeight()));
		setPreferredSize(new Dimension(640,488));
		try {
			sock = new Socket(InetAddress.getByAddress(new byte[]{10, 31, 81, 16}), 3182);
			socketPrinter = new PrintStream(sock.getOutputStream());
			socketScanner = new Scanner(sock.getInputStream());
			socketCommunications = new Thread(){
				public void run(){
					socketPrinter.print("HELLO");
					System.out.println("beginning to run");
					while(!socketScanner.hasNextLine()){}
					if(!socketScanner.nextLine().equals("HELLO")){
					System.err.println("WARNING: Recieved message, but it wasn't a HELLO response");
					} else{
						System.out.println("Connected");
					}
					String currentLine = "";
					while(true){
						socketPrinter.print("GIVE "+Robot.getTable().getNumber("SEARCHMODE",3));
						while(!socketScanner.hasNextLine()){}
						currentLine = socketScanner.nextLine();
						angle=currentLine.split("@")[1];
						points=currentLine.split("@")[0];
						System.out.println(angle);
						Robot.getTable().putNumber("targetangle",Double.parseDouble(angle));
						try {
							Thread.sleep(50);
						}
						catch(InterruptedException ex) {
							Logger.getLogger(SocketWidget.class.getName()).log(Level.SEVERE, null, ex);
						}
						repaint();
					}
				}
			};
			socketCommunications.start();
		//sock.getInputStream().read(raw);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChanged(Property property) {
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		try{
		g2.fillRect(Integer.parseInt(points.split(",")[0]),Integer.parseInt(points.split(",")[1]),Integer.parseInt(points.split(",")[2]),Integer.parseInt(points.split(",")[3]));
		g2.drawString(angle,20,20);
		} catch(Exception e){}
	}
}
