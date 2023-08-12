package goedegep.demo.jfx.eobjecttreeview.guifx;

import javafx.scene.image.Image;

public class Resources {
  private static final double IMAGE_WIDTH = 34.0;
  private static final double IMAGE_HEIGHT = 34.0
      ;
  private static Image companyImage = null;
  private static Image employeesImage = null;
  private static Image formerEmployeesImage = null;
  private static Image personImage = null;
  private static Image birthdayImage = null;

  public static Image getCompanyImage() {
    if (companyImage == null) {
      companyImage = new Image(Resources.class.getResourceAsStream("Institution.png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
    }
    
    return companyImage;
  }

  public static Image getEmployeesImage() {
    if (employeesImage == null) {
      employeesImage = new Image(Resources.class.getResourceAsStream("Employee.png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
    }
    
    return employeesImage;
  }

  public static Image getFormerEmployeesImage() {
    if (formerEmployeesImage == null) {
      formerEmployeesImage = new Image(Resources.class.getResourceAsStream("Former employee.png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
    }
    
    return formerEmployeesImage;
  }

  public static Image getPersonImage() {
    if (personImage == null) {
      personImage = new Image(Resources.class.getResourceAsStream("Person.png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
    }
    
    return personImage;
  }

  public static Image getBirthdayImage() {
    if (birthdayImage == null) {
      birthdayImage = new Image(Resources.class.getResourceAsStream("Birthday.gif"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
    }
    
    return birthdayImage;
  }
}
