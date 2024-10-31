package goedegep.ov2.decode;

import goedegep.ov2.Ov2Item;

/**
 * 
 * @author xseillier
 *
 */

public interface Ov2DecodeItem {

	Ov2Item decodeItem( byte[] rawItem);	
	Ov2DecodeItem getNext();
	void setNext( Ov2DecodeItem nextItem);
	Ov2Item decode(  byte[] rawItem );
}
