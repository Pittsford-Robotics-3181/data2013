/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Given an extended JPanel and URL read and create BufferedImages to be displayed from a MJPEG stream
 * @author shrub34 Copyright 2012
 * Free for reuse, just please give me a credit if it is for a redistributed package
 */
public class MJPEGWidget extends StaticWidget implements Runnable
{
	private static final String CONTENT_LENGTH = "Content-length: ";
	private static final String CONTENT_TYPE = "Content-type: image/jpeg";
	private BufferedImage image;
	private InputStream urlStream;
	private StringWriter stringWriter;
	private boolean processing = true;
	
	@Override
	public void init(){
		try {
			URLConnection urlConn = new URL("http://10.31.81.10/axis-cgi/mjpg/video.cgi?resolution=640x480&req_fps=20&.mjpg").openConnection();
			// change the timeout to taste, I like 1 second
			urlConn.setReadTimeout(50);
			urlConn.connect();
			urlStream = urlConn.getInputStream();
			stringWriter = new StringWriter(128);
		}
		catch(IOException ex) {
			Logger.getLogger(MJPEGWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Stop the loop, and allow it to clean up
	 */
	public synchronized void stop()
	{
		processing = false;
	}
	
	/**
	 * Keeps running while process() returns true
	 * 
	 * Each loop asks for the next JPEG image and then sends it to our JPanel to draw
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		while(processing)
		{
			try
			{
				byte[] imageBytes = retrieveNextImage();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
				
				BufferedImage image = ImageIO.read(bais);
				this.image = image;
				
				repaint();
			}catch(SocketTimeoutException ste){
				System.err.println("failed stream read: " + ste);
				//viewer.setFailedString("Lost Camera connection: " + ste);
				repaint();
				stop();
			}catch(IOException e){
				System.err.println("failed stream read: " + e);
				stop();
			}
		}
		
		// close streams
		try
		{
			urlStream.close();
		}catch(IOException ioe){
			System.err.println("Failed to close the stream: " + ioe);
		}
	}
	
	/**
	 * Using the <i>urlStream</i> get the next JPEG image as a byte[]
	 * @return byte[] of the JPEG
	 * @throws IOException
	 */
	private byte[] retrieveNextImage() throws IOException
	{
		boolean haveHeader = false; 
		int currByte = -1;
		
		String header = null;
		// build headers
		// the DCS-930L stops it's headers
		while((currByte = urlStream.read()) > -1 && !haveHeader)
		{
			stringWriter.write(currByte);
			
			String tempString = stringWriter.toString(); 
			int indexOf = tempString.indexOf(CONTENT_TYPE);
			if(indexOf > 0)
			{
				haveHeader = true;
				header = tempString;
			}
		}		
		
		// 255 indicates the start of the jpeg image
		while((urlStream.read()) != 255)
		{
			// just skip extras
		}
		
		// rest is the buffer
		int contentLength = contentLength(header);
		byte[] imageBytes = new byte[contentLength + 1];
		// since we ate the original 255 , shove it back in
		imageBytes[0] = (byte)255;
		int offset = 1;
        int numRead = 0;
        while (offset < imageBytes.length
               && (numRead=urlStream.read(imageBytes, offset, imageBytes.length-offset)) >= 0) {
            offset += numRead;
        }       
		
		stringWriter = new StringWriter(128);
		
		return imageBytes;
	}

	// dirty but it works content-length parsing
	private static int contentLength(String header)
	{
		int indexOfContentLength = header.indexOf(CONTENT_LENGTH);
		int valueStartPos = indexOfContentLength + CONTENT_LENGTH.length();
		int indexOfEOL = header.indexOf('\n', indexOfContentLength);
		
		String lengthValStr = header.substring(valueStartPos, indexOfEOL).trim();
		
		int retValue = Integer.parseInt(lengthValStr);
		
		return retValue;
	}

	@Override
	public void propertyChanged(Property property) {
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, null, 640, 480);
	}
}