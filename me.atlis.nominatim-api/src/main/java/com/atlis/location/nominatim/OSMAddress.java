package com.atlis.location.nominatim;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 *
 * @author nf
 */
public class OSMAddress {
  private String country;
  private String state;
  private String region;
  private String city;
  private String village;
  private String town;
  private String municipality;
  private String house_number;
  
  private String highway;
  private String road;
  private String aeroway;
  private String pedestrian;
  private String neighbourhood;
  private String suburb;
  private String postcode;
  private String county;
  private String country_code;
  private String housename;
  private String amenity;
  
  private String aerodrome;
  private String memorial;
  private String monument;
  private String attraction;
  private String castle;
  private String museum;
  private String hospital;
  private String theme_park;
  private String tourism;

  public String getCountry() {
      return country;
  }

  public void setCountry(String country) {
      this.country = country;
  }

  public String getState() {
      return state;
  }

  public void setState(String state) {
      this.state = state;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getCity() {
      return city;
  }

  public void setCity(String city) {
      this.city = city;
  }
  
  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public String getHousenumber() {
    return house_number;
  }

  public void setHousenumber(String housenumber) {
    house_number = housenumber;
  }

  @Override
  public String toString() {
      return ToStringBuilder.reflectionToString(this);
  }
  
  public String getHospital() {
    return hospital;
  }

  public void setHospital(String hospital) {
    this.hospital = hospital;
  }

  public String getMuseum() {
    return museum;
  }

  public void setMuseum(String museum) {
    this.museum = museum;
  }

  public String getCastle() {
    return castle;
  }

  public void setCastle(String castle) {
    this.castle = castle;
  }

  public String getPedestrian() {
    return pedestrian;
  }

  public void setPedestrian(String pedestrian) {
    this.pedestrian = pedestrian;
  }

  public String getAerodrome() {
    return aerodrome;
  }

  public void setAerodrome(String aerodrome) {
    this.aerodrome = aerodrome;
  }

  public String getMemorial() {
    return memorial;
  }

  public void setMemorial(String memorial) {
    this.memorial = memorial;
  }

  public String getMonument() {
    return monument;
  }

  public void setMonument(String monument) {
    this.monument = monument;
  }

  public String getAttraction() {
    return attraction;
  }

  public void setAttraction(String attraction) {
    this.attraction = attraction;
  }

  public String getHousename() {
      return housename;
  }

  public void setHousename(String housename) {
      this.housename = housename;
  }

  public String getAmenity() {
    return amenity;
  }

  public void setAmenity(String amenity) {
    this.amenity = amenity;
  }

  public String getCounty() {
      return county;
  }

  public void setCounty(String county) {
      this.county = county;
  }

  public String getSuburb() {
      return suburb;
  }

  public void setSuburb(String suburb) {
      this.suburb = suburb;
  }

  public String getHighway() {
    return highway;
  }

  public void setHighway(String highway) {
    this.highway = highway;
  }

  public String getRoad() {
      return road;
  }

  public void setRoad(String road) {
      this.road = road;
  }

  public String getAeroway() {
    return aeroway;
  }

  public void setAeroway(String aeroway) {
    this.aeroway = aeroway;
  }

  public String getPostcode() {
      return postcode;
  }

  public void setPostcode(String postcode) {
      this.postcode = postcode;
  }

  public String getCountry_code() {
      return country_code == null ? null : country_code.toUpperCase();
  }

  public void setCountry_code(String country_code) {
      this.country_code = country_code;
  }

  public String getNeighbourhood() {
      return neighbourhood;
  }

  public void setNeighbourhood(String neighbourhood) {
      this.neighbourhood = neighbourhood;
  }

  public String getTheme_park() {
    return theme_park;
  }

  public void setTheme_park(String theme_park) {
    this.theme_park = theme_park;
  }

  public String getTourism() {
    return tourism;
  }

  public void setTourism(String tourism) {
    this.tourism = tourism;
  }


}
