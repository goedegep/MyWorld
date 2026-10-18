package goedegep.travels.logic;

import goedegep.gpx.model.DocumentRoot;
import goedegep.travels.model.Location;
import goedegep.travels.model.VacationElement;

/**
 * This record is used to store the data needed to import a KML placemark into a vacation element.
 * 
 * @param vacationElement The vacation element (either a {@code Location} or a {@code GPXTrack} derived from the placemark.
 * @param gpxFileDocumentRoot The GPX file document root, which can be used to create a GPX file.
 *        This value is only used if the {@code vacationElement} is a {@code GPXTrack}.
 * @param placemarkAddress The placemark address, from which the user can copy/past information.
 * @param nominatimLocation The Nominatim location, from which the user can copy/past information.
 *        This value is only set if a Nominatim API was provided, and information could be obtained via this API.
 */
public record KmlPlacemarkImportData(VacationElement vacationElement, DocumentRoot gpxFileDocumentRoot, String placemarkAddress, Location nominatimLocation) {
  
}
