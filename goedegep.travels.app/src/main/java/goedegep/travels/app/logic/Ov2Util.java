package goedegep.travels.app.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.ov2.Ov2FileEncoderDecoder;
import goedegep.ov2.Ov2Item;
import goedegep.ov2.Ov2ItemType2;
import goedegep.poi.app.LocationCategory;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsPackage;

public class Ov2Util {
  private static final Logger LOGGER = Logger.getLogger(Ov2Util.class.getName());

  public static void createOv2File(EObject startEObject, String fileName) {
    LOGGER.severe("=> startEObject=" + startEObject.toString() + ", fileName=" + fileName);
    
    List<Ov2Item> ov2Items = new ArrayList<>();
    
    // write the locations
    TreeIterator<EObject> vacationIterator = startEObject.eAllContents();
    
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      switch(eObject.eClass().getClassifierID()) {
        
      // Only locations are written as waypoints
      case VacationsPackage.LOCATION:
        Location location = (Location) eObject;
        if (location.isSetLatitude()  &&  location.isSetLongitude()) {
          Ov2ItemType2 ov2ItemType2 = new Ov2ItemType2();
          ov2ItemType2.setLatitude(location.getLatitude());
          ov2ItemType2.setLongitude(location.getLongitude());
          
          if (location.isSetName()) {
            ov2ItemType2.setDescription(location.getName());
          } else if (location.getLocationCategory() == LocationCategory.CITY  &&  location.isSetCity()) {
            ov2ItemType2.setDescription(location.getCity());
          }
          
          ov2Items.add(ov2ItemType2);
        }
        
        break;
        
      }
      
    }
    
    try {
      Ov2FileEncoderDecoder.encodeToFile(ov2Items, fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    LOGGER.severe("<= ");
  }

}
