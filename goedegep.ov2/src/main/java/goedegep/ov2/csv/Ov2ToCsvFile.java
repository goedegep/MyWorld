package goedegep.ov2.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;

public class Ov2ToCsvFile {

	
	private BufferedWriter bufferedWriter ;
	public static String SEPARATOR =",";
	private String ColummType2 = "latitude" + SEPARATOR + "longitude" + SEPARATOR + "description";
	private String ColummType3 = "latitude" + SEPARATOR + "longitude" + SEPARATOR + "description" + SEPARATOR + "id";
	
	public Ov2ToCsvFile( String filePath, Ov2ToCsvType type ) throws IOException
	{
		File file = new File( filePath );
		if( file.exists() )
		{
			throw new FileAlreadyExistsException( filePath );
		}
		else
		{
			bufferedWriter = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file )  ) );
			if( type == Ov2ToCsvType.TWO )
			{
				writeLine( ColummType2 );
			}
			else
			{
				writeLine( ColummType3 );
			}
		}
	}
	
	public void writeLine( String line ) throws IOException
	{
		bufferedWriter.write( line );
		bufferedWriter.newLine();
	}
	
	/**
	 * close file
	 */
	public void close()
	{
		if( bufferedWriter != null )
		{
			try {
				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
			}
		}
	}
}
