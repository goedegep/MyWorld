package goedegep.finan.jobappointment.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.finan.jobappointment.JobAppointmentUtil;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.SalaryEvent;
import goedegep.finan.jobappointment.model.SalaryEventType;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.types.model.TypesPackage;
import javafx.scene.control.TableCell;

public class SalaryEventsTable extends EObjectTable<SalaryEvent> {
  
  /**
   * Constructor.
   */
  public SalaryEventsTable(CustomizationFx customization, List<SalaryEvent> salaryEvents) {
    super(customization, JobAppointmentPackage.eINSTANCE.getSalaryEvent(), new SalaryEventsTableDescriptor(), null, salaryEvents);
  }
}

class SalaryEventsTableDescriptor extends EObjectTableDescriptor<SalaryEvent> {
  private static TypesPackage TYPES_PACKAGE = TypesPackage.eINSTANCE;
  private static JobAppointmentPackage JOB_APPOINTMENT_PACKAGE = JobAppointmentPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<SalaryEvent>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<SalaryEvent>>of(
      new EObjectTableColumnDescriptorBasic<SalaryEvent>(TYPES_PACKAGE.getEvent_Date(), "Date", true, true),
      new EObjectTableColumnDescriptorCustom<SalaryEvent>(JOB_APPOINTMENT_PACKAGE.getSalaryEvent_SalaryEventType(), "Type", 150, true, true, column -> {
        TableCell<SalaryEvent, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              SalaryEventType salaryEventType = (SalaryEventType) item;
              setText(JobAppointmentUtil.getSalaryEventTypeText(salaryEventType));
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<SalaryEvent>(null, "Description", 600, true, true, column -> {
            TableCell<SalaryEvent, Object> cell = new TableCell<>() {

              @Override
              protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || (item == null)) {
                  setText(null);
                }
                else {
                  SalaryEvent salaryEvent = (SalaryEvent) item;
                  setText(JobAppointmentUtil.getSalaryEventDescription(salaryEvent));
                }
              }
            };

            return cell;
          })
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<SalaryEvent>> rowOperations = new HashMap<>() {
    {
      put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public SalaryEventsTableDescriptor() {
    super("The are no salary events to show", null, columnDescriptors, rowOperations);
  }

}
