package layer.physical.entities;

public class PDPPackageDTO {

	private Crossroads origin;
	private Crossroads destination;
	private double weight;
	private long radius;
	private PDPPackage pdpPackage;

	public PDPPackageDTO(Crossroads origin, Crossroads destination, double weight, PDPPackage p) {

		this.weight = weight;
		this.origin = origin;
		this.destination = destination;
		this.pdpPackage = p;
	}

	public Crossroads getOrigin() {
		return origin;
	}

	public Crossroads getDestination() {
		return destination;
	}

	public double getWeight() {
		return weight;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDPPackageDTO other = (PDPPackageDTO) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (Double.doubleToLongBits(weight) != Double
				.doubleToLongBits(other.weight))
			return false;
		return true;
	}



}
