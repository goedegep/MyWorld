package goedegep.jars.mvn.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.IncludesArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.plugins.annotations.LifecyclePhase;
//import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
//
//import org.apache.maven.shared.dependency.graph.DependencyNode;
//import org.apache.maven.shared.dependency.graph.traversal.CollectingDependencyNodeVisitor;
//import org.apache.maven.shared.dependency.graph.traversal.CollectingDependencyNodeVisitor;



@Mojo( name = "getJars")
public class JarsMojo extends AbstractMojo {

//  @Parameter(defaultValue = "${project}", readonly = true, required = true)
//  private MavenProject project;
//
//  @Parameter(defaultValue = "${session}", readonly = true, required = true)
//  private MavenSession session;
//
//  @Component(hint="maven3")
//  private DependencyGraphBuilder dependencyGraphBuilder;
  
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info( "Hello, world." );

    // If you want to filter out certain dependencies.
//    ArtifactFilter artifactFilter = new IncludesArtifactFilter("groupId:artifactId:version");
//    ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
//    buildingRequest.setProject(project);
//    try{
//      DependencyNode depenGraphRootNode = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, artifactFilter);
//      CollectingDependencyNodeVisitor visitor = new  CollectingDependencyNodeVisitor();
//      depenGraphRootNode.accept(visitor);
//      List<DependencyNode> children = visitor.getNodes();
//
//      getLog().info("CHILDREN ARE :");
//      for(DependencyNode node : children) {
//        Artifact atf = node.getArtifact();
//      }
//    }catch(Exception e) {
//      e.printStackTrace();
//    }
  }
}
