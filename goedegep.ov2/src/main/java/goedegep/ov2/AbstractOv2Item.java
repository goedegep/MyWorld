package goedegep.ov2;


/**
 * 
 * @author xseillier
 *
 */

public abstract class AbstractOv2Item implements Ov2Item {

	protected int type;
		
	protected AbstractOv2Item( int type )
	{
		this.type = type;
	}
	
	@Override
	public int getType() {
		return type;
	}

}
