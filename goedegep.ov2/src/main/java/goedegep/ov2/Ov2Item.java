package goedegep.ov2;

/**
 * 
 *
 */

public interface Ov2Item {

	int getType();
	String accept( Ov2ItemVisitor visitor );
}
