/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.*;
import javax.microedition.io.Connector;

/**
 * @TODO make this more xmlish
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class RobotLog extends PrintStream {

	public RobotLog() throws IOException {
		super(((FileConnection)Connector.open("robot.log")).openOutputStream());
	}

	public RobotLog(String logFileName) throws IOException {
		super(((FileConnection)Connector.open(logFileName)).openOutputStream());
	}

	private void timeStamp() {
		super.print(System.currentTimeMillis() / 3600000 + ":" + System.currentTimeMillis() / 60000 + ":" + System.currentTimeMillis() / 1000.0 + " --> ");
	}

	public void print(boolean b) {
		timeStamp();
		super.print(b);
	}

	public void print(int i) {
		timeStamp();
		super.print(i);
	}

	public void print(long l) {
		timeStamp();
		super.print(l);
	}

	public void print(float f) {
		timeStamp();
		super.print(f);
	}

	public void print(double d) {
		timeStamp();
		super.print(d);
	}

	public void print(char[] s) {
		timeStamp();
		super.print(s);
	}

	public void print(String s) {
		timeStamp();
		super.print(s);
	}

	public void print(Object o) {
		timeStamp();
		super.print(o);
	}

	public void printComment(String s) {
		super.print("<!--" + s + "-->");
	}

	public void printCommentLine(String s) {
		super.print("<!--");
		println();
		super.print(s);
		println();
		super.print("-->");
		println();
	}
}