package goedegep.vacations.app.guifx;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.Tuplet;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsFactory;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

/**
 * Import vacation information from files.
 * <p>
 * This class provides functionality to import vacation information into a {@link Vacations} structure.
 */
public class VacationsImporter {
  private static final Logger LOGGER = Logger.getLogger(VacationsImporter.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final VacationsFactory VACATIONS_FACTORY = VacationsFactory.eINSTANCE;
  private static final TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
  private static final FlexDateFormat FDF = new FlexDateFormat(true, true);
  
  private Vacations vacations;     // the vacation information to which the imported data will be added.
  private ComponentFactoryFx componentFactory;
  private boolean abort;           // set to true when importing has to be aborted.
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param vacations the vacation information to which the imported data will be added. This value may not be null.
   */
  public VacationsImporter(CustomizationFx customization, Vacations vacations) {
    Objects.requireNonNull(customization, "argument ‘customization’ must not be null");
    this.vacations = Objects.requireNonNull(vacations, "argument ‘vacations’ must not be null");
    
    componentFactory = customization.getComponentFactoryFx();
  }
  
  /**
   * Import vacation information from directories and files.
   * <p>
   * All sub folders of the specified vacationsFolder are read. Each sub folder is supposed to represent a single vacation.<br/>
   * Each sub folder is handled by the method {@link #handleVacationDirectory}. This method can set <b>abort</b>, if so, the importing stops.
   * 
   * @param vacationsFolder the folder that contains the information about the vacations. This parameter cannot be null.
   * @param vacationPicturesFolder the folder that contains all folders with vacation pictures. This parameter is optional, so it can be null.
   */
  public void importVacations(String vacationsFolder, String vacationPicturesFolder) {
    Objects.requireNonNull(vacationsFolder, "argument ‘vacationsFolder’ must not be null");
    LOGGER.severe("   vacationsFolder=" + vacationsFolder + ", vacationPicturesFolder=" + (vacationPicturesFolder != null ? vacationPicturesFolder : "(null"));

    abort = false;
    
    Path vacationsPath = Paths.get(vacationsFolder);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationsPath)) {
      for (Path path: stream) {
        LOGGER.info("Handling path=" + path.toString());
        if (Files.isDirectory(path)) {
          handleVacationDirectory(path);
        } else {
          LOGGER.severe("path \'" + path.toString() + "\' is not a directory, so it is skipped.");
        }
        if (abort) {
          break;
        }
      }
      
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    LOGGER.info("<= vacations=" + vacations.toString());
  }
  
