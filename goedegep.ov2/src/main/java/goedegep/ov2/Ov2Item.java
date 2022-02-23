package goedegep.ov2;

/**
 * 
 *
 */

public interface Ov2Item {

	public int getType();
	public String accept( Ov2ItemVisitor visitor );
}
