package goedegep.vacations.app.logic;

import goedegep.gpx.model.DocumentRoot;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationElement;

public record KmlPlacemarkImportData(VacationElement vacationElement, DocumentRoot gpxFileDocumentRoot, String placemarkAddress, Location nominatimLocation) {
  
}
