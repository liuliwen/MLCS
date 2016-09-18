package mlcs;

import java.util.Arrays;
import java.util.List;

public class PointNode {
	int point[];
	int ID;
	int tleve;
	List<PointNode> precursor;

	public int getTleve() {
		return tleve;
	}

	public void setTleve(int tleve) {
		this.tleve = tleve;
	}

	public List<PointNode> getPrecursor() {
		return precursor;
	}

	public void setPrecursor(List<PointNode> precursor) {
		this.precursor = precursor;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public PointNode(int[] point) {
		super();
		this.point = point;
	}

	public PointNode() {
		super();
	}

	public int[] getPoint() {
		return point;
	}

	public void setPoint(int[] point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "point=" + Arrays.toString(point) + ", ID=" + ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(point);
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
		PointNode other = (PointNode) obj;
		if (!Arrays.equals(point, other.point))
			return false;
		return true;
	}
	
	
}
