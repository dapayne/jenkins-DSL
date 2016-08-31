package com.tcfbank.is.jenkins.ciJobs.jobs

import com.tcfbank.is.jenkins.config.Constants
import com.tcfbank.is.jenkins.elements.Folder
import com.tcfbank.is.jenkins.elements.GradleJob
import com.tcfbank.is.jenkins.elements.PublishOverSSHJob
import com.tcfbank.is.jenkins.elements.ShellJob
import com.tcfbank.is.jenkins.elements.subelements.ArtifactoryGradlePlugin
import com.tcfbank.is.jenkins.elements.subelements.TransferSet

/**
 * Created by t910425 on 3/28/2016.
 */
class DefaultAPIPolicyDeployJob extends GradleJob {

    DefaultAPIPolicyDeployJob(String appName) {
        displayName = "deploy-${appName}-api-policy"
        folder = new Folder()
        jobName = "${folder.name}/${displayName}"
        gitUrl = "https://github.com/tcfbank/${appName}-api-policy.git"
        branchName = "*/master"
        tasks = "archiveFolder"
        buildDescription = "Creates tar file containing all the config files for CA api gateway"
        String fileNamePrefix = appName.split("-").collect{ it.capitalize() }.join("")
        TransferSet transferSet = new TransferSet(sourceFiles: "argfile/${fileNamePrefix}APIargFile-TST.properties,mappings/${fileNamePrefix}APIMappings-TST.xml,${fileNamePrefix}API.xml,template/${fileNamePrefix}APITemplate-TST.properties",
                                                  remoteDirectory: "/${fileNamePrefix}API-TST/GatewayMigrationUtility",
                                                  execCommand: "cd ${fileNamePrefix}API-TST/GatewayMigrationUtility;\n" +
                                                          "nohup sh /opt/GatewayMigrationUtility-1.2.00/GatewayMigrationUtility.sh  migrateIn  --argFile  argfile/${fileNamePrefix}APIargFile-TST.properties --bundle ${fileNamePrefix}API.xml  --results results.xml --map mappings/${fileNamePrefix}APIMappings-TST.xml --template template/${fileNamePrefix}APITemplate-TST.properties")
        publishOverSSH = new PublishOverSSHJob(serverName: Constants.buildNode, transferSets: [transferSet])
        shellAction = new ShellJob(commands: "cat /root/${fileNamePrefix}API-TST/GatewayMigrationUtility/results.xml")
        deployToArtifactory = new ArtifactoryGradlePlugin(resolveRepo: "libs-snapshot", publishRepo: "ext-snapshot-local",
                                                          includePatterns: "*.tar")
    }

}
