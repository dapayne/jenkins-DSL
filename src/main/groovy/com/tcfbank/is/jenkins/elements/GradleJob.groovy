package com.tcfbank.is.jenkins.elements

import com.tcfbank.is.jenkins.elements.subelements.ArtifactoryGradlePlugin

/**
 * Created by t910425 on 3/25/2016.
 */
class GradleJob extends Job{

    String buildFile
    String buildDescription
    boolean fromRootBuildScriptDir = false
    String gradleName
    boolean makeExecutable = false
    String rootBuildScriptDir
    String switches = ""
    String tasks
    boolean useWorkspaceAsHome = false
    boolean useWrapper = false
    String gitUrl
    String gitCredentialsId
    String branchName
    String cronTrigger = ""
    String[] stringParams
    PublishOverSSHJob publishOverSSH
    ShellJob shellAction
    String jUnitResults
    String coberturaResults
    ArtifactoryGradlePlugin deployToArtifactory
}
