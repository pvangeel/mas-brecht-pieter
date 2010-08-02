package framework.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

/**
 * Class to create an arrowshape that can be drawn on the screen
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public class ArrowShape {

		private final double flange = 6;

		private GeneralPath generalPath;
		private final double r;
		private final double radius = 2;
		private final double theta;
		private final double tip = 20;
		private final double x0, y0;

		public ArrowShape(double x0, double y0, double length, double theta) {
			this.x0 = x0;
			this.y0 = y0;
			this.r = length;
			this.theta = theta;
			createGeneralPath();
		}

		private void createGeneralPath() {
			// tip = head height tip to flange / \
			// flange = max width of the head /__ __\
			// radius = half the shaft width | |
			double x1, y1, x2, y2, x3, y3, x4, y4, x5, y5, x6, y6, x7, y7; // | |
			x1 = x0 + radius * Math.cos(Math.toRadians(theta - 90));
			y1 = y0 + radius * Math.sin(Math.toRadians(theta - 90));
			Shape s0 = new Line2D.Double(x0, y0, x1, y1);
			// g2.draw(s0);
			double thetaOffset = 90 - Math.toDegrees(Math.atan((r - tip - 8) / radius));
			x2 = x0 + (r - tip - 8) * Math.cos(Math.toRadians(theta - thetaOffset));
			y2 = y0 + (r - tip - 8) * Math.sin(Math.toRadians(theta - thetaOffset));
			Shape s1 = new Line2D.Double(x1, y1, x2, y2);
			// g2.draw(s1);
			double flangeOffset = 90 - Math.toDegrees(Math.atan((r - tip - 8) / (radius + flange)));
			x3 = x0 + (r - tip - 8) * Math.cos(Math.toRadians(theta - flangeOffset));
			y3 = y0 + (r - tip - 8) * Math.sin(Math.toRadians(theta - flangeOffset));
			Shape s2 = new Line2D.Double(x2, y2, x3, y3);
			// g2.draw(s2);
			x4 = x0 + (r - 8) * Math.cos(Math.toRadians(theta));
			y4 = y0 + (r - 8) * Math.sin(Math.toRadians(theta));
			Shape s3 = new Line2D.Double(x3, y3, x4, y4);
			// g2.draw(s3);
			x5 = x0 + (r - tip - 8) * Math.cos(Math.toRadians(theta + flangeOffset));
			y5 = y0 + (r - tip - 8) * Math.sin(Math.toRadians(theta + flangeOffset));
			Shape s4 = new Line2D.Double(x4, y4, x5, y5);
			// g2.draw(s4);
			x6 = x0 + (r - tip - 8) * Math.cos(Math.toRadians(theta + thetaOffset));
			y6 = y0 + (r - tip - 8) * Math.sin(Math.toRadians(theta + thetaOffset));
			Shape s5 = new Line2D.Double(x5, y5, x6, y6);
			// g2.draw(s5);
			x7 = x0 + radius * Math.cos(Math.toRadians(theta + 90));
			y7 = y0 + radius * Math.sin(Math.toRadians(theta + 90));
			Shape s6 = new Line2D.Double(x6, y6, x7, y7);
			// g2.draw(s6);
			Shape s7 = new Line2D.Double(x7, y7, x0, y0);
			// g2.draw(s7);
			GeneralPath needle = new GeneralPath(s0);
			needle.append(s1, true);
			needle.append(s2, true);
			needle.append(s3, true);
			needle.append(s4, true);
			needle.append(s5, true);
			needle.append(s6, true);
			needle.append(s7, true);
			generalPath = needle;
		}

		public GeneralPath getGeneralPath() {
			return generalPath;
		}
		
		public static ArrowShape createArrowShape(double x0, double y0, double x1, double y1) {
				double length = Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow((y1 - y0), 2));
				double angle = Math.toDegrees(-Math.atan2(x1 - x0, y1 - y0) + Math.PI / 2);
				return new ArrowShape(x0, y0, length, angle);
		}
		
		public void draw(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(Color.black);
			g2.fill(getGeneralPath());
		}

	}
