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

import java.io.IOException;

import goedegep.util.mslinks.data.FileAttributesFlags;
import goedegep.util.mslinks.data.Filetime;
import goedegep.util.mslinks.data.GUID;
import goedegep.util.mslinks.data.HotKeyFlags;
import goedegep.util.mslinks.data.LinkFlags;
import goedegep.util.mslinks.io.ByteReader;
import goedegep.util.mslinks.io.ByteWriter;

public class ShellLinkHeader implements Serializable {
  private static final String         NEWLINE = System.getProperty("line.separator");

	private static int headerSize = 0x0000004C;
	private static GUID clsid = new GUID("00021401-0000-0000-C000-000000000046");
	
	public static final int SW_SHOWNORMAL = 1;
	public static final int SW_SHOWMAXIMIZED = 3;
	public static final int SW_SHOWMINNOACTIVE = 7;	
	
	private LinkFlags lf;
	private FileAttributesFlags faf;
	private Filetime creationTime, accessTime, writeTime;
	private int fileSize, iconIndex, showCommand;
	private HotKeyFlags hkf;
	
	public ShellLinkHeader() {
		lf = new LinkFlags(0);
		faf = new FileAttributesFlags(0);
		creationTime = new Filetime();
		accessTime = new Filetime();
		writeTime = new Filetime();
		showCommand = SW_SHOWNORMAL;
		hkf = new HotKeyFlags();
	}
	
	public ShellLinkHeader(ByteReader data) throws ShellLinkException, IOException {
		int size = (int)data.read4bytes();
		if (size != headerSize)
			throw new ShellLinkException();		
		GUID g = new GUID(data);
		if (!g.equals(clsid))
			throw new ShellLinkException();
		lf = new LinkFlags(data);
		faf = new FileAttributesFlags(data);
		creationTime = new Filetime(data);
		accessTime = new Filetime(data);
		writeTime = new Filetime(data);
		fileSize = (int)data.read4bytes();
		iconIndex = (int)data.read4bytes();
		showCommand = (int)data.read4bytes();
		if (showCommand != SW_SHOWNORMAL && showCommand != SW_SHOWMAXIMIZED && showCommand != SW_SHOWMINNOACTIVE)
			throw new ShellLinkException();
		hkf = new HotKeyFlags(data);
		data.read2bytes();
		data.read8bytes();
	}
	
	public LinkFlags getLinkFlags() { return lf; }
	public FileAttributesFlags getFileAttributesFlags() { return faf; }
	public Filetime getCreationTime() { return creationTime; }
	public Filetime getAccessTime() { return accessTime; }
	public Filetime getWriteTime() { return writeTime; }
	public HotKeyFlags getHotKeyFlags() { return hkf; }
	
	public int getFileSize() { return fileSize; }
	public ShellLinkHeader setFileSize(long n) { fileSize = (int)n; return this; }
	
	public int getIconIndex() { return iconIndex; }
	public ShellLinkHeader setIconIndex(int n) { iconIndex = n; return this; }
	
	public int getShowCommand() { return showCommand; }
	public ShellLinkHeader setShowCommand(int n) throws ShellLinkException { 
		if (n == SW_SHOWNORMAL || n == SW_SHOWMAXIMIZED || n == SW_SHOWMINNOACTIVE) {
			showCommand = n;
			return this; 
		} else 
			throw new ShellLinkException();
	}

	public void serialize(ByteWriter bw) throws IOException {
		bw.write4bytes(headerSize);
		clsid.serialize(bw);
		lf.serialize(bw);
		faf.serialize(bw);
		creationTime.serialize(bw);
		accessTime.serialize(bw);
		writeTime.serialize(bw);
		bw.write4bytes(fileSize);
		bw.write4bytes(iconIndex);
		bw.write4bytes(showCommand);
		hkf.serialize(bw);
		bw.write2bytes(0);
		bw.write8bytes(0);
	}
    
    public String toString() {
      StringBuilder buf = new StringBuilder();
      
      buf.append("ShellLinkHeader:").append(NEWLINE);
      
      buf.append("  HeaderSize: ").append(String.format("0x%8s", Integer.toHexString(headerSize)).replace(' ', '0')).append(NEWLINE);
      buf.append("  LinkCLSID: ").append(clsid.toString()).append(NEWLINE);
      buf.append(lf.toString());
      buf.append(faf.toString());
      buf.append("  CreationTime: ").append(creationTime.toString()).append(NEWLINE);
      buf.append("  AccessTime: ").append(accessTime.toString()).append(NEWLINE);
      buf.append("  WriteTime: ").append(writeTime.toString()).append(NEWLINE);
      buf.append("  FileSize: ").append(String.format("0x%8s", Integer.toHexString(fileSize)).replace(' ', '0')).append(NEWLINE);
      buf.append("  IconIndex: ").append(String.format("0x%8s", Integer.toHexString(iconIndex)).replace(' ', '0')).append(NEWLINE);
      buf.append("  ShowCommand: ").append(String.format("0x%8s", Integer.toHexString(showCommand)).replace(' ', '0')).append(NEWLINE);
      buf.append("  HotKey: key=\"").append(hkf.getKey()).append("\", isAlt=");
      buf.append(hkf.isAlt()).append(", isCtrl=").append(hkf.isCtrl()).append(", isShift=").append(hkf.isShift());

      
      return buf.toString();
    }
}
