package gui;

import java.awt.Graphics;

//import pclabs4.gui.GUIGradientFieldCreator;
//import pclabs4.gui.GUIPackageCreator;
//import pclabs4.gui.GUIPackagePickedCreator;
import framework.gui.GUI;
import framework.gui.ZoomPanningGUIPlugin;
import framework.layer.physical.position.ContinuousPosition;
import gui.creators.GUICommunicationCapabilityCreator;
import gui.creators.GUICommunicationLinkCreator;
import gui.creators.GUICrossroadsCreator;
import gui.creators.GUIDeviceCreator;
import gui.creators.GUIOneWayRoadCreator;
import gui.creators.GUIStorageCapabilityCreator;
import gui.creators.GUITruckCreator;
import gui.creators.GUITwoWayRoadCreator;

public class SimpleGUI extends GUI {

	private static final long serialVersionUID = 1L;

	public SimpleGUI() {
		// LLPosition beginPosition = new LLPosition(50.8780898, 4.7086349);
		// setUpperLeft(new ContinuousPosition(beginPosition.getX(),
		// beginPosition.getY()));
		setUpperLeft(new ContinuousPosition(6694635603L, 4002725795L));
//		setUpperLeft(new ContinuousPosition(0L, 0L));
		
		addPlugin(new ZoomPanningGUIPlugin());
	}

	@Override
	protected void initializeGUIObjectCreators() {
		new GUIOneWayRoadCreator(this);
		new GUITwoWayRoadCreator(this);
		new GUICrossroadsCreator(this);

		new GUIDeviceCreator(this);
		new GUIStorageCapabilityCreator(this);
		new GUICommunicationCapabilityCreator(this);
		new GUICommunicationLinkCreator(this);
		
		new GUITruckCreator(this);
		new GUIPackageCreator(this);
		new GUIPackagePickedCreator(this);
		new GUIGradientFieldCreator(this);
	}

	@Override
	protected void specificDrawActions(Graphics g) {
	}

}
