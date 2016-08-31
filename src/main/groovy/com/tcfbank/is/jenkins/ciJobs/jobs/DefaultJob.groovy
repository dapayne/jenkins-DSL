package com.tcfbank.is.jenkins.ciJobs.jobs

import com.tcfbank.is.jenkins.config.Constants
import com.tcfbank.is.jenkins.elements.Folder
import com.tcfbank.is.jenkins.elements.GradleJob
import com.tcfbank.is.jenkins.elements.PublishOverSSHJob
import com.tcfbank.is.jenkins.elements.subelements.ArtifactoryGradlePlugin
import com.tcfbank.is.jenkins.elements.subelements.TransferSet

/**
 * Created by T910431 on 4/14/2016.
 */
class DefaultJob extends GradleJob {
    TransferSet transferSet;
    public DefaultJob(String appName, String appInitials) {
        displayName = appName;
        folder = new Folder(appName)
        gitUrl = "\${SSH_URL}"
        // Leaving out " " spaces as that evaluates to null eg. stringParams = ["payload", null, "Payload sent from the github enterprise webhook"]
        stringParams = "payload   Payload sent from the github enterprise webhook".split()
        tasks = "clean build"
        gitCredentialsId= "3677b686-66e6-4e04-b460-825673631d66"
        if(appInitials) {
            transferSet = new TransferSet(sourceFiles: "build/libs/*.jar",
                    removePrefix: "build/libs",
                    execCommand: "sh /opt/services/restart_${appInitials}.sh")
        }
        jUnitResults = "build/test-results/*.xml"
        coberturaResults="build/reports/cobertura/coverage.xml"
    }

    public DefaultJob(String appName, String appInitials, boolean ignoreCobertura) {
        this(appName, appInitials)
        if(ignoreCobertura) {
            coberturaResults = null
        }
    }

    public DefaultJob setAsDevJob() {
        jobName = "${folder.name}/dev-deploy-${displayName}"
        branchName = "*/development"
        buildDescription = "Build/Deploy ${displayName} to DEV Environment."
        if(transferSet) {
            publishOverSSH = new PublishOverSSHJob(serverName: Constants.devServicesNode, transferSets: [transferSet])
        }
        deployToArtifactory = new ArtifactoryGradlePlugin(publishRepo: "libs-snapshot-local")
        return this
    }

    public DefaultJob setAsTstJob() {
        jobName = "${folder.name}/tst-deploy-${displayName}"
        branchName = "*/master"
        buildDescription = "Build/Deploy ${displayName} to TST Environment."
        if(transferSet) {
            publishOverSSH = new PublishOverSSHJob(serverName: Constants.tstServicesNode, transferSets: [transferSet])
        }
        deployToArtifactory = new ArtifactoryGradlePlugin(publishRepo: "libs-release-local")
        return this
    }
}
