package goedegep.rolodex.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

/**
 */
public class AdminResourcesFx extends AbstractAppResourcesFx {
  private Image largeAddressBookImage = null;
  private Image largePhoneBookImage = null;
  private Image largeBirthdayImage = null;
  private Image largePhonesImage = null;
  private Image personImage = null;
  private Image familyImage = null;
  private Image institutionImage = null;
  private Image employeeImage = null;

//  @Override
//  public ImageIcon getSmallIconDisabled() {
//    return null;
//  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("AdminLogo.jpg"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
  
  public Image getLargeAddressBookImage() {
    if (largeAddressBookImage == null) {
      try {
        largeAddressBookImage = new Image(getClass().getResourceAsStream("AddressBookLarge.jpg"));
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return largeAddressBookImage;
  }
  
  public Image getLargePhoneBookImage() {
    if (largePhoneBookImage == null) {
      try {
        largePhoneBookImage = new Image(getClass().getResourceAsStream("Telefoongids.jpg"));
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return largePhoneBookImage;
  }
  
  public Image getLargeBirthdayImage() {
    if (largeBirthdayImage == null) {
      try {
        largeBirthdayImage = new Image(getClass().getResourceAsStream("Birthday.gif"));
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return largeBirthdayImage;
  }
  
  public Image getLargePhonesImage() {
    if (largePhonesImage == null) {
      try {
        largePhonesImage = new Image(getClass().getResourceAsStream("LargePhones.jpg"));
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return largePhonesImage;
  }
  
  public Image getPersonImage() {
    if (personImage == null) {
      try {
        personImage = new Image(getClass().getResourceAsStream("Person.png"), 18, 18, true, true);
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return personImage;
  }
  
  public Image getFamilyImage() {
    if (familyImage == null) {
      try {
        familyImage = new Image(getClass().getResourceAsStream("Family.png"), 18, 18, true, true);
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return familyImage;
  }
  
  public Image getInstitutionImage() {
    if (institutionImage == null) {
      try {
        institutionImage = new Image(getClass().getResourceAsStream("Institution.png"), 18, 18, true, true);
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return institutionImage;
  }
  
  public Image getEmployeeImage() {
    if (employeeImage == null) {
      try {
        employeeImage = new Image(getClass().getResourceAsStream("Employee.png"), 18, 18, true, true);
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return employeeImage;
  }
}