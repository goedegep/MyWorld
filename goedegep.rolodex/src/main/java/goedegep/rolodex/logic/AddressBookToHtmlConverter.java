package goedegep.rolodex.logic;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.IOUtils;

import goedegep.jfx.CustomizationFx;
import goedegep.rolodex.gui.AdminResourcesFx;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;

/**
 * This class generates an HTML address book String from a {@code Rolodex}.
 * <p>
 * The address book is a table containing all (non archived) families, persons and institutions.<p>
 * For each entry the name, address and phone numbers are shown.
 */
public class AddressBookToHtmlConverter {
  /**
   * The {@code Rolodex} for which HTML is to be generated.
   */
  private Rolodex rolodex = null;
  
  /**
   * {@code URL} for the type image for a person.
   */
  private static URL personImageUrl;
  
  /**
   * {@code URL} for the type image for a family.
   */  
  private static URL familyImageUrl;
  
  /**
   * {@code URL} for the type image for an institution.
   */
  private static URL institutionImageUrl;

  /**
   * {@code StringBuilder} for building the HTML text.
   */
  private StringBuilder buf = new StringBuilder();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param rolodex The {@code Rolodex} for which HTML is to be generated.
   */
  public AddressBookToHtmlConverter(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    AdminResourcesFx rolodexResources = (AdminResourcesFx) customization.getResources();
    personImageUrl = rolodexResources.getPersonImageUrl();
    familyImageUrl = rolodexResources.getFamilyImageUrl();
    institutionImageUrl = rolodexResources.getInstitutionImageUrl();
  }
  
  /**
   * Generate an HTML address book String from a the {@code rolodex}.
   * 
   * @return an HTML address book String.
   */
  public String addressBookToHtml() {
    buf.setLength(0);
    
    String header = """
            <html>
              <head>
                <style>
                  table {
                    border-collapse: collapse;
                  }
                  
                  th, td {
                    border: 1px solid #100808;
                    padding: 4px; 
                  }
                </style>
              </head>
              <body>
            """;
    
    buf.append(header);
    
    buf.append("<h1>");
    buf.append("Adreslijst");
    buf.append("</h1>\n");
    
    buf.append("<table >");

    buf.append("<th>Type</th><th>Naam</th><th>Straat/Postbus</th><th>Postcode</th><th>Plaats</th><th>Land</th><th>Telefoonnummers</th>\n");
    
    for (Family family: rolodex.getFamilyList().getFamilies()) {
      if (!family.isArchived()) {
        familyToTableRow(family);
      }
    }
    
    for (Person person: rolodex.getPersonList().getPersons()) {
      if (!person.isArchived()) {
        personToTableRow(person);
      }
    }
    
    for (Institution institution: rolodex.getInstitutionList().getInstitutions()) {
      if (!institution.isArchived()) {
        institutionToTableRow(institution);
      }
    }
    
    buf.append("</table>\n");
    buf.append("</body>\n");
    buf.append("</html>\n");
    
//    System.out.println(buf.toString());   
    return buf.toString();
  }

  /**
   * Generate a table row for a {@code Family}
   * 
   * @param family the {@code Family} for which a table row is to be generated.
   */
  private void familyToTableRow(Family family) {
    buf.append("<tr>\n");
    
    // type
    buf.append("<td>");
    addImage(familyImageUrl);
    buf.append("</td>");
    buf.append("<td>");
    
    // name
    if (family.getFamilyTitle() != null) {
      buf.append(family.getFamilyTitle()).append(" ");
    }
    buf.append(family.getFamilyName()).append("</td>");
    
    // address
    addAddress(family.getAddress());
    
    // phone numbers
    addPhoneNumbers(family.getPhoneNumbers());
    
    buf.append("\n</tr>");
  }