  /**
   * Handle a single vacation directory.
   * <p>
   * The vacation start date and the title are derived from the directory name, which is supposed to have the following format:
   *   &lt;flex date&gt;&lt;space&gt;&lt;vacation title&gt;.<br/>
   * If a vacation with that start date and title exists, it is checked whether there is anything to be added. This is done by calling
   * the method {@link #vacationCheckContent}.<br/>
   * If such a vacation doesn't exist, it is first checked whether a vacation with the same start date exists. If so the user is asked
   * whether the vacation has to be added, or that the existing vacation, or the vacation directory has to be renamed.<br/>
   * If there is no vacation with the same date, the user is just asked whether the vacation shall be added or not, or whether the importing
   * has to be completely stopped.
   * 
   * @param vacationDirectoryPath the path for the vacation directory, which may not be null.
   * @return
   */
  private Vacation handleVacationDirectory(Path vacationDirectoryPath) {
    LOGGER.severe("=> vacationDirectoryPath=" + vacationDirectoryPath.getFileName().toString());
    
    // derive start date and title from the directory name.
    String directoryName = vacationDirectoryPath.getFileName().toString();
        
    Vacation resultVacation = null;
    
    try {
      Tuplet<FlexDate, String> vacationFlexDateAndTitle = getVacationDateAndTitleFromDirectoryName(directoryName);
      FlexDate date = vacationFlexDateAndTitle.getObject1();
      String titlePart = vacationFlexDateAndTitle.getObject2();
      Vacation vacation = vacations.findVacation(date, titlePart);
      if (vacation != null) {
        LOGGER.severe("Existing vacation.");
        vacationCheckContent(vacation, vacationDirectoryPath);
        resultVacation = vacation;
      } else {
        Vacation vacationOnSameDate =  vacations.findVacation(date);
        LOGGER.severe("New vacation. Do we have to add it?");
        if (vacationOnSameDate != null) {
          StringBuilder buf = new StringBuilder();
          buf.append("The vacation \'").append(directoryName).append("\' doesn't exist yet,").append(NEWLINE);
          buf.append("but there is a vacation \\'").append(createVacationFolderName(vacationOnSameDate)).append("\'.").append(NEWLINE);
          buf.append("You can chose to:").append(NEWLINE);
          buf.append("• Add this vacation").append(NEWLINE);
          buf.append("• Rename the existing vacation to: ").append(titlePart).append(NEWLINE);
          buf.append("• Rename this folder to: ").append(createVacationFolderName(vacationOnSameDate)).append(NEWLINE);
          buf.append("• Ignore this folder").append(NEWLINE);

          ChoiceDialog<UnknownVacationKnownDateChoice> choiceDialog = componentFactory.createChoiceDialog(
              "How to continue?",
              buf.toString(),
              "What do you want?",
              UnknownVacationKnownDateChoice.ADD_VACATION,
              UnknownVacationKnownDateChoice.values());
          choiceDialog.showAndWait();
          UnknownVacationKnownDateChoice unknownVacationChoice = choiceDialog.getSelectedItem();
          LOGGER.severe("Selected item: " + unknownVacationChoice);
          switch (unknownVacationChoice) {
          case ADD_VACATION:
            resultVacation = importVacation(vacationDirectoryPath, date, titlePart);
            vacations.addVacation(resultVacation);
            break;
            
          case IGNORE_DIRECTORY:
            // No action
            break;
            
          case RENAME_DIRECTORY:
            Path newVacationDirectoryPath = vacationDirectoryPath.getParent().resolve(Paths.get(createVacationFolderName(vacationOnSameDate)));
            LOGGER.severe("Going to move directory \'" + vacationDirectoryPath.toString() + "\' to \'" + newVacationDirectoryPath.toString() + "\'");
            try {
              Files.move(vacationDirectoryPath, newVacationDirectoryPath);
            } catch (IOException e) {
              e.printStackTrace();
            }
            break;
            
          case RENAME_VACATION:
            vacationOnSameDate.setTitle(titlePart);
            vacationCheckContent(vacationOnSameDate, vacationDirectoryPath);
            resultVacation = vacationOnSameDate;
            break;
          }
                    
        } else {
          StringBuilder buf = new StringBuilder();
          buf.append("Do you want to add the vacation \'").append(directoryName).append("\' ?");

          ChoiceDialog<UnknownVacationChoice> choiceDialog = componentFactory.createChoiceDialog(
              "How to continue?",
              buf.toString(),
              "What do you want?",
              UnknownVacationChoice.ADD_VACATION,
              UnknownVacationChoice.values());
          choiceDialog.showAndWait();
          UnknownVacationChoice unknownVacationChoice = choiceDialog.getSelectedItem();
          LOGGER.severe("Selected item: " + unknownVacationChoice);
          switch (unknownVacationChoice) {
          case ADD_VACATION:
            resultVacation = importVacation(vacationDirectoryPath, date, titlePart);
            vacations.addVacation(resultVacation);
            break;
            
          case DONT_ADD_VACATION:
            // No action
            break;
            
          case ABORT_IMPORT:
            abort = true;
            break;
          }
          
        }
      }
    } catch (RuntimeException e) {
      LOGGER.severe("Directory cannot be handled. Reason: " + e.getMessage());
    }
    
    LOGGER.severe("<=");
    return resultVacation;
  }
  
  /**
   * Derive the date and title of a vacation from the directory name.
   * <p>
   * The directory name is supposed to have the following format: &lt;flex date&gt;&lt;space&gt;&lt;vacation title&gt;.
   * If the directory name isn't according to this format, a RuntimeException is thrown.
   * 
   * @param directoryName the name of 
   * @return
   */
  private static Tuplet<FlexDate, String> getVacationDateAndTitleFromDirectoryName(String directoryName) {
    LOGGER.info("=> directoryName=" + directoryName);
    
    int spaceIndex = directoryName.indexOf(" ");
    if (spaceIndex == -1) {
      throw new RuntimeException("Wrong directory name format: no space found. Directory name is: '" + directoryName + "'");
    }
    String datePart = directoryName.substring(0, spaceIndex);    
    LOGGER.info("datePart=" + datePart);
    String titlePart = directoryName.substring(spaceIndex + 1);
    LOGGER.info("titlePart=" + titlePart);
    
    FlexDate date;
    try {
      date = FDF.parse(datePart);
    } catch (ParseException e) {
      throw new RuntimeException("Wrong directory name format: first part is not a valid Flex Date. Directory name is: '" + directoryName + "'");
    }
    
    return new Tuplet<FlexDate, String>(date, titlePart);
  }

