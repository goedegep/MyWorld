package goedegep.finan.jobappointment;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.jobappointment.model.CollectiveRaise;
import goedegep.finan.jobappointment.model.JobAppointment;
import goedegep.finan.jobappointment.model.JobAppointmentFactory;
import goedegep.finan.jobappointment.model.ParttimePercentage;
import goedegep.finan.jobappointment.model.SalaryEvent;
import goedegep.finan.jobappointment.model.SalaryEventType;
import goedegep.finan.jobappointment.model.SalaryNotice;
import goedegep.finan.jobappointment.model.SalaryPayment;
import goedegep.jfx.FormattersFactory;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class JobAppointmentUtil {
  private static final Logger LOGGER = Logger.getLogger(JobAppointmentUtil.class.getName());
  private static final PgCurrencyFormat     CF = new PgCurrencyFormat();

  public static List<SalaryPayment> getSalaryPayments(JobAppointment jobAppointment) {
   
    List<SalaryPayment> salaryPayments = new ArrayList<>();
    for (SalaryEvent salaryEvent: jobAppointment.getSalaryevents()) {
      if (salaryEvent.getSalaryEventType() == SalaryEventType.SALARY_PAYMENT) {
        salaryPayments.add((SalaryPayment) salaryEvent);
      }
    }
    
    return salaryPayments;
  }

  /**
   * Add the salary payments to the salary events of a JobAppointment.
   * 
   * @param jobAppointment the JobAppointment for which the salary payments are to be added.
   * @param lastYear the year of the last period to handle.
   * @param lastMonth the month of the last period to handle.
   */
  public static void addSalaryPayments(JobAppointment jobAppointment, int lastYear, int lastMonth) {
    LOGGER.info("=> lastYear=" + lastYear + ", lastMonth=" + lastMonth);
        
    Calendar progressionDateCalendar = new GregorianCalendar();
    progressionDateCalendar.setTime(DateUtil.localDateToDate(jobAppointment.getCommencementOfEmploymentDate()));
    Calendar endDate = new GregorianCalendar(lastYear, lastMonth, 1);
    
    // To merge the created SalaryPayments with the existing events, we iterate over a copy of the existing events
    // and clear the existing events list.
    // (Using add() on an iterator on the existing events isn't easy here, because of the ordering).
    List<SalaryEvent> salaryEventsCopy = new ArrayList<>(jobAppointment.getSalaryevents());
    jobAppointment.getSalaryevents().clear();
    Iterator<SalaryEvent> eventIterator = salaryEventsCopy.iterator();
    
    SalaryEvent salaryEvent = getNextSalaryEvent(eventIterator);
    PgCurrency grossSalary = jobAppointment.getStartingSalary();
    int parttimePercentage = jobAppointment.getStartingParttimePercentage();
    
    Date currentSalaryEventDate = null;
    
    if (salaryEvent != null) {
      currentSalaryEventDate = salaryEvent.getDate().toDate();
    }

    while (!progressionDateCalendar.after(endDate)) {
      
      while (salaryEvent != null  &&
          progressionDateCalendar.getTime().equals(currentSalaryEventDate)) {
        if (salaryEvent instanceof SalaryNotice) {
          SalaryNotice salaryNotice = (SalaryNotice) salaryEvent;
          grossSalary = salaryNotice.getNewSalaryFulltime();
          LOGGER.info("new grossSalary from SalaryNotice: " + CF.format(grossSalary));
        } else if (salaryEvent instanceof CollectiveRaise) {
          CollectiveRaise collectiveRaise = (CollectiveRaise) salaryEvent;
          if (collectiveRaise.getPercentage() != null) {
            grossSalary = grossSalary.multiply(1 + collectiveRaise.getPercentage().doubleValue() / 100).roundUp();
          }
          if (collectiveRaise.getAmount() != null) {
            grossSalary = grossSalary.add(collectiveRaise.getAmount());
          }
          LOGGER.info("new grossSalary from CollectiveRaise: " + CF.format(grossSalary));
        } else if (salaryEvent instanceof ParttimePercentage) {
          ParttimePercentage parttimePercentageEvent = (ParttimePercentage) salaryEvent;
          parttimePercentage = parttimePercentageEvent.getParttimePercentage();
          LOGGER.info("new parttimePercentage from ParttimePercentage: " + parttimePercentage);
        }

        jobAppointment.getSalaryevents().add(salaryEvent);
        LOGGER.info("Adding salaryEvent: " + salaryEvent.toString());
        salaryEvent = getNextSalaryEvent(eventIterator);
        
        if (salaryEvent != null) {
          currentSalaryEventDate = salaryEvent.getDate().toDate();
        }
      }
      
      // Create and add salary payment.
      SalaryPayment salaryPayment = JobAppointmentFactory.eINSTANCE.createSalaryPayment();
      salaryPayment.setSalaryEventType(SalaryEventType.SALARY_PAYMENT);
      YearMonth yearMonth = YearMonth.of(progressionDateCalendar.get(Calendar.YEAR), progressionDateCalendar.get(Calendar.MONTH) + 1);
      salaryPayment.setPeriod(yearMonth);
      if (parttimePercentage == 100) {
        salaryPayment.setGrossSalary(grossSalary);
      } else {
        salaryPayment.setGrossSalary(grossSalary.multiply(parttimePercentage).divide(100));
      }
      LOGGER.info("Adding SalaryPayment: " + salaryPayment.toString());
      jobAppointment.getSalaryevents().add(salaryPayment);

      progressionDateCalendar.add(Calendar.MONTH, 1);
    }
  }
  
  /**
   * Get the next salary change related event from a SalaryEvent iterator.
   * 
   * @param eventIterator the Iterator to get the next salary related event from.
   * @return the next salary related event from the eventIterator, or null if there isn't such an event.
   */
  private static SalaryEvent getNextSalaryEvent(Iterator<SalaryEvent> eventIterator) {
    LOGGER.info("=>");
    
    SalaryEvent salaryEvent = null;
    boolean found = false;

    while (!found  &&  eventIterator.hasNext()) {
      salaryEvent = eventIterator.next();
      LOGGER.info("salaryEvent: " + salaryEvent.toString());
      if ((salaryEvent instanceof SalaryNotice)  ||
          (salaryEvent instanceof CollectiveRaise)  ||
          (salaryEvent instanceof ParttimePercentage)) {
        found = true;
      }
    }

    if (found) {
      LOGGER.info("<= " + salaryEvent.toString());
      return salaryEvent;
    } else {
      LOGGER.info("<= (null)");
      return null;
    }
  }
  
  public static String getSalaryEventTypeText(SalaryEventType salaryEventType) {
    switch (salaryEventType) {
    case COLLECTIVE_RAISE:
      return "collective raise";
      
    case PARTTIME_PERCENTAGE:
      return "new parttime percentage";
      
    case SALARY_NOTICE:
      return "salary notice";
      
    case SALARY_PAYMENT:
      return "salary payment";
      
    default:
      throw new RuntimeException("unknown SalaryEventType: " + salaryEventType);
    }
  }
  
  /**
   * Get the description for a salary event.
   * 
   * @param salaryEvent the SalaryEvent to get the description for.
   * @return the description of <code>salaryEvent</code>
   */
  public static String getSalaryEventDescription(SalaryEvent salaryEvent) {
    PgCurrencyFormat pgCurrencyFormat = FormattersFactory.getPgCurrencyFormat();
    DateTimeFormatter dateDateTimeFormatter = FormattersFactory.getDateDateTimeFormatterInstance();
    DateTimeFormatter yearMonthDateTimeFormatter = FormattersFactory.getYearMonthDateTimeFormatterInstance();
    FixedPointValueFormat fixedPointValueFormat = FormattersFactory.getFixedPointValueFormat();
    
    StringBuilder buf = new StringBuilder();
    boolean commaNeeded = false;
    
    switch (salaryEvent.getSalaryEventType()) {
    case COLLECTIVE_RAISE:
      CollectiveRaise collectiveRaise = (CollectiveRaise) salaryEvent;
      if (collectiveRaise.getPercentage() != null) {
        buf.append(fixedPointValueFormat.format(collectiveRaise.getPercentage())).append("%");
      }
      if (collectiveRaise.getAmount() != null) {
        buf.append(pgCurrencyFormat.format(collectiveRaise.getAmount()));
      }
      break;
      
    case PARTTIME_PERCENTAGE:
      ParttimePercentage parttimePercentage = (ParttimePercentage) salaryEvent;
      if (parttimePercentage.getParttimePercentage() != null) {
        buf.append(parttimePercentage.getParttimePercentage()).append("%");
      }
      break;
      
    case SALARY_NOTICE:
      SalaryNotice salaryNotice = (SalaryNotice) salaryEvent;
      
      if (salaryNotice.getCurrentSalaryFulltime() != null) {
        buf.append("current fulltime salary: ").append(pgCurrencyFormat.format(salaryNotice.getCurrentSalaryFulltime()));
        commaNeeded = true;
      }
      
      if (salaryNotice.getCurrentParttimePercentage() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("current parttime percentage: ").append(salaryNotice.getCurrentParttimePercentage()).append("%");
        commaNeeded = true;
      }
      
      if (salaryNotice.getCurrentSalaryParttime() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("current parttime salary: ").append(pgCurrencyFormat.format(salaryNotice.getCurrentSalaryParttime()));
        commaNeeded = true;
      }
      
      if (salaryNotice.getNewSalaryFulltime() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("new fulltime salary: ").append(pgCurrencyFormat.format(salaryNotice.getNewSalaryFulltime()));
        commaNeeded = true;
      }
      
      if (salaryNotice.getNewParttimePercentage() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("new parttime percentage: ").append(salaryNotice.getNewParttimePercentage()).append("%");
        commaNeeded = true;
      }
      
      if (salaryNotice.getNewSalaryParttime() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("new parttime salary: ").append(pgCurrencyFormat.format(salaryNotice.getNewSalaryParttime()));
        commaNeeded = true;
      }
      
      String functionGroup = salaryNotice.getFunctionGroup();
      if (functionGroup != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("new function group: ").append(functionGroup);
        commaNeeded = true;
      }
      
      if (salaryNotice.getStartingDate() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("starting from: ").append(dateDateTimeFormatter.format(salaryNotice.getStartingDate()));
      }
      
      break;
      
    case SALARY_PAYMENT:
      SalaryPayment salaryPayment = (SalaryPayment) salaryEvent;
      if (salaryPayment.getPeriod() != null) {
        buf.append("period: ").append(yearMonthDateTimeFormatter.format(salaryPayment.getPeriod()));
        commaNeeded = true;
      }
      
      if (salaryPayment.getGrossSalary() != null) {
        if (commaNeeded) {
          buf.append(", ");
        }
        buf.append("gross salary: ").append(pgCurrencyFormat.format(salaryPayment.getGrossSalary()));
      }
      break;
    }
    
    if (salaryEvent.getNotes() != null) {
      if (buf.length() != 0) {
        buf.append(", ");
      }
      buf.append(salaryEvent.getNotes());
    }
    
    return buf.toString();
  }
}
