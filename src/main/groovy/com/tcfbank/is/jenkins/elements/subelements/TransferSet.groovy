package com.tcfbank.is.jenkins.elements.subelements

/**
 * Created by t910425 on 3/25/2016.
 */
class TransferSet {

    String excludeFiles
    String execCommand
    boolean execInPty = false
    long execTimeout
    boolean flattenFiles = false
    boolean makeEmptyDirs = false
    boolean noDefaultExcludes = false
    String patternSeparator
    boolean remoteDirIsDateFormat = false
    String remoteDirectory
    String removePrefix
    String sourceFiles

}