  private static Vacation importVacation(Path vacationDirectoryPath, FlexDate date, String title) {
    LOGGER.severe("=> vacationDirectory=" + vacationDirectoryPath.toString());
    
    Vacation vacation = VACATIONS_FACTORY.createVacation();
    vacation.setDate(date);
    vacation.setTitle(title);
    
    vacationCheckContent(vacation, vacationDirectoryPath);
        
    LOGGER.severe("<= vacation=" + vacation.toString());
    return vacation;
  }

  /**
   * For an existing vacation, this method checks whether any information is to be added from a directory.
   * 
   * @param vacation the vacation to which information may be added.
   * @param vacationDirectoryPath the related directory from which information may be added.
   */
  private static void vacationCheckContent(Vacation vacation, Path vacationDirectoryPath) {
    LOGGER.info("=> vacationDirectory=" + vacationDirectoryPath.toString());
    List<String> referencedFiles = vacation.getAllReferencedFiles();
    
    FileReference bestandReferentie;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationDirectoryPath)) {
      for (Path path: stream) {
        LOGGER.info("Handling path=" + path.toString());
        if (Files.isDirectory(path)) {
          LOGGER.severe("path is a directory");
        } else {
          LOGGER.info("path is a file.");
          if (!referencedFiles.contains(path.toString())) {
            TextInputDialog dialog = new TextInputDialog("Voucher");
            dialog.setTitle("Hoe verder?");
            dialog.setHeaderText("Wil je het document \'" + path.getFileName().toString() + "\' toevoegen aan de vakantie \'" + vacation.getId() + "\'?");
            dialog.setContentText("Titel van het document");
            Optional<String> optionalDocumentTitle = dialog.showAndWait();
            if (optionalDocumentTitle.isPresent()) {
              String documentTitle = optionalDocumentTitle.get();
              bestandReferentie = TYPES_FACTORY.createFileReference();
              bestandReferentie.setFile(path.toString());
              if (!documentTitle.isEmpty()) {
                bestandReferentie.setTitle(documentTitle);
              }
              vacation.getDocuments().add(bestandReferentie);
              LOGGER.severe("Document added: " + bestandReferentie.toString());
            } else {
              LOGGER.severe("Skipping document");
            }            
          } else {
            LOGGER.severe("Existing document");
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    LOGGER.severe("<=");
  }
  
  /**
   * Create the folder name for a vacation.
   * <p>
   * The specified vacation must have the start date and the title set.
   * 
   * @param vacation 
   * @return
   */
  private static String createVacationFolderName(Vacation vacation) {
    if (!vacation.isSetDate()) {
      throw new IllegalArgumentException("Cannot create folder name for a vacation without a start date.");
    }
    if (!vacation.isSetTitle()) {
      throw new IllegalArgumentException("Cannot create folder name for a vacation without a title.");
    }
    return vacation.getId();
  }
}

enum UnknownVacationKnownDateChoice {
  ADD_VACATION("Add as new vacation"),
  RENAME_VACATION("Rename vacation found for same date"),
  RENAME_DIRECTORY("Rename this folder"),
  IGNORE_DIRECTORY("Ignore this folder");
  
  private String text;
  
  UnknownVacationKnownDateChoice(String text) {
    this.text = text;
  }
  
  public String toString() {
    return text;
  }
}

enum UnknownVacationChoice {
  
  ADD_VACATION("Add vacation"),
  DONT_ADD_VACATION("Don't add vacation"),
  ABORT_IMPORT("Abort importing");
  
  private String text;
  
  UnknownVacationChoice(String text) {
    this.text = text;
  }
  
  public String toString() {
    return text;
  }
}
