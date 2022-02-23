package goedegep.util.projectinfo;

import java.util.ArrayList;
import java.util.List;

public class AggregatedProjectInfo {
  List<ProjectInfo> projectInfos = new ArrayList<>();

  public AggregatedProjectInfo() {
    
  }
  
  public void addProjectInfo(ProjectInfo projectInfo) {
    projectInfos.add(projectInfo);
  }
  
  public void print() {
    int totalNumberOfWrittenClasses = 0;
    int totalNumberOfGeneratedClasses = 0;
    int totalNumberOfWrittenLines = 0;
    int totalNumberOfGeneratedLines = 0;
    int totalNumberOfEmfProjects = 0;
    for (ProjectInfo projectInfo: projectInfos) {
      if (projectInfo.isEmfModel()) {
        totalNumberOfEmfProjects++;
      }
      Integer numberOfClasses = projectInfo.getNumberOfClasses();
      if (numberOfClasses != null) {
        if (projectInfo.isEmfModel()) {
          totalNumberOfGeneratedClasses += numberOfClasses;
        } else {
          totalNumberOfWrittenClasses += numberOfClasses;
        }
      }
      Integer numberOfLines = projectInfo.getNumberOfLines();
      if (numberOfLines != null) {
        if (projectInfo.isEmfModel()) {
          totalNumberOfGeneratedLines += numberOfLines;
        } else {
          totalNumberOfWrittenLines += numberOfLines;
        }
      }
    }
    
    System.out.println("Overview:");
    System.out.println("Total number of projects: " + projectInfos.size() + ", of which " + totalNumberOfEmfProjects + " are EMF model projects");
    System.out.println("Total number of written classes: " + totalNumberOfWrittenClasses);
    System.out.println("Total number of written lines: " + totalNumberOfWrittenLines);
    System.out.println("Total number of generated classes: " + totalNumberOfGeneratedClasses);
    System.out.println("Total number of generated lines: " + totalNumberOfGeneratedLines);
    
    System.out.println();
    System.out.println("Details per project:");
    
    for (ProjectInfo projectInfo: projectInfos) {
      System.out.println();
      projectInfo.print();
    }
  }
}
