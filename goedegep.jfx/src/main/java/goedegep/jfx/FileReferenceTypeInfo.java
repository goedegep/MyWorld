package goedegep.jfx;

/**
 * Via a list of these records you can specify the types of file references to be handled by
 * a {@code FileReferencePanel}.<br/>
 * 
 * @param tag A string to refer to this type in your code
 * @param displayName The text shown in the GUI.
 * @param isFolder To distinguish between file and folder references. Used to show a file or folder selecter.
 * @param filePathPrefix The path prefix; the first part of the file or folder path.
 */
public record FileReferenceTypeInfo (String tag, String displayName, boolean isFolder, String filePathPrefix) {
}
