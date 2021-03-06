/*
	https://github.com/BlackOverlord666/mslinks
	
	Copyright (c) 2015 Dmitrii Shamrikov

	Licensed under the WTFPL
	You may obtain a copy of the License at
 
	http://www.wtfpl.net/about/
 
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*/
package goedegep.util.mslinks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import goedegep.util.mslinks.data.ItemID;
import goedegep.util.mslinks.io.ByteReader;
import goedegep.util.mslinks.io.ByteWriter;

@SuppressWarnings("serial")
public class LinkTargetIDList extends LinkedList<ItemID> implements Serializable {
	
	public LinkTargetIDList() {}
	
	public LinkTargetIDList(ByteReader data) throws IOException, ShellLinkException {
		int size = (int)data.read2bytes();
		
		int pos = data.getPosition(); 
		
		boolean binary = false;
		int s = (int)data.read2bytes();
		while (s != 0) {
			s -= 2;
			if (binary) {
				byte[] b = new byte[s];
				for (int i=0; i<s; i++)
					b[i] = (byte)data.read();
				add(new ItemID(b));
			} else try {
				add(new ItemID(data, s));
			} catch (UnsupportedCLSIDException e) {
				System.err.println("unsupported CLSID");
				binary = true;
			}
			s = (int)data.read2bytes();
		}
		
		pos = data.getPosition() - pos;
		if (pos != size) 
			throw new ShellLinkException();
	}

	public void serialize(ByteWriter bw) throws IOException {
		int size = 2;
		byte[][] b = new byte[size()][];
		int i = 0;
		for (ItemID j : this) {
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			ByteWriter w = new ByteWriter(ba);
			
			j.serialize(w);			
			b[i++] = ba.toByteArray();					
		}			
		for (byte[] j : b)
			size += j.length + 2;
		
		bw.write2bytes(size);
		for (byte[] j : b) {
			bw.write2bytes(j.length + 2);
			bw.writeBytes(j);
		}
		bw.write2bytes(0);
	}
	
	public boolean isCorrect() {
		for (ItemID i : this) 
			if (i.getType() == ItemID.TYPE_UNKNOWN)
				return false;
		return true;
	}
    
    public String toString() {
      StringBuilder buf = new StringBuilder();
      
      for (ItemID itemId: this) {
        if (itemId == null) {
          buf.append("  ItemID: null");
        } else {
          buf.append(itemId.toString());
        }
      }
      
      return buf.toString();
    }
}
