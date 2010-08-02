package framework.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A GUIPlugin to provide zooming and panning functionality
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public class ZoomPanningGUIPlugin extends GUIPlugin implements MouseListener {

	private Point panningStartPoint;

	@Override
	protected void addedToGui() {
		getGui().addMouseListener(this);
	}

	@Override
	public void executeDrawOperations(Graphics g) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.isControlDown()) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				zoom(0.5, e.getPoint());
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				zoom(2, e.getPoint());
			}
		}
	}

	private void zoom(double zoomFactor, Point center) {
		long centerX = getGui().pixelsToMilliMeter(center.x) + getGui().getUpperLeft().getX();
		long centerY = getGui().pixelsToMilliMeter(center.y) + getGui().getUpperLeft().getY();
		getGui().setMilliMeterPerPixel((long) Math.round(getGui().getMilliMeterPerPixel() * zoomFactor));
		getGui().getUpperLeft().setX(centerX - getGui().pixelsToMilliMeter(getGui().getGuiWidth() / 2));
		getGui().getUpperLeft().setY(centerY - getGui().pixelsToMilliMeter(getGui().getGuiHeight() / 2));
		getGui().setUpdate(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (panningStartPoint != null) {
			throw new IllegalStateException();
		}
		panningStartPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (panningStartPoint == null) {
			throw new IllegalStateException();
		}
		int diffX = panningStartPoint.x - e.getX();
		int diffY = panningStartPoint.y - e.getY();
		getGui().getUpperLeft().setX(getGui().getUpperLeft().getX() + getGui().pixelsToMilliMeter(diffX));
		getGui().getUpperLeft().setY(getGui().getUpperLeft().getY() + getGui().pixelsToMilliMeter(diffY));
		panningStartPoint = null;
		getGui().setUpdate(true);
	}
}
