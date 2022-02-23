package goedegep.ov2;

/**
 * 
 * @author xseillier
 * 
 * EXTENDED POI RECORD:
 * 1 byte T: type (always 3)
 * 4 bytes L: length of this record in bytes (including the T and L fields)
 * 4 bytes X: longitude coordinate of the POI
 * 4 bytes Y: latitude coordinate of the POI
 * P bytes Name: zero−terminated ASCII string specifying the name
 * of the POI
 * Q bytes Unique ID: zero−terminated string specifying the unique ID
 * of the POI
 * L−P−Q−13 bytes Extra data: zero−terminated string, not used yet
 */

public class Ov2ItemType3 extends Ov2ItemType2 {

	
	private String id;
	
	public Ov2ItemType3()
	{
		super( 3 );
	}

	public String getId() {
		return id;
	}

	public void setId(String aId) {
		id = aId;
	}

	
	@Override
	public String accept(Ov2ItemVisitor visitor) {
		return visitor.visit( this );
	}

	
	@Override
	public String toString() {
		return "Ov2ItemType3 [getLongitude()=" + getLongitude()
				+ ", getLatitude()=" + getLatitude() + ", getDescription()="
				+ getDescription() + ", getType()=" + getType() + "]";
	}
}
