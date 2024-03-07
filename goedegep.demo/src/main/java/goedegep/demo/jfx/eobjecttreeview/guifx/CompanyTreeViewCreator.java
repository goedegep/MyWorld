package goedegep.demo.jfx.eobjecttreeview.guifx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import goedegep.appgen.TableRowOperation;
import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Person;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListValueDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;

public class CompanyTreeViewCreator {
  private static final EmfSamplePackage EMF_SAMPLE_PACKAGE = EmfSamplePackage.eINSTANCE;
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");


  /**
   * Create an {@ling EObjectTreeView} for a {link Company}.
   * 
   * @return an {@ling EObjectTreeView} for a {link Company}.
   */
  public EObjectTreeView createCompanyTreeView() {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .addEClassDescriptor(EMF_SAMPLE_PACKAGE.getCompany(), createDescriptorForCompany())
        .addEClassDescriptor(EMF_SAMPLE_PACKAGE.getPerson(), createDescriptorForPerson())
        .addEClassDescriptor(EMF_SAMPLE_PACKAGE.getBirthday(), createDescriptorForBirthday());
    
    return eObjectTreeView;
    
  }
    
  /**
   * Create the descriptor for the EClass goedegep.emfsample.model.Company.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForCompany() {
    List<NodeOperationDescriptor> nodeOperationDescriptors;
        
    // Company
    // Note: at this level there are no node operations.
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          return "My Digital Life";
        })
        .setStrongText(true)
        .setExpandOnCreation(true)
        .setNodeIconFunction(eObject -> {
          return Resources.getCompanyImage();
        });
    
    EObjectTreeItemClassListReferenceDescriptor classListReferenceDescriptor;
    
    // Note: we use a different order for the children than the default order.
    // Company.employees
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New employee"));
    classListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(EMF_SAMPLE_PACKAGE.getCompany_Employees())
        .setLabelText("Employees")
        .setStrongText(true)
        .setNodeIconFunction(object -> Resources.getEmployeesImage())
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New employee"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(classListReferenceDescriptor);
    
    // Company.formerEmployees
    classListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(EMF_SAMPLE_PACKAGE.getCompany_FormerEmployees())
        .setLabelText("Former employees");
    classListReferenceDescriptor.setStrongText(true);
    classListReferenceDescriptor.setNodeIconFunction(object -> Resources.getFormerEmployeesImage());
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(classListReferenceDescriptor);
    
    // Company.birthdays
    classListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(EMF_SAMPLE_PACKAGE.getCompany_Birthdays(), EMF_SAMPLE_PACKAGE.getCompany())
        .setLabelText("Birthdays");
    classListReferenceDescriptor.setStrongText(true);
    classListReferenceDescriptor.setNodeIconFunction(object -> Resources.getBirthdayImage());
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(classListReferenceDescriptor);
        
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.emfsample.model.Person.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForPerson() {
    List<NodeOperationDescriptor> nodeOperationDescriptors;
        
    // Person
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New employee after this employee"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
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
        })
        .setStrongText(true)
        .setNodeIconFunction(eObject -> Resources.getPersonImage())
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New employee after this employee"));
    
    // Person.firstNames
    EObjectTreeItemAttributeListValueDescriptor listValueDescriptor = new EObjectTreeItemAttributeListValueDescriptor()
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New firstname before this name"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New firstname after this name"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this firstname"));
    
    EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Firstnames())
        .setLabelText("First names")
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Add a firstname"))
        .setListValuesDescriptor(listValueDescriptor);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // Person.surname
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Surname())
        .setLabelText("Surname");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Person.gender
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Gender())
        .setLabelText("Gender");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Person.birthday
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(EMF_SAMPLE_PACKAGE.getPerson_Birthday());
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    // Person.retirementDate
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_RetirementDate())
        .setLabelText("Retirement date")
        .setFormat(DF);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Person.hasChildren
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getPerson_HasChildren())
        .setLabelText("Has children")
        .setPresentationType(PresentationType.BOOLEAN);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
        
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.emfsample.model.Birthday.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForBirthday() {
        
    // Birthday
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
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
        })
        .setStrongText(true)
        .setNodeIconFunction(eObject -> {
          return Resources.getBirthdayImage();
        });
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // Birthday.day
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getBirthday_Day())
        .setLabelText("Day");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Birthday.month
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getBirthday_Month())
        .setLabelText("Month");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Birthday.year
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(EMF_SAMPLE_PACKAGE.getBirthday_Year())
        .setLabelText("Year");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
        
    return eObjectTreeItemClassDescriptor;
  }

}
