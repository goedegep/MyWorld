package goedegep.resources;

/**
 * This record provides information on a single image file.
 * 
 * @param filename Filename of the image (without any directory path)
 * @param width the width of the image
 * @param height the height of the image
 */
public record ImageFileInfo(String filename, int width, int height) {

}