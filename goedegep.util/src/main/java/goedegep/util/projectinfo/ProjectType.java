package goedegep.util.projectinfo;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * The project is a model project if:
 * - there is a .ecore file AND
 * - there is a *Factory.java file AND
 * - there is a *Package.java file AND
 * - 
 * @author Peter
 *
 */
public class ProjectType extends SimpleFileVisitor<Path> {
  private static final PathMatcher ecoreFileMatcher = FileSystems.getDefault().getPathMatcher("glob:" + "*.ecore");
  private static final PathMatcher factoryMatcher = FileSystems.getDefault().getPathMatcher("glob:" + "*Factory.java");
  private static final PathMatcher adapterFactoryMatcher = FileSystems.getDefault().getPathMatcher("glob:" + "*AdapterFactory.java");
  private static final PathMatcher packageMatcher = FileSystems.getDefault().getPathMatcher("glob:" + "*Package.java");
  
  
  private Path ecoreFile = null;
  private Path factoryFile = null;
  private Path packageFile = null;
  private Path basePackageDir = null;
  private boolean implDirFound = false;
  private boolean utilDirFound = false;

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    if (!attrs.isDirectory()) {
      Path name = file.getFileName();
      if (name != null) {
        
        if (ecoreFileMatcher.matches(name)) {
          ecoreFile = file;
        }
        
        if (factoryMatcher.matches(name)  && !adapterFactoryMatcher.matches(name)) {
          factoryFile = file;
        }
        
        if (packageMatcher.matches(name)) {
          packageFile = file;
        }
      }
    }
    
    return CONTINUE;
  }
  
  public boolean evaluate(Path projectDirectory) {
    ecoreFile = null;
    factoryFile = null;
    packageFile = null;
    
    try {
      Files.walkFileTree(projectDirectory, this);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    if (packageFile != null) {
      basePackageDir = packageFile.getParent();
      Path checkDir = basePackageDir.resolve("impl");
      implDirFound = Files.exists(checkDir);
      checkDir = basePackageDir.resolve("util");
      utilDirFound = Files.exists(checkDir);
    }
    
    return ecoreFile != null  &&
           factoryFile != null  &&
           packageFile != null  &&
           implDirFound  &&  utilDirFound;
    
  }
      
  public void printResult() {
    System.out.println("ecoreFile = " + ecoreFile);
    System.out.println("factoryFile = " + factoryFile);
    System.out.println("packageFile = " + packageFile);
    System.out.println("basePackageDir = " + basePackageDir);
    if (ecoreFile != null  &&
        factoryFile != null  &&
        packageFile != null  &&
        implDirFound  &&  utilDirFound) {
      System.out.println("EMF Model project");
    }
  }
}
