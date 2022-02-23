package com.atlis.location.nominatim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class OSMLocationInfoAdapter extends TypeAdapter<OSMLocationInfo> {
  private static final Logger LOGGER = Logger.getLogger(OSMLocationInfoAdapter.class.getName());

  @Override
  public OSMLocationInfo read(JsonReader reader) throws IOException {
    LOGGER.info("=>");
    
    OSMLocationInfo osmLocationInfo = new OSMLocationInfo(); 
    reader.beginObject(); 
    
    while (reader.hasNext()) {
      String name = reader.nextName();
      LOGGER.info("name=" + name);
      switch (name) {
      case "nominatimResponse":
        osmLocationInfo.setNominatimResponse(reader.nextString());
        break;
        
      case "display_name":
        osmLocationInfo.setDisplay_name(reader.nextString());
        break;
        
      case "licence":
        osmLocationInfo.setLicence(reader.nextString());
        break;
        
      case "place_id":
        osmLocationInfo.setPlace_id(reader.nextString());
        break;
        
      case "place_rank":
        osmLocationInfo.setPlace_rank(reader.nextInt());
        break;
        
      case "osm_id":
        osmLocationInfo.setOsm_id(reader.nextString());
        break;
        
      case "osm_type":
        osmLocationInfo.setOsm_type(reader.nextString());
        break;
        
      case "lat":
        osmLocationInfo.setLat(reader.nextDouble());
        break;
        
      case "lon":
        osmLocationInfo.setLon(reader.nextDouble());
        break;
        
      case "boundingbox":
        List<Double> boundingbox = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
          boundingbox.add(reader.nextDouble());
        }
        osmLocationInfo.setBoundingbox(boundingbox.toArray(new Double[boundingbox.size()]));
        reader.endArray();
        break;
        
      case "category":
        osmLocationInfo.setCategory(reader.nextString());
        break;
        
      case "type":
        osmLocationInfo.setType(reader.nextString());
        break;
        
      case "name":
        JsonToken nextToken = reader.peek();
        if (nextToken == JsonToken.NULL) {
          reader.nextNull();
        } else {
          osmLocationInfo.setName(reader.nextString());
        }
        break;
        
      case "importance":
        osmLocationInfo.setImportance(reader.nextDouble());
        break;
        
      case "icon":
        osmLocationInfo.setIcon(reader.nextString());
        break;
        
      case "address":
        OSMAddress address = readAddress(reader);
        osmLocationInfo.setAddress(address);
        break;
        
      case "addresstype":
        osmLocationInfo.setAddresstype(reader.nextString());
        break;
        
      case "geokml":
        osmLocationInfo.setGeokml(reader.nextString());
        break;
        
//      case "kml":
//        Kml kml = readKml(reader);
//        osmLocationInfo.setKml(kml);
//        break;
        
      default:
        String string = reader.nextString();
        LOGGER.severe("Unknown name: " + name  + ":" + string);
      }
    }
    
    reader.endObject(); 
    
    LOGGER.info("<=");
    return osmLocationInfo; 
  }
  
  private OSMAddress readAddress(JsonReader reader) throws IOException {
    LOGGER.info("=>");
    
    OSMAddress address = new OSMAddress();
    reader.beginObject(); 
    
    while (reader.hasNext()) {
      String name = reader.nextName();
      LOGGER.info("name=" + name);
      switch (name) {
      case "country":
        address.setCountry(reader.nextString());
        break;
        
      case "state":
        address.setState(reader.nextString());
        break;
        
      case "region":
        address.setRegion(reader.nextString());
        break;
        
      case "city":
        address.setCity(reader.nextString());
        break;
        
      case "village":
        address.setVillage(reader.nextString());
        break;
        
      case "town":
        address.setTown(reader.nextString());
        break;
        
      case "municipality":
        address.setMunicipality(reader.nextString());
        break;
        
      case "house_number":
        address.setHousenumber(reader.nextString());
        break;
        
      case "highway":
        address.setHighway(reader.nextString());
        break;
                
      case "road":
        address.setRoad(reader.nextString());
        break;
        
      case "aeroway":
        address.setAeroway(reader.nextString());
        break;
        
      case "pedestrian":
        address.setPedestrian(reader.nextString());
        break;
        
      case "neighbourhood":
        address.setNeighbourhood(reader.nextString());
        break;
        
      case "suburb":
        address.setSuburb(reader.nextString());
        break;
        
      case "postcode":
        address.setPostcode(reader.nextString());
        break;
        
      case "county":
        address.setCounty(reader.nextString());
        break;
        
      case "country_code":
        address.setCountry_code(reader.nextString());
        break;
        
      case "housename":
        address.setHousename(reader.nextString());
        break;
        
      case "amenity":
        address.setAmenity(reader.nextString());
        break;
        
      case "aerodrome":
        address.setAerodrome(reader.nextString());
        break;
        
      case "memorial":
        address.setMemorial(reader.nextString());
        break;
        
      case "monument":
        address.setMonument(reader.nextString());
        break;
        
      case "attraction":
        address.setAttraction(reader.nextString());
        break;
        
      case "castle":
        address.setCastle(reader.nextString());
        break;
        
      case "museum":
        address.setMuseum(reader.nextString());
        break;
        
      case "hospital":
        address.setHospital(reader.nextString());
        break;
        
      case "theme_park":
        address.setTheme_park(reader.nextString());
        break;
        
      case "tourism":
        address.setTourism(reader.nextString());
        break;
        
      default:
        String string = reader.nextString();
        LOGGER.severe("Unknown name: " + name  + ":" + string);
      }
    }
    
    reader.endObject(); 
    
    LOGGER.info("<=");
    return address;
  }
  
//  private Kml readKml(JsonReader reader) throws IOException {
//    LOGGER.severe("=>");
//    
//    Kml kml = new Kml();
//    reader.beginObject(); 
//    
//    while (reader.hasNext()) {
//      String name = reader.nextName();
//      switch (name) {
////      case "x":
////        LOGGER.severe("x");
////        kml.setCountry(reader.nextString());
////        break;
//        
//      default:
//        String string = reader.nextString();
//        LOGGER.severe("Unknown: name" + name  + ":" + string);
//      }
//    }
//    
//    reader.endObject(); 
//    
//    LOGGER.severe("<=");
//    return kml;
//  }

  @Override
  public void write(JsonWriter writer, OSMLocationInfo osmLocationInfo) throws IOException {
    writer.beginObject(); 
    writer.name("nominatimResponse"); 
    writer.value(osmLocationInfo.getNominatimResponse()); 
    writer.name("rollNo"); 
    writer.value(osmLocationInfo.getLat()); 
    writer.endObject(); 
  }

}
