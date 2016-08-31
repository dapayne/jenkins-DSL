package com.tcfbank.is.jenkins.elements.subelements

import com.tcfbank.is.jenkins.config.Constants

/**
 * Created by t910425 on 3/29/2016.
 */
class ArtifactoryGradlePlugin {
    String artifactoryUrl = Constants.artifactoryUrl
    String resolveRepo
    String publishRepo
    String includePatterns = ""
    String excludePatterns = ""
}
