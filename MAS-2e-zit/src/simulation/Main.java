package simulation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import layer.agent.entities.DelegateMASDeliveryAgent;
import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;

public class Main {
	
	public static void main(String[] args) {
		
		final JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(9, 2));
		frame.setSize(400, 200);
		
		frame.add(new JLabel("nb Agents:"));
		final JTextField agents = new JTextField("3");
		frame.add(agents);
		
		frame.add(new JLabel("nb Grid Lines:"));
		final JTextField grid = new JTextField("6");
		frame.add(grid);
		
		frame.add(new JLabel("PackageInjectionRate:"));
		final JTextField packagerate = new JTextField("60");
		frame.add(packagerate);
		
		frame.add(new JLabel("packageWindow"));
		final JCheckBox packageWindow = new JCheckBox();
		frame.add(packageWindow);
		
		frame.add(new JLabel("truckWindow"));
		final JCheckBox truckWindow = new JCheckBox();
		frame.add(truckWindow);
		
		frame.add(new JLabel("crossroadWindow"));
		final JCheckBox crWindow = new JCheckBox();
		frame.add(crWindow);
		
		
		JButton button = new JButton("Start");
		frame.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PDPPackage.createWindow = packageWindow.isSelected();
				Crossroads.withVisuals = crWindow.isSelected();
				DelegateMASDeliveryAgent.withVisuals = truckWindow.isSelected();
				new DelegateMasSimulation(Integer.valueOf(agents.getText()), Integer.valueOf(grid.getText()), Integer.valueOf(packagerate.getText()));
			}
		});
		frame.setVisible(true);
	}

}
