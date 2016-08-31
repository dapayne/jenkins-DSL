package com.tcfbank.is.jenkins.elements

/**
 * Created by t910425 on 3/30/2016.
 */
class View {
    String viewName = ""
    String viewDescription = ""
    List<String> associatedJobs = []
    String jobRegex = null
    Boolean recurseIntoSubDirs = false
}
