package com.tcfbank.is.jenkins.utils

import com.tcfbank.is.jenkins.config.Constants
import com.tcfbank.is.jenkins.elements.GradleJob
import com.tcfbank.is.jenkins.elements.subelements.TransferSet
import javaposse.jobdsl.dsl.ContextHelper
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.helpers.publisher.PublishBuildContext

/**
 * Created by t910425 on 3/25/2016.
 */
class JobsCreator {


    static Job createGradleJob(DslFactory dslFactory, GradleJob job, boolean shouldDisable) {
        FolderCreator.createFolder(dslFactory,job.folder)
        dslFactory.job(job.jobName) {
            description job.jobDescription
            disabled shouldDisable
            scm {
                git {
                    remote {
                        url job.gitUrl
                        credentials job.gitCredentialsId ?: Constants.gitCredentialsId
                    }
                    branch job.branchName
                }
            }
            triggers {
                if(job.cronTrigger){
                    scm job.cronTrigger
                }else{
                    githubPush()
                }
                customWorkspace '${JOB_NAME}'
            }
            steps {
                gradle {
                    gradleName Constants.gradleInstallationName
                    useWrapper job.useWrapper
                    switches job.switches
                    description job.buildDescription
                    tasks job.tasks
                }
                if (job.publishOverSSH) {
                    publishOverSsh {
                        server(job.publishOverSSH.serverName) {
                            job.publishOverSSH.transferSets.each {TransferSet set->
                                transferSet {
                                    sourceFiles set.sourceFiles
                                    removePrefix set.removePrefix
                                    remoteDirectory set.remoteDirectory
                                    execCommand set.execCommand
                                }
                            }

                        }
                    }
                }
                if(job.shellAction){
                    shell(job.shellAction.commands)
                }
            }
            publishers {
                if (job.jUnitResults) {
                    archiveJunit(job.jUnitResults){
                        allowEmptyResults true
                    }
                }
                if(job.coberturaResults) {
                    //CoberturaContext coberturaContext = new CoberturaContext()
                    cobertura(job.coberturaResults, )
                }

                publishBuild {
                    setDiscardOldBuilds(true)
                    logRotator(-1,-1,-1,1)
                }
                
            }
            wrappers {
                if(job.deployToArtifactory){
                    CustomConfigCreator.artifactoryGradlePlugin(job.deployToArtifactory,delegate)
                }
            }
        }
    }

}
