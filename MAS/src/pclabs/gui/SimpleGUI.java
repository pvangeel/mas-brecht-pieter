package pclabs.gui;

import java.awt.Graphics;

import pclabs.gui.creators.GUICommunicationCapabilityCreator;
import pclabs.gui.creators.GUICommunicationLinkCreator;
import pclabs.gui.creators.GUICrossroadsCreator;
import pclabs.gui.creators.GUIDeviceCreator;
import pclabs.gui.creators.GUIOneWayRoadCreator;
import pclabs.gui.creators.GUIStorageCapabilityCreator;
import pclabs.gui.creators.GUITruckCreator;
import pclabs.gui.creators.GUITwoWayRoadCreator;
import pclabs4.gui.GUIPackageCreator;
import pclabs4.gui.GUIPackagePickedCreator;
import framework.gui.GUI;
import framework.layer.physical.position.ContinuousPosition;

public class SimpleGUI extends GUI {

	private static final long serialVersionUID = 1L;

	public SimpleGUI() {
		// LLPosition beginPosition = new LLPosition(50.8780898, 4.7086349);
		// setUpperLeft(new ContinuousPosition(beginPosition.getX(),
		// beginPosition.getY()));
		setUpperLeft(new ContinuousPosition(6694635603L, 4002725795L));
//		setUpperLeft(new ContinuousPosition(0L, 0L));
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
	}

	@Override
	protected void specificDrawActions(Graphics g) {
	}

}
