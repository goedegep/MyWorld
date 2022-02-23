package goedegep.ov2.decode;

import java.nio.ByteBuffer;

import goedegep.ov2.Ov2Item;
import goedegep.ov2.Ov2ItemType3;

//import com.xseillier.ov2.item.Ov2Item;
//import com.xseillier.ov2.item.Ov2ItemType3;

/**
 * 
 * @author xseillier
 *
 */

public class Ov2DecodeItemType3 extends AbstractOv2DecodeItem {

	public Ov2DecodeItemType3() {
		super( 3 );
	}

	@Override
	public Ov2Item decodeItem(byte[] rawItem) {
		
		Ov2ItemType3 response = new Ov2ItemType3();
		ByteBuffer oByteBuffer =  ByteBuffer.wrap( rawItem );
		readType( oByteBuffer );
		readLength( oByteBuffer );	
		response.setLongitude( readCoordinate( oByteBuffer ) );
		response.setLatitude(  readCoordinate( oByteBuffer ) );
		response.setDescription(  readString( oByteBuffer ) );
		response.setId( readString( oByteBuffer ) );
		return response;
	}
}
