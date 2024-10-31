package goedegep.ov2;

public interface Ov2ItemVisitor {

	String visit( Ov2ItemType0 ov2Item );
	String visit( Ov2ItemType1 ov2Item );
	String visit( Ov2ItemType2 ov2Item );
	String visit( Ov2ItemType3 ov2Item );
	
}
