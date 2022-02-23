package goedegep.ov2.decode;

import java.nio.ByteBuffer;

import goedegep.ov2.Ov2Item;
import goedegep.ov2.Ov2ItemType2;

/**
 * 
 *
 */

public class Ov2DecodeItemType2 extends AbstractOv2DecodeItem {

	public Ov2DecodeItemType2() {
		super( 2 );
	}

	@Override
	public Ov2Item decodeItem(byte[] rawItem) {
		
		Ov2ItemType2 response = new Ov2ItemType2();
		ByteBuffer oByteBuffer =  ByteBuffer.wrap( rawItem );
		readType( oByteBuffer );
		readLength( oByteBuffer );	
		response.setLongitude( readCoordinate( oByteBuffer ) );
		response.setLatitude(  readCoordinate( oByteBuffer ) );
		response.setDescription(  readString( oByteBuffer ) );
		return response;
	}
}
