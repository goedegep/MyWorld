package goedegep.ov2;

/**
 * 
 * @author xseillier
 *
 * SKIPPER RECORD:
 * 1 byte T: type (always 1)
 * 4 bytes Number of bytes in the file, including and starting at this
 * record, that contain data for POI enclosed in the given
 * rectangle
 * 4 bytes X1: longitude coordinate of the west edge of the rectangle
 * 4 bytes Y1: latitude coordinate of the south edge of the rectangle
 * 4 bytes X2: longitude coordinate of the east edge of the rectangle
 * 4 bytes Y2: latitude coordinate of the north edge of the rectangle
 */
public class Ov2ItemType1 extends AbstractOv2Item {

	
	private double x1;
	private double x2;
	
	private double y1;
	private double y2;
	
		
	public Ov2ItemType1()
	{
		super( 1 );
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double aX1) {
		x1 = aX1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double aX2) {
		x2 = aX2;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double aY1) {
		y1 = aY1;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double aY2) {
		y2 = aY2;
	}

	
	@Override
	public String accept(Ov2ItemVisitor visitor) {
		return visitor.visit( this );
	}
	
	@Override
	public String toString() {
		return "Ov2ItemType1 [x1=" + x1 + ", x2=" + x2 + ", y1=" + y1 + ", y2="
				+ y2 + ", type=" + type + "]";
	}

}
