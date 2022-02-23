package com.atlis.location.nominatim;

import de.micromata.opengis.kml.v_2_2_0.Kml;

/**
 *
 * @author nf
 */
public class OSMLocationInfo {

    private String nominatimResponse; // Full response to the query.
    private String display_name;  // Free text representation of the location
    private String licence;       // The license which applies to the data
    private String place_id;      // An internal database id, don't use it
    private Integer place_rank;
    private String osm_id;        // A 'unique' id on a single instance of the OSM database
    private String osm_type;      // E.g.: 'node', 'way'
    private Double lat;           // Coordinate: latitude
    private Double lon;           // Coordinate: longitude
    private Double[] boundingbox; // A bounding box around the location
    private String category;      // E.g. (city) boundary - currently not used, as the name class isn't possible
    private String type;          // E.g.: 'administrative', 'house'
    private String name;          // Name of the location.
    private Double importance;    // a floating point number
    private String icon;          // an icon url
    
    private String addresstype;   // the kind of address, e.g. road
    private OSMAddress address;
    private String geokml;
    private Kml kml;
    
    
    public String getNominatimResponse() {
      return nominatimResponse;
    }

    public void setNominatimResponse(String nominatimResponse) {
      this.nominatimResponse = nominatimResponse;
    }

    private String error;

    public String getIcon() {
      return icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    
    public Double[] getBoundingbox() {
      return boundingbox;
    }

    public void setBoundingbox(Double[] boundingbox) {
      this.boundingbox = boundingbox;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Integer getPlace_rank() {
      return place_rank;
    }

    public void setPlace_rank(Integer place_rank) {
      this.place_rank = place_rank;
    }

    public String getAddresstype() {
      return addresstype;
    }

    public void setAddresstype(String addresstype) {
      this.addresstype = addresstype;
    }

    public OSMAddress getAddress() {
        return address;
    }

    public void setAddress(OSMAddress address) {
        this.address = address;
    }

    public String getOsm_id() {
        return osm_id;
    }

    public void setOsm_id(String osm_id) {
        this.osm_id = osm_id;
    }

    public String getOsm_type() {
        return osm_type;
    }

    public void setOsm_type(String osm_type) {
        this.osm_type = osm_type;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Double getImportance() {
      return importance;
    }

    public void setImportance(Double importance) {
      this.importance = importance;
    }
    
    public String getGeokml() {
      return geokml;
    }

    public void setGeokml(String geokml) {
      this.geokml = geokml;
    }

    public Kml getKml() {
      return kml;
    }

    public void setKml(Kml kml) {
      this.kml = kml;
    }

    @Override
    public String toString() {
        return "[display_name = " + display_name + ", licence = " + licence + ", place_id = " + place_id + ", lon = " + lon + ", address = " + address + ", osm_id = " + osm_id + ", osm_type = " + osm_type + ", lat = " + lat + ", boundingbox = [" + boundingbox[0] + "," + boundingbox[1] + "," + boundingbox[2] + "," + boundingbox[3] + "]]";
    }

    /**
     * Response example:
     *
     * {
     * "place_id":"63949160", "licence":"Data © OpenStreetMap contributors, ODbL
     * 1.0. http:\/\/www.openstreetmap.org\/copyright", "osm_type":"way",
     * "osm_id":"34958053", "lat":"40.7903088", "lon":"-73.9599513",
     * "display_name":"97th Street Transverse, Park West Village, Manhattan, New
     * York County, New York City, New York, 10029, United States of America",
     * "address":{ "road":"97th Street Transverse", "neighbourhood":"Park West
     * Village", "suburb":"Manhattan", "county":"New York County", "city":"New
     * York City", "state":"New York", "postcode":"10029", "country":"United
     * States of America", "country_code":"us" } }
     */
}
