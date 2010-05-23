package framework.core;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import framework.experiment.ProgramParameters;

public class EnvironmentTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInitialize() {
		ProgramParameters.initialize(new HashMap<String, Object>());
		assertNotNull(ProgramParameters.getProgramParameters());
	}

	@Test
	public void testGetParameter() {
		final MyConcreteType expected = new MyConcreteType();
//		Environment.initialize(new HashMap<String, Object>() {{put("Joseph", expected);}});
		
		assertEquals(expected, ProgramParameters.getProgramParameters().getParameter("Joseph", Object.class));
	}

	@Test
	public void testGetEnvironment() {
		fail("Not yet implemented");
	}


	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetParameter() {
		fail("Not yet implemented");
	}

}

class MyConcreteType {
	
}