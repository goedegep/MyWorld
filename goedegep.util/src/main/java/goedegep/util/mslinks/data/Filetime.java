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
package goedegep.util.mslinks.data;

import java.io.IOException;
import java.util.GregorianCalendar;

import goedegep.util.mslinks.Serializable;
import goedegep.util.mslinks.io.ByteReader;
import goedegep.util.mslinks.io.ByteWriter;

@SuppressWarnings("serial")
public class Filetime extends GregorianCalendar implements Serializable {
	private long residue;
	
	public Filetime() {
		super();
	}
	
	public Filetime(ByteReader data) throws IOException {
		this(data.read8bytes());
	}
	
	public Filetime(long time) {
		long t = time / 10000;
		residue = time - t;
		setTimeInMillis(t);
		add(YEAR, -369);
	}
	
	public long toLong() {
		GregorianCalendar tmp = (GregorianCalendar)clone();
		tmp.add(YEAR, 369);
		return tmp.getTimeInMillis() + residue;		
	}

	public void serialize(ByteWriter bw) throws IOException {
		bw.write8bytes(toLong());		
	}
	
	public String toString() {
		return String.format("%d:%d:%d %d.%d.%d", 
				get(HOUR_OF_DAY), get(MINUTE), get(SECOND),
				get(DAY_OF_MONTH), get(MONTH) + 1, get(YEAR));
	}
}