  /**
   * Generate a table row for a {@code Person}
   * 
   * @param person the {@code Person} for which a table row is to be generated.
   */
  private void personToTableRow(Person person) {
    buf.append("<tr>\n");
    
    // type
    buf.append("<td>");
    addImage(personImageUrl);
    buf.append("</td>");
    
    // name
    buf.append("<td>").append(person.getName()).append("</td>");
    
    // address
    addAddress(person.getAddress());
    
    // phone numbers
    addPhoneNumbers(person.getPhoneNumbers());
    
    buf.append("\n</tr>");
  }


  /**
   * Generate a table row for an {@code Institution}
   * 
   * @param institution the {@code Institution} for which a table row is to be generated.
   */
  private void institutionToTableRow(Institution institution) {
    buf.append("<tr>\n");
    
    // type
    buf.append("  <td>");
    addImage(institutionImageUrl);
    buf.append("</td>");
    
    // name
    buf.append("<td>").append(institution.getName()).append("</td>");
    
    // address
    addAddress(institution.getAddress());
    
    // phone numbers
    addPhoneNumbers(institution.getPhoneNumbers());
    
    buf.append("\n</tr>\n");
  }
  
  /**
   * Generate the table cells for an {@code Address}.
   * 
   * @param address the {@code Address} for which table cells are to be generated.
   */
  private void addAddress(Address address) {
    buf.append("<td>");
    if (address != null) {
      if (address.getStreetName() != null) {
        buf.append(address.getStreetName());
        if (address.getHouseNumber() != null) {
          buf.append(" ").append(address.getHouseNumber());
          
          if (address.getHouseNumberExtension() != null) {
            buf.append(address.getHouseNumberExtension());
          }
        }

      } else if (address.getPOBox() != null) {
        buf.append(address.getPOBox());
      }
    }
    buf.append("</td>");
    buf.append("<td>");
    if (address != null  &&  address.getPostalCode() != null) {
      buf.append(address.getPostalCode());
    }
    buf.append("</td>");
    buf.append("<td>");
    if (address != null  &&  address.getCity() != null) {
      buf.append(address.getCity().getCityName());
    }
    buf.append("</td>");
    buf.append("<td>");
    if (address != null  &&  address.getCity() != null  &&  address.getCity().getCountry() != null) {
      buf.append(address.getCity().getCountry().getCountryName());
    }
    buf.append("</td>");
  }
  
  /**
   * Generate the table cell for a list of {@code PhoneNumber}s.
   * 
   * @param phoneNumbers the {@code PhoneNumber}s for which a table cell is to be generated.
   */
  private void addPhoneNumbers(List<PhoneNumber> phoneNumbers) {
    buf.append("<td>");
    boolean first = true;
    for (PhoneNumber phoneNumber: phoneNumbers) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      buf.append(phoneNumber.getPhoneNumber());
    }
    buf.append("</td>");
    
  }
  
  /**
   * Generate an embedded HTML image element.
   * 
   * @param url {@code URL} of the image to be added.
   */
  private void addImage(URL url) {
    buf.append("<img src=\"");
      buf.append("data:image/")
      .append(getImageType(url))
      .append(";base64,")
      .append(getBase64EncodedImage(url));
    buf.append("\" height=\"")
    .append(20)
    .append("\" >");
    buf.append("</img>");
  }

  /**
   * Generate a Base64 encoded String for an image.
   * 
   * @param url {@code URL} of the image to be encoded.
   * @return a Base64 encoded String for the image file identified by the {@code urlString}.
   */
  private Object getBase64EncodedImage(URL url) {
    try {
      byte[] fileContent = IOUtils.toByteArray(url.openStream());
      String encodedString = Base64.getEncoder().encodeToString(fileContent);
      return encodedString;
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  /**
   * Get the image type (jpg or png) of an image.
   * 
   * @param url {@code URL} of the image
   * @return "jpg", "png" or null.
   */
  private String getImageType(URL url) {
    String urlString = url.toString();
    
    if (urlString.endsWith(".jpg")) {
      return "jpg";
    } else if (urlString.endsWith(".png")) {
      return "png";
    } else {
      return null;
    }
  }
}
