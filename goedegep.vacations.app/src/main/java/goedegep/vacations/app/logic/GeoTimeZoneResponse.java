package goedegep.vacations.app.logic;

public class GeoTimeZoneResponse {
  private static final String NEWLINE = System.getProperty("line.separator");

  private double longitude;
  private double latitude;
  private String location;
  private String country_iso;
  private String iana_timezone;
  private String timezone_abbreviation;
  private String dst_abbreviation;
  private String offset;
  private String dst_offset;
  private String current_local_datetime;
  private String current_utc_datetime;
  
  public double getLongitude() {
    return longitude;
  }
  
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
  
  public double getLatitude() {
    return latitude;
  }
  
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public String getCountry_iso() {
    return country_iso;
  }
  
  public void setCountry_iso(String country_iso) {
    this.country_iso = country_iso;
  }
  
  public String getIana_timezone() {
    return iana_timezone;
  }
  
  public void setIana_timezone(String iana_timezone) {
    this.iana_timezone = iana_timezone;
  }
  
  public String getTimezone_abbreviation() {
    return timezone_abbreviation;
  }
  
  public void setTimezone_abbreviation(String timezone_abbreviation) {
    this.timezone_abbreviation = timezone_abbreviation;
  }
  
  public String getDst_abbreviation() {
    return dst_abbreviation;
  }
  
  public void setDst_abbreviation(String dst_abbreviation) {
    this.dst_abbreviation = dst_abbreviation;
  }
  
  public String getOffset() {
    return offset;
  }
  
  public void setOffset(String offset) {
    this.offset = offset;
  }
  
  public String getDst_offset() {
    return dst_offset;
  }
  
  public void setDst_offset(String dst_offset) {
    this.dst_offset = dst_offset;
  }
  
  public String getCurrent_local_datetime() {
    return current_local_datetime;
  }
  
  public void setCurrent_local_datetime(String current_local_datetime) {
    this.current_local_datetime = current_local_datetime;
  }
  
  public String getCurrent_utc_datetime() {
    return current_utc_datetime;
  }
  
  public void setCurrent_utc_datetime(String current_utc_datetime) {
    this.current_utc_datetime = current_utc_datetime;
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(NEWLINE);
    buf.append("latitude, longitude: ").append(latitude).append(", ").append(longitude).append(NEWLINE);
    buf.append("location, country_iso: ").append(location).append(", ").append(country_iso).append(NEWLINE);
    buf.append("iana_timezone, timezone_abbreviation: ").append(iana_timezone).append(", ").append(timezone_abbreviation).append(NEWLINE);
    buf.append("dst_abbreviation: ").append(dst_abbreviation).append(NEWLINE);
    buf.append("offset, dst_offset: ").append(offset).append(", ").append(dst_offset).append(NEWLINE);
    buf.append("current_local_datetime, current_utc_datetime: ").append(current_local_datetime).append(", ").append(current_utc_datetime).append(NEWLINE);
    
    return buf.toString();
  }
}
