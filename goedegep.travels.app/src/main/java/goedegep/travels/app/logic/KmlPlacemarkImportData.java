package goedegep.travels.app.logic;

import goedegep.gpx.model.DocumentRoot;
import goedegep.travels.model.Location;
import goedegep.travels.model.VacationElement;

public record KmlPlacemarkImportData(VacationElement vacationElement, DocumentRoot gpxFileDocumentRoot, String placemarkAddress, Location nominatimLocation) {
  
}
