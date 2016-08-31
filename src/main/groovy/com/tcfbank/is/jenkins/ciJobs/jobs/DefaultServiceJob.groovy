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
class DefaultServiceJob extends GradleJob {
    TransferSet transferSet;
    public DefaultServiceJob(String appName, String appInitials) {
        displayName = appName;
        folder = new Folder(appName)
        gitUrl = "https://github.com/tcfbank/${appName}.git"
        //cronTrigger = "H/15 7-19 * * 1-5"
        tasks = "clean build"
        if(appInitials) {
            transferSet = new TransferSet(sourceFiles: "build/libs/*.jar",
                    removePrefix: "build/libs",
                    execCommand: "sh /opt/services/restart_${appInitials}.sh")
        }
        jUnitResults = "build/test-results/*.xml"
        coberturaResults="build/reports/cobertura/coverage.xml"
    }

    public DefaultServiceJob(String appName, String appInitials, boolean ignoreCobertura) {
        this(appName, appInitials)
        if(ignoreCobertura) {
            coberturaResults = null
        }
    }

    public DefaultServiceJob setAsDevJob() {
        jobName = "${folder.name}/dev-deploy-${displayName}"
        branchName = "*/development"
        buildDescription = "Build/Deploy ${displayName} to DEV Environment."
        if(transferSet) {
            publishOverSSH = new PublishOverSSHJob(serverName: Constants.devServicesNode, transferSets: [transferSet])
        }
        deployToArtifactory = new ArtifactoryGradlePlugin(publishRepo: "libs-snapshot-local")
        return this
    }

    public DefaultServiceJob setAsTstJob() {
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
