package framework.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import framework.core.SimulationCore;
import framework.core.VirtualClock;
import framework.events.Event;
import framework.events.EventBroker;
import framework.events.EventListener;
import framework.instructions.InstructionManager;
import framework.instructions.ShutDownSimulatorInstruction;
import framework.layer.physical.position.ContinuousPosition;
import framework.utils.TimeUtils;

/**
 * A Graphical User Interface framework for alls event sent out by the simulation.  
 * 
 * @author Jelle Van Gompel & Bart Tuts
 *
 */
public abstract class GUI extends JPanel implements EventListener {

	private static final int guiWidth = 800;
	private static final int guiHeight = 600;
	private static final long repaintInterval = 100;

	private ContinuousPosition upperLeft = new ContinuousPosition(0L, 0L);
	private long milliMeterPerPixel = 5000L;

	private final GUIEventDispatcher dispatcher = new GUIEventDispatcher();
	private final Collection<GUIObject> guiObjects = new HashSet<GUIObject>();

	private long time = 0;
	private boolean update = false;
	private final Timer timer = new Timer();

	private final JFrame mainFrame = new JFrame();
	private final JFrame instructionsFrame = new JFrame();

	private final List<GUIPlugin> plugins = new ArrayList<GUIPlugin>();

	/**
	 * Creates the GUI and starts listening to all event out of the framework
	 */
	public GUI() {
		EventBroker.getEventBroker().register(this);
		initializeGUIObjectCreators();
		timer.schedule(new RepaintTask(), repaintInterval);
		initiateGUI();
	}

	/**
	 * Adds a plugin to the GUI framework
	 * Plugins will be called each time the GUI repaints
	 * 
	 * @param plugin
	 * 		The plugin to add
	 */
	public void addPlugin(GUIPlugin plugin) {
		if (plugin == null) {
			throw new IllegalArgumentException();
		}
		plugins.add(plugin);
		plugin.setGui(this);
	}

	/**
	 * Initiates the main display panel and a default instructions panel 
	 */
	private void initiateGUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().add(this);
		mainFrame.setBounds(0, 0, guiWidth, guiHeight);
		mainFrame.setVisible(true);

		instructionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instructionsFrame.getContentPane().add(createInstructionsPanel());
		instructionsFrame.setBounds(guiWidth, 0, 200, 120);
		instructionsFrame.setVisible(true);

	}

	/**
	 * Creates a default instruction panel with pause/resume and stop simulation buttons
	 * @return the instruction panel
	 */
	private JPanel createInstructionsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		final JButton pauseButton = new JButton(("Pause simulation"));
		final JButton resumeButton = new JButton(("Resume simulation"));
		final JButton stopButton = new JButton(("Stop simulation"));
		resumeButton.setEnabled(false);
		pauseButton.setEnabled(true);
		panel.add(pauseButton);
		panel.add(resumeButton);
		panel.add(stopButton);

		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimulationCore.getSimulationCore().pauseSimulation();
				resumeButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
		});
		resumeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimulationCore.getSimulationCore().resumeSimulation();
				resumeButton.setEnabled(false);
				pauseButton.setEnabled(true);
			}
		});
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InstructionManager.getInstructionManager().addInstruction(
						new ShutDownSimulatorInstruction(VirtualClock.getClock().getCurrentTime()));
			}
		});
		return panel;
	}

	/**
	 * In this method, the gui object creators have to be initialized 
	 */
	protected abstract void initializeGUIObjectCreators();

	/**
	 * Adds a guiobject to the pool of guiobjects managed by the gui and registers the event listeners of the object.
	 * 
	 * @param guiObject
	 * 		the guiobject to be added
	 */
	synchronized public void addGUIObject(GUIObject guiObject) {
		if (guiObject == null || guiObjects.contains(guiObject)) {
			throw new IllegalArgumentException();
		}
		guiObject.setGui(this);
		guiObjects.add(guiObject);
		guiObject.registerEvents();
	}

	/**
	 * Removes a guiobject from the guiobject pool and unregisters its listeners
	 *   
	 * @param guiObject
	 * 		the guiobject to be removed
	 */
	synchronized public void removeGUIObject(GUIObject guiObject) {
		if (guiObject == null || !guiObjects.contains(guiObject)) {
			throw new IllegalArgumentException();
		}
		guiObjects.remove(guiObject);
		dispatcher.removeFromListeners(guiObject);
	}

	/**
	 * Implementation of the handleEvent method from EventListener.
	 * The event is dispatched to all interested guiobjects and a flag is raised to indicate that redrawing should occur. 
	 */
	@Override
	public void handleEvent(Event event) {
		time = event.getTime();
		dispatcher.dispacthEvent(event);
		updateDrawing();
	}

	/**
	 * @return The eventdispatcher of the gui framework
	 */
	public GUIEventDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Searches the guiobject pool managed by the gui framework, for an object of the given class and id.
	 * @param c
	 * 		Class of the guiobject
	 * @param id
	 * 		Id of the guiobject
	 * 
	 * @return
	 * 		A GUIObject of the given class and id
	 */
	@SuppressWarnings("unchecked")
	synchronized public <T> T findSpecificGUIObject(Class<T> c, int id) {
		// System.out.println("GUI.findSpecificGUIObject() "+c.getSimpleName()+" "+id);
		// System.out.println("GUI.findSpecificGUIObject() "+guiObjects);
		for (GUIObject obj : guiObjects) {
			if (c.isInstance(obj) && obj.getId() == id) {
				return (T) obj;
			}
		}
		throw new IllegalArgumentException("could not find object " + c.getSimpleName() + " with id " + id);
	}

	/**
	 * Draw the current content of the managed guiobjects on the screen. 
	 * All guiplugins are invoked subsequently.
	 */
	@Override
	synchronized protected void paintComponent(Graphics g) {
		if (isUpdate()) {
			g.setColor(Color.white);
			g.fillRect(0, 0, guiWidth, guiHeight);
			g.setColor(Color.black);
			g.drawString("Time: " + TimeUtils.getTimeObject(time), 0, 20);
			for (GUIObject guiObject : new HashSet<GUIObject>(guiObjects)) {
				guiObject.draw(g);
			}
			specificDrawActions(g);
			for (GUIPlugin plugin : plugins) {
				plugin.executeDrawOperations(g);
			}
			setUpdate(false);
		}
	}

	/**
	 * Allows for specific draw operations in a subclass
	 * @param g
	 * 		The Graphics object of the screen
	 */
	protected abstract void specificDrawActions(Graphics g);

	/**
	 * Conditionally raises a flag to indicate that redrawing should occur
	 */
	private void updateDrawing() {
		if (!isUpdate()) {
			setUpdate(true);
		}
	}

	/**
	 * Raises or lowers the flag to indicate redrawing
	 * @param update
	 * 		flag
	 */
	protected void setUpdate(boolean update) {
		this.update = update;
	}

	/**
	 * @return
	 * 		True is a redraw should occur, false otherwise
	 */
	private boolean isUpdate() {
		return update;
	}

	/**
	 * @return
	 * 		The width of the gui screen
	 */
	public int getGuiWidth() {
		return guiWidth;
	}

	/**
	 * @return
	 * 		The height of the gui screen
	 */
	public int getGuiHeight() {
		return guiHeight;
	}

	/**
	 * @return
	 * 		A position object indicating the upperleft corner of the current screen being displayed
	 */
	public ContinuousPosition getUpperLeft() {
		return upperLeft;
	}

	/**
	 * Shift the drawing screen to the given upperleft corner
	 * @param upperLeft
	 * 		The corner
	 */
	protected void setUpperLeft(ContinuousPosition upperLeft) {
		this.upperLeft = upperLeft;
	}

	/**
	 * Alters the amount of millimeters represented by one pixel
	 * @param milliMeterPerPixel
	 * 		the amount of mm per pixel
	 */
	public void setMilliMeterPerPixel(long milliMeterPerPixel) {
		if (milliMeterPerPixel < 0) {
			throw new IllegalArgumentException();
		}
		this.milliMeterPerPixel = milliMeterPerPixel;
	}

	/**
	 * @return
	 * 		The amount of millimeter represented by one pixel
	 */
	public long getMilliMeterPerPixel() {
		return milliMeterPerPixel;
	}

	/**
	 * @param milliMeter
	 * @return
	 * 		The amount of pixels needed to represent the given amount of millimeter
	 */
	public int milliMeterToPixels(long milliMeter) {
		return (int) (milliMeter / getMilliMeterPerPixel());
	}

	/**
	 * @param pixels
	 * @return
	 * 		The amount of pixels represented by the given amount of pixels
	 */
	public long pixelsToMilliMeter(int pixels) {
		return pixels * getMilliMeterPerPixel();
	}

	/**
	 * Disposes of the mainscreen and instruction screen
	 */
	@Override
	public void terminate() {
		mainFrame.dispose();
		instructionsFrame.dispose();
	}

	/**
	 * Repaint task for redrawing the screen
	 */
	private class RepaintTask extends TimerTask {
		@Override
		public void run() {
			repaint();
			timer.schedule(new RepaintTask(), repaintInterval);
		}

	}

	/**
	 * @return
	 * 		The pool of GUIObjects managed by the gui framework
	 */
	public synchronized Collection<GUIObject> getGuiObjects() {
		return new HashSet<GUIObject>(guiObjects);
	}

	/**
	 * @param x
	 * @return
	 * 		The amount of pixels from the left of the screen this x-coordinate should be drawn
	 */
	public int convertX(long x) {
		long newX = (x - getUpperLeft().getX());
		return milliMeterToPixels(newX);
	}

	/**
	 * @param y
	 * @return
	 * 		The amount of pixels from the top of the screen this y-coordinate should be drawn
	 */
	public int convertY(long y) {
		long newY = (y - getUpperLeft().getY());
		return milliMeterToPixels(newY);
	}

	/**
	 * @param c
	 * @return
	 * 		The GUIObjects managed by this gui framework of the given class
	 */
	synchronized public <T> Collection<T> getGuiObjects(Class<T> c) {
		HashSet<T> collection = new HashSet<T>();
		for (GUIObject guiObject : guiObjects) {
			if (c.isInstance(guiObject)) {
				collection.add(c.cast(guiObject));
			}
		}
		return collection;
	}
}
