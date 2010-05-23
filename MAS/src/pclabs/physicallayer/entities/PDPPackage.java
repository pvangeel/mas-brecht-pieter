package pclabs.physicallayer.entities;

public class PDPPackage {

	private Crossroads origin;
	private Crossroads destination;
	private double weight;
	private int id;

	public PDPPackage(int id, Crossroads origin, Crossroads destination, double weigth) {
		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.weight = weigth;
	}

	public Crossroads getDestination() {
		return destination;
	}
	
	public Crossroads getOrigin() {
		return origin;
	}
	

	/**
	 * Creates a DTO representation of this PDPPackage
	 * @return
	 */
	public PDPPackageDTO getDTO() {
		return new PDPPackageDTO(origin, destination, weight);
	}

	public int getId() {
		return id;
	}

}
