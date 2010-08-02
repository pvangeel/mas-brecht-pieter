package framework.utils;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A utility class for different calculations involving angles
 * 
 * @author Rutger Claes <rutger.claes@cs.kuleuven.be>
 */
public abstract class AngleCalculations {

	/**
	 * Calculates the weighed average of a number of angles
	 * 
	 * @param 	angles		map containing the angles as keys and the weights as values
	 * @return	the average angle
	 */
	@SuppressWarnings("boxing")
	public static double calculateAverage( Map<Double,Double> angles ) {
		double totalSines = 0;
		double totalCosines = 0;
		double totalWeight = 0;
		
		for( Entry<Double,Double> angle : angles.entrySet() ) {
			double adjustedAngle  = angle.getKey() + ( angle.getValue() > 0 ? 0.0 : Math.PI );
			double adjustedWeight = Math.abs( angle.getValue() );
			
			totalSines += adjustedWeight*Math.sin( adjustedAngle );
			totalCosines += adjustedWeight*Math.cos( adjustedAngle );
			
			totalWeight += adjustedWeight;
		}
		
		if( totalWeight == 0 )
			return Math.random()*Math.PI*2;
		
		double averageSine = totalSines/totalWeight;
		double averageCosine = totalCosines/totalWeight;
		
		double result = Math.atan2( averageSine, averageCosine );
		
		return result;
	}
	
	/**
	 * Given a mapping between objects and angles, this method will select the
	 * object whose angle is closest to the <direction> angle.
	 * 
	 * @param <E>			the type of object to choose from
	 * @param direction		the desired direction
	 * @param options		the possible options
	 * @return				the option whose angle is closest to <direction>
	 */
	@SuppressWarnings("boxing")
	public static <E> E selectDirection( double direction, Map<Double,E> options ) {
		E selectedOption = null;
		double bestProjection = Double.NEGATIVE_INFINITY;
		
		for( Entry<Double,E> option : options.entrySet() ) {
			double optionProjection = Math.cos( direction - option.getKey() );
			if( optionProjection > bestProjection ) {
				bestProjection = optionProjection;
				selectedOption = option.getValue();
			}
		}
		
		return selectedOption;
	}
}
