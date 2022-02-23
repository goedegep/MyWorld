package goedegep.ov2.decode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import goedegep.ov2.Ov2Item;

/**
 * 
 *
 */
public abstract class AbstractOv2DecodeItem implements Ov2DecodeItem {

	
	private Ov2DecodeItem next = null;

	private int type;
	
	public AbstractOv2DecodeItem( int type )
	{
		this.type = type;
	}
	
	
	public Ov2Item decode(  byte[] rawItem )
	{
		if(rawItem[ 0 ] == type )
		{			
			return this.decodeItem( rawItem );
		}
		else
		{
			if( getNext() != null )
			{
				return getNext().decode( rawItem );
			}
			else
			{
				throw new UnknownOv2TypeException( "The type " + rawItem[ 0 ] + " is unknown");
			}
		}
	}
	
	@Override
	public Ov2DecodeItem getNext() {
		return next;
	}

	@Override
	public void setNext(Ov2DecodeItem nextItem) {
		
		if( next == null )
		{
			next = nextItem;
		}
		else
		{
			next.setNext( nextItem );
		}
	}

	/**
	 * 
	 * @param byteBuffer
	 * @return
	 */
	protected byte readType( ByteBuffer byteBuffer )
	{
		return byteBuffer.get();
	}
	
	/**
	 * 
	 * @param byteBuffer
	 * @return
	 */
	protected int readLength( ByteBuffer byteBuffer )
	{
		return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	
	protected double readCoordinate( ByteBuffer byteBuffer )
	{
		return ( (double) byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt() ) / 100000;
	}
	
	
	protected String readString( ByteBuffer byteBuffer )
	{
		byte character;
		
		StringBuffer response = new StringBuffer();
		//return ( byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt() ) / 100000;
		while( ( character = byteBuffer.get() ) != 0x00 )
		{
			response.append( (char) character );
		}
		return response.toString();
	}
}
