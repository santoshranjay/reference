package com.san.poc.maven.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

/**
 * Goal to deploy the artifact on dev server.
 *
 * @goal deploy
 * 
 * @phase deploy
 */
public class DeployMojo extends AbstractMojo {
	
	private static final String PS = File.separator;
	/**
	 * deployment location (e.g, oep dev server location)
	 * @parameter property="project.deploy.directory"
	 * @required
	 */
	private File outputDirectory;
	/**
	 * @parameter default-value="${project}"
	 * @required
	 */
	private MavenProject project;

	/**
	 * @parameter default-value="${localRepository}"
	 * @required
	 */
	private ArtifactRepository localRepo;

	/**
	 * @parameter default-value="${build.finalName}"
	 */
	private String finalName;

	

	/**
	 * execute the goal 'deploy'
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		File f = outputDirectory;

		if (!f.exists()) {
			getLog().warn(
					"Deploy directory '" + outputDirectory + "' doesn't exist.");
			return;
		}

		getLog().info("Copying Artifact '" + getArtifactLocation() + "' to directory '" + outputDirectory + "'");
		try {
			FileUtils.copyFileToDirectory(new File(getArtifactLocation()),
					outputDirectory);
		} catch (IOException e) {
			getLog().warn(e);
			throw new MojoFailureException(e.getMessage());
		}

	}


	/**
	 * create the artifact path of local repository.
	 * @return - artifact absolute path.
	 */
	private String getArtifactLocation() {
		String groupLoc = StringUtils.replace(project.getGroupId(), ".", PS);
		String artifactDefaultName = project.getArtifactId() + "-"
				+ project.getVersion() + ".jar";

		String artifactLoc = localRepo.getBasedir() + PS + groupLoc + PS
				+ project.getArtifactId() + PS + project.getVersion() + PS
				+ (finalName == null ? artifactDefaultName : finalName);
		return artifactLoc;
	}
}
