package goedegep.ov2.decode;

/**
 * 
 * @author xseillier
 *
 */

public class Ov2DecodeChain {

	
	private Ov2DecodeItem ov2DecodeItem;
	private static Ov2DecodeChain ov2DecodeChain = new Ov2DecodeChain();
	
	
	private Ov2DecodeChain()
	{
		ov2DecodeItem = new Ov2DecodeItemType0();		
		ov2DecodeItem.setNext( new Ov2DecodeItemType1() );
		ov2DecodeItem.setNext( new Ov2DecodeItemType2() );
		ov2DecodeItem.setNext( new Ov2DecodeItemType3() );
	}
	
	public static Ov2DecodeChain getInstance()
	{
		return ov2DecodeChain;
	}
	
	public Ov2DecodeItem getDecodeChain()
	{
		return ov2DecodeItem;
	}
}
