package layer.devices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;
import layer.physical.entities.Road;
 
//import be.kuleuven.cs.distrinet.agentwise.traffic.coordination.agents.AgentRegistry;
//import be.kuleuven.cs.distrinet.agentwise.traffic.coordination.agents.road.TMCRoadAgent;
//import be.kuleuven.cs.distrinet.agentwise.traffic.model.traffic.network.element.Direction;
//import be.kuleuven.cs.distrinet.agentwise.traffic.model.traffic.network.element.Junction;
//import be.kuleuven.cs.distrinet.agentwise.traffic.model.traffic.network.element.Segment;
//import be.kuleuven.cs.distrinet.agentwise.traffic.util.Conversion;
 
/**
 * http://en.wikipedia.org/wiki/A*_search_algorithm
 *
 * @author rutger
 */
public abstract class AStarRouter {
 
	@SuppressWarnings("boxing")
	public Trajectory calculateTrajectory(PDPPackage pdpPackage, final long currentTime, final Crossroads start, final Crossroads end) {
		if( start == null ) {
			throw new IllegalArgumentException( "Start should not be null" );
		}
		if( end == null ) {
			throw new IllegalArgumentException( "End should not be null" );
		}
 
		final Trajectory trajectory = new Trajectory(pdpPackage);
 
		final Set<Crossroads> closedSet = new HashSet<Crossroads>();
 
		final Map<Crossroads,Float> g_score = new HashMap<Crossroads, Float>();
		g_score.put( start, 0f );
 
		final Map<Crossroads,Float> h_score = new HashMap<Crossroads, Float>();
		h_score.put( start, this.calculateHeuristic( start.distanceTo( end ) ) );
 
		final SortedMap<Float,Crossroads> f_score = new TreeMap<Float,Crossroads>();
		f_score.put( this.calculateHeuristic( start.distanceTo( end ) ), start );
 
		final HashMap<Crossroads,Crossroads> came_from = new HashMap<Crossroads, Crossroads>();
 
		while( !f_score.isEmpty() ) {
			final Crossroads x = f_score.remove( f_score.firstKey() );
 
//			if( x.equals( end ) ) {
			if( x.getPosition().getX() == end.getPosition().getX() && x.getPosition().getY() == end.getPosition().getY()) {
				final List<Crossroads> result = this.reconstructPath( came_from, end );
//				result.add( 0, start );
				trajectory.load( result );
				return trajectory;
			}
 
			closedSet.add( x );
 
			for( final Road outgoingDirection : x.getOutgoingConnections() ) {
				
				final Crossroads y = outgoingDirection.getOtherConnector(x);
				
				if( closedSet.contains( y ) ) {
					continue;
				}
 
				final float t_g_score = g_score.get( start ) + this.calculateCostOff( outgoingDirection );
				boolean t_is_better = false;
 
				if( !f_score.values().contains( y ) ) {
					h_score.put( y, this.calculateHeuristic( y.distanceTo( end ) ) );
					t_is_better = true;
				}
				else if( t_g_score < g_score.get( y ) ) {
					t_is_better = true;
				}
 
				if( t_is_better ) {
					came_from.put( y, x );
					g_score.put( y, t_g_score );
					f_score.put( g_score.get( y ) + h_score.get( y ), y );
				}
			}
		}
 
		assert false : "Destination could not be reached";
		return trajectory;
	}
 
	protected abstract float calculateHeuristic( float distance );
 
	protected abstract float calculateCostOff( Road direction );
 
	private List<Crossroads> reconstructPath( final HashMap<Crossroads,Crossroads> cameFrom, final Crossroads end) {
		if( cameFrom.containsKey( end ) ) {
			final List<Crossroads> path = this.reconstructPath( cameFrom, cameFrom.get( end ) );
			path.add( end );
			return path;
		}
 
		return new LinkedList<Crossroads>();
	}
 
	public static class DistanceBasedAStar extends AStarRouter {
 
		@Override
		protected float calculateCostOff( final Road direction ) {
			return direction.getLength();
		}
 
		@Override
		protected float calculateHeuristic(final float distance) {
			return distance;
		}
	}
}