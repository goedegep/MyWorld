package goedegep.ov2;

public interface Ov2ItemVisitor {

	public String visit( Ov2ItemType0 ov2Item );
	public String visit( Ov2ItemType1 ov2Item );
	public String visit( Ov2ItemType2 ov2Item );
	public String visit( Ov2ItemType3 ov2Item );
	
}
