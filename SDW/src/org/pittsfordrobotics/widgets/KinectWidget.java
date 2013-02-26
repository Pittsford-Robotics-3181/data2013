/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * @deprecated
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class KinectWidget extends StaticWidget{

	private BufferedImage sprite;
	private Socket sock = null;
	String read = "";
	int width = 250;
	int height = 250;
	int channels = 1;
	byte[] raw = new byte[width * height * channels];
	int[] rawint = new int[width * height * channels];
	int[] rgb = new int[width * height * channels];
	Scanner socketScanner;
	PrintWriter out;
	//BufferedReader in;
	DatagramSocket serverSocket;
	String serverHostname;
	InetAddress IPAddress;
	public void init() {
		try {
			sock = new Socket(InetAddress.getByAddress(new byte[]{10, 31, 81, 16}), 3182);
			sock.setSoTimeout(100);
			out = new PrintWriter(sock.getOutputStream());
//			in = new BufferedReader(null).;
			serverSocket = new DatagramSocket(3181);

			serverSocket.setSoTimeout(200);
			setPreferredSize(new Dimension(800, 600));
			setBackground(Color.WHITE);

			sprite = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		}
		catch(IOException ex) {
			Logger.getLogger(KinectWidget.class.getName()).log(Level.SEVERE, null, ex);
		}



	}

	public void paint(Graphics g) {
		int bufSize = (width * channels);

		int imgSize = width * height * channels;



		byte[] receiveData = new byte[bufSize];
		byte[] sendData = new byte[bufSize];

		try {

			// Connection to netbook

			int packs;


			byte[] lineNo = new byte[2];
			String fullLine;

			out.println("start");

			packs = bufSize;

			int line = 0;
			try {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				packs = width;

				lineNo[0] = receiveData[0];
				lineNo[1] = receiveData[1];

				line = ((int)lineNo[0] & 0xff) + ((int)lineNo[1] & 0xff);


				for(int j = 2; j < packs; j++) {
					raw[(line * width * channels) + (j - 0)] = receiveData[j];
				}

				for(int k = 0; k < width; k++) {

					int r = raw[(line * (width) * 1) + (1 * k)];
					int b = r;
					int green = r;

					int val = ((r & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (b & 0x0ff);
					sprite.setRGB(k, line, val);

				}

			}
			catch(SocketTimeoutException ex) {

				System.out.println("TIMEOUT");
			}

		}
		catch(UnknownHostException ex) {
			System.err.println(ex);
		}
		catch(IOException ex) {
			System.err.println(ex);
		}
		g.translate(getInsets().left, getInsets().top);
		Graphics2D g2d = (Graphics2D)g;


		g2d.drawImage(sprite, 0, 0, this);

		//Toolkit.getDefaultToolkit().sync();
		//g2d.dispose();
	}

	@Override
	public void propertyChanged(Property property) {
	}
}