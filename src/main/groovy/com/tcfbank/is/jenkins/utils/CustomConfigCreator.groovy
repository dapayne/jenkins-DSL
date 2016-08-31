package com.tcfbank.is.jenkins.utils

import com.tcfbank.is.jenkins.config.Constants
import com.tcfbank.is.jenkins.elements.subelements.ArtifactoryGradlePlugin

/**
 * Created by t910425 on 3/29/2016.
 */
class CustomConfigCreator {

    static def artifactoryGradlePlugin = { ArtifactoryGradlePlugin artifactoryGradlePlugin, wrapperContext ->
        def nodeBuilder = new NodeBuilder()
        wrapperContext.wrapperNodes << nodeBuilder.'org.jfrog.hudson.gradle.ArtifactoryGradleConfigurator'(plugin: "artifactory@2.5.0") {
            deployMaven(true)
            deployIvy(false)
            deployBuildInfo(true)
            includeEnvVars(false)
            runChecks(false)
            violationRecipients()
            includePublishArtifacts(false)
            scopes()
            licenseAutoDiscover(true)
            disableLicenseAutDiscovery(false)
            ivyPattern('[organisation]/[module]/ivy-[revision].xml')
            enableIssueTrackerIntegration(false)
            aggregateBuildIssues(false)
            artifactPattern('[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]')
            notM2Compatible(false)
            artifactDeploymentPatterns{
                includePatterns(artifactoryGradlePlugin.includePatterns)
                excludePatterns(artifactoryGradlePlugin.excludePatterns)
            }
            discardOldBuilds(true)
            discardBuildArtifacts(true)
            passIdentifiedDownstream(false)
            matrixParams()
            skipInjectInitScript(false)
            allowPromotionOfNonStagedBuilds(false)
            allowBintrayPushOfNonStageBuilds(false)
            blackDuckRunChecks(false)
            blackDuckAppName()
            blackDuckAppVersion()
            filterExcludedArtifactsFromBuild(false)
            resolverDetails {
                artifactoryName(Constants.artifactoryJenkinsKey)
                artifactoryUrl(artifactoryGradlePlugin.artifactoryUrl)
                resolveReleaseRepository {
                    keyFromText(artifactoryGradlePlugin.resolveRepo)
                    keyFromSelect()
                    dynamicMode(true)
                }
            }
            details {
                artifactoryName(Constants.artifactoryJenkinsKey)
                artifactoryUrl(artifactoryGradlePlugin.artifactoryUrl)
                deployReleaseRepository {
                    keyFromText(artifactoryGradlePlugin.publishRepo)
                    keyFromSelect()
                    dynamicMode(true)
                }
            }
            deployArtifacts(true)
            envVarsPatterns {
                includePatterns()
                excludePatterns('*password*,*secret*')
            }
            aggregationBuildStatus('Released')
            blackDuckReportRecipients()
            blackDuckScopes()
            blackDuckIncludePublishedArtifacts(false)
            autoCreateMissingComponentRequests(true)
            autoDiscardStaleComponentRequests(true)
        }
    }

}
