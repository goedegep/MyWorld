package goedegep.ov2;

/**
 * 
 * @author xseillier
 *
 * DELETED RECORD:
 * 1 byte T: type (always 0)
 * 4 bytes L: length of this record in bytes (including the T and L fields)
 * L−5 bytes bytes to ignore (content undefined)
*/
public class Ov2ItemType0 extends AbstractOv2Item {

		
	public Ov2ItemType0()
	{
		super( 0 );
	}

	@Override
	public String toString() {
		return "Ov2ItemType0 [type=" + type + "]";
	}

	
	@Override
	public String accept(Ov2ItemVisitor visitor ) {
		return visitor.visit( this );
	}
}
