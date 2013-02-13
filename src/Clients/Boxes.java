package Clients;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Middle.OrderProcessing;
import Processing.Order;

@SuppressWarnings("serial")
public class Boxes extends JFrame {

	public void paintComponents(Graphics g){
		super.paintComponents(g);
		g.setColor(Color.black);
		g.fillRect(100, 100, 100, 100);
	}
	public Dimension getPreferredSize(){
		return new Dimension(100,100);
	}
	public Dimension getMinimumSize(){
		return getPreferredSize();
	}
}


