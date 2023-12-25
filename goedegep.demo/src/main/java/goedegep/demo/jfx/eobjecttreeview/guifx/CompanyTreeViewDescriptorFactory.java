package goedegep.demo.jfx.eobjecttreeview.guifx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

import goedegep.appgen.TableRowOperation;
import goedegep.demo.guifx.DemoAppResources;
import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Person;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListValueDescriptor;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.resources.ImageSize;
import goedegep.util.emf.EmfPackageHelper;

public class CompanyTreeViewDescriptorFactory {
  private static final EmfSamplePackage EMF_SAMPLE_PACKAGE = EmfSamplePackage.eINSTANCE;
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");


  public static EObjectTreeDescriptor createDescriptor() {
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    EmfPackageHelper companyPackageHelper = new EmfPackageHelper(EMF_SAMPLE_PACKAGE);
    
    createAndAddEObjectTreeDescriptorForCompany(eObjectTreeDescriptor, companyPackageHelper);
    createAndAddEObjectTreeDescriptorForPerson(eObjectTreeDescriptor, companyPackageHelper);
    createAndAddEObjectTreeDescriptorForBirthday(eObjectTreeDescriptor, companyPackageHelper);
    
    return eObjectTreeDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.emfsample.model.Company.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Company descriptor is to be added.
   * @param companyPackageHelper an <code>EmfPackageHelper</code> for the <code>EmfSamplePackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForCompany(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper companyPackageHelper) {
    EClass eClass = companyPackageHelper.getEClass("goedegep.emfsample.model.Company");
    List<NodeOperationDescriptor> nodeOperationDescriptors;
        
    // Company
    // Note: at this level there are no node operations.
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            return "My Digital Life";
          }, true, null,
        eObject -> {
            return Resources.getCompanyImage();
        });
    eObjectTreeItemClassDescriptor.setStrongText(true);
    
    // Note: we use a different order for the children than the default order.
    // Company.employees
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New employee"));
    EObjectTreeItemClassListReferenceDescriptor classListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(EMF_SAMPLE_PACKAGE.getCompany_Employees(), "Employees", false, nodeOperationDescriptors);
    classListReferenceDescriptor.setStrongText(true);
    classListReferenceDescriptor.setNodeIconFunction(object -> Resources.getEmployeesImage());
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(classListReferenceDescriptor);
    
    // Company.formerEmployees
    classListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(EMF_SAMPLE_PACKAGE.getCompany_FormerEmployees(), "Former employees", false, null);
    classListReferenceDescriptor.setStrongText(true);
    classListReferenceDescriptor.setNodeIconFunction(object -> Resources.getFormerEmployeesImage());
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(classListReferenceDescriptor);
    
    // Company.birthdays
    classListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(EMF_SAMPLE_PACKAGE.getCompany_Birthdays(), "Birthdays", false, null);
    classListReferenceDescriptor.setStrongText(true);
    classListReferenceDescriptor.setNodeIconFunction(object -> Resources.getBirthdayImage());
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(classListReferenceDescriptor);
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.emfsample.model.Person.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Person descriptor is to be added.
   * @param companyPackageHelper an <code>EmfPackageHelper</code> for the <code>EmfSamplePackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForPerson(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper companyPackageHelper) {
    EClass eClass = companyPackageHelper.getEClass("goedegep.emfsample.model.Person");
    List<NodeOperationDescriptor> nodeOperationDescriptors;
        
    // Person
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New employee after this employee"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          StringBuilder buf = new StringBuilder();
          Person person = (Person) eObject;
          boolean spaceNeeded = false;

          for (String firstName: person.getFirstnames()) {
            if (spaceNeeded) {
              buf.append(" ");
            }
            buf.append(firstName);
            spaceNeeded = true;
          }
          
          if (spaceNeeded) {
            buf.append(" ");
          }
          buf.append(person.getSurname());
          return buf.toString();
        }, false, nodeOperationDescriptors,
        eObject -> Resources.getPersonImage());
    eObjectTreeItemClassDescriptor.setStrongText(true);
    
    // Person.firstNames
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New firstname before this name"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New firstname after this name"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this firstname"));
    EObjectTreeItemAttributeListValueDescriptor listValueDescriptor = new EObjectTreeItemAttributeListValueDescriptor(false, nodeOperationDescriptors);
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Add a firstname"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeListDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Firstnames(), "First names", false, nodeOperationDescriptors, listValueDescriptor));
    
    // Person.surname
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Surname(), "Surname", null));
    
    // Person.gender
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Gender(), "Gender", null));
    
    // Person.birthday
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Birthday(), companyPackageHelper.getEClass("goedegep.emfsample.model.Person"),
        null, false, null));
    
    // Person.retirementDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_RetirementDate(), "Retirement date", DF, null));
    
    // Person.hasChildren
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_HasChildren(), "Has children", PresentationType.BOOLEAN, null));
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.emfsample.model.Birthday.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Birthday descriptor is to be added.
   * @param companyPackageHelper an <code>EmfPackageHelper</code> for the <code>EmfSamplePackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForBirthday(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper companyPackageHelper) {
    EClass eClass = companyPackageHelper.getEClass("goedegep.emfsample.model.Birthday");
        
    // Birthday
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          StringBuilder buf = new StringBuilder();
          if (eObject != null) {
          Birthday birthday = (Birthday) eObject;

          buf.append(birthday.getDay())
          .append("-")
          .append(birthday.getMonth())
          .append("-")
          .append(birthday.getYear());
          } else {
            buf.append("Birthday not set");
          }
          return buf.toString();
        }, false, null,
        eObject -> {
            return Resources.getBirthdayImage();
        });
    eObjectTreeItemClassDescriptor.setStrongText(true);
    
    // Birthday.day
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getBirthday_Day(), "Day", null));
    
    // Birthday.month
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getBirthday_Month(), "Month", null));
    
    // Birthday.year
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getBirthday_Year(), "Year", null));
    
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

}
