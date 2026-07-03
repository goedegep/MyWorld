package goedegep.demo.jfx.editor.logic;

import java.util.logging.Logger;

import goedegep.demo.jfx.editor.step1.gui.PersonEditor;
import goedegep.demo.jfx.editor.step2.gui.PersonEditor2;
import goedegep.demo.jfx.eobjecttreeview.gui.CompanyTreeViewCreator;
import goedegep.emfsample.model.Company;
import goedegep.emfsample.model.EmfSampleFactory;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Person;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.util.datetime.DateUtil;
import goedegep.util.emf.EMFResource;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public class CompanyService {
  @SuppressWarnings("unused")
  private static Logger LOGGER = Logger.getLogger(CompanyService.class.getName());

  private CustomizationFx customization;

  private Company company;
  private EObjectTreeView companyTreeView;
  
  public CompanyService(CustomizationFx customization) {
    this.customization = customization;
    
    EMFResource<Company> resource = new EMFResource<>(EmfSamplePackage.eINSTANCE, EmfSampleFactory.eINSTANCE::createCompany, ".company");
    company = resource.newEObject();
    
    Person person = EmfSampleFactory.eINSTANCE.createPerson();
    person.setSurname("Smith");
    person.setRetirementDate(DateUtil.createDate(21, 6, 2023));
    company.getFormerEmployees().add(person);
  }
  
  public void addFormerEmployee(Person person) {
    company.getFormerEmployees().add(person);
  }

  public void showCompanyTreeView() {
    JfxStage companyTreeViewWindow = new JfxStage(customization, "Company Tree View");
    
    HBox hBox = customization.getComponentFactoryFx().createHBox();
    companyTreeView = new CompanyTreeViewCreator().createCompanyTreeView().setEObject(company);
    companyTreeView.setMinWidth(400);
    companyTreeView.setMinHeight(700);
    hBox.getChildren().add(companyTreeView);

    companyTreeViewWindow.setScene(new Scene(hBox));
    companyTreeViewWindow.setX(1200);
    companyTreeViewWindow.setY(200);
    companyTreeViewWindow.show();
  }

  public void showPersonEditor() {
    PersonEditor personEditor = PersonEditor.newInstance(customization, this);
    personEditor.setX(500);
    personEditor.setY(400);
    personEditor.show();
    
    if (companyTreeView != null) {
      companyTreeView.addObjectSelectionListener((_, treeItem) -> {
        if (treeItem != null) {
          Object value = treeItem.getValue();
          if (value instanceof Person person) {
            personEditor.setObject(person);
          }
        }
      });
    }
  }

  public void showPersonEditorStep2() {
    PersonEditor2 personEditor2 = PersonEditor2.newInstance(customization, this);
    personEditor2.setX(500);
    personEditor2.setY(400);
    personEditor2.show();
    
    if (companyTreeView != null) {
      companyTreeView.addObjectSelectionListener((_, treeItem) -> {
        if (treeItem != null) {
          Object value = treeItem.getValue();
          if (value instanceof Person person) {
            personEditor2.setObject(person);
          }
        }
      });
    }
  }
}
