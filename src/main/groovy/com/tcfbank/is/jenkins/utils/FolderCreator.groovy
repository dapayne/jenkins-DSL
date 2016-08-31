package com.tcfbank.is.jenkins.utils

import com.tcfbank.is.jenkins.elements.Folder
import javaposse.jobdsl.dsl.DslFactory

/**
 * Created by t910425 on 3/25/2016.
 */
class FolderCreator {
    static def createFolder(DslFactory dslFactory, Folder folder) {
        dslFactory.folder(folder.name) {
            displayName(folder.displayName)
        }
    }
}
