package goedegep.pctools.filescontrolled.guifx;

//import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import goedegep.jfx.jfxjunitrunner.JfxJUnitRunner;
import goedegep.pctools.filescontrolled.logic.ControlledSetBuildingTask;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.EqualityType;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import goedegep.pctools.filescontrolled.types.FileCopyInfo;
import goedegep.pctools.filescontrolled.types.FileInfoMap;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.util.Tuplet;
import goedegep.util.emf.EMFResource;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

@RunWith(JfxJUnitRunner.class)
public class FilesControlledTest {

  private static final Path DISC_STRUCTURE_SPECIFICATION_PATH = Paths.get("target", "test-classes", "Test Directory Structure", "TestDiscStructureSpecification.xmi");

  private Tuplet<FileInfoMap, List<FileCopyInfo>> result = null;
  private boolean ready = false;

  @Test
  public void filesControlledTest() {
//    // Create Logger
//    Logger logger = Logger.getLogger("");
//    logger.setLevel(Level.SEVERE);
//    
//    EMFResource<DiscStructureSpecification> emfResource = new EMFResource<>(
//          PCToolsPackage.eINSTANCE,
//          () -> PCToolsFactory.eINSTANCE.createDiscStructureSpecification(), ".xmi");
//    DiscStructureSpecification discStructureSpecification = null;
//    try {
//      discStructureSpecification = emfResource.load(DISC_STRUCTURE_SPECIFICATION_PATH.toAbsolutePath().toString());
//    } catch (FileNotFoundException e1) {
//      e1.printStackTrace();
//    }
//    ControlledSetBuildingTask controlledSetBuildingTask = new ControlledSetBuildingTask(discStructureSpecification, null);
//    Thread  buildControlledSetThread = new Thread(controlledSetBuildingTask);
//
//    controlledSetBuildingTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//
//      @Override
//      public void handle(WorkerStateEvent event) {
//        result = controlledSetBuildingTask.getValue();
//        ready = true;
//      }
//
//    });
//    buildControlledSetThread.start();
//    while (ready == false) {
//      try {
//        Thread.sleep(100);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
////    FileInfoMap generatedFileInfoMap = result.getObject1();
//    List <FileCopyInfo> generatedFileCopyInfoList = result.getObject2();
//    List <FileCopyInfo> expectedFileCopyInfoList = createExpectedFileCopyInfoList();
////    assertThat("FileCopyInfo list not correct", generatedFileCopyInfoList, is(expectedFileCopyInfoList));
//    
//    System.out.println("Before");
//    System.out.println(discStructureSpecification.toString());
//    System.out.println("After");
  }
  
  public List <FileCopyInfo> createExpectedFileCopyInfoList() {
//    List <FileCopyInfo> fileCopyInfos = new ArrayList<>();
//    FileCopyInfo fileCopyInfo;
//    FileInfo fileInfo;
//    Path path;
//    
//    fileCopyInfo = new FileCopyInfo();
//    
//    fileCopyInfo.setEqualityType(EqualityType.EQUAL);
    
//    fileInfo = new FileInfo();
//    fileInfo.setFile(Paths.get("A controlled file.txt"));
//    path = Paths.get("target", "test-classes", "Test Directory Structure", "directory not to be checked", "controlled directory", "subdir 1");
//    path = path.toAbsolutePath();
//    fileInfo.setDirectory(path);    
//    fileCopyInfo.setFirstFileFoundInfo(fileInfo);
//    
//    fileInfo = new FileInfo();
//    fileInfo.setFile(Paths.get("A controlled file.txt"));
//    path = Paths.get("target", "test-classes", "Test Directory Structure", "directory not to be checked", "controlled directory", "subdir 1", "save");
//    path = path.toAbsolutePath();
//    fileInfo.setDirectory(path);    
//    fileCopyInfo.setSecondFileFoundInfo(fileInfo);
//    
//    fileCopyInfos.add(fileCopyInfo);
//
//    return fileCopyInfos;
    
    return null;
  }

}
