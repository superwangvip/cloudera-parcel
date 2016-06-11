package com.cloudera.plugin.mojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.cloudera.plugin.Parcel;

@Mojo(name = "download", requiresProject = false, defaultPhase = LifecyclePhase.VALIDATE)
public class Download extends AbstractMojo {

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "${localRepository}", required = true, readonly = true)
  private ArtifactRepository localRepository;

  @Parameter(required = true)
  private List<Parcel> parcels;

  @Override
  public void execute() throws MojoExecutionException {
    if (parcels == null) {
      parcels = new ArrayList<>();
    }
    for (Parcel parcel : parcels) {
      parcel.setLocalRepositoryDirectory(localRepository.getBasedir());
      parcel.setBaseDirectory(project.getBasedir().getAbsolutePath());
      parcel.download(getLog());
    }
  }

}
