package com.tcfbank.is.jenkins.utils

import com.tcfbank.is.jenkins.elements.View
import javaposse.jobdsl.dsl.DslFactory

/**
 * Created by t910425 on 3/25/2016.
 */
class ViewCreator {
    static def createView(DslFactory dslFactory, View view) {
        dslFactory.listView(view.viewName) {
            description(view.viewDescription)

            jobs {
                if (view.associatedJobs) {
                    names(view.associatedJobs as String[])
                }
                if (view.jobRegex) {
                    regex(view.jobRegex)
                }
            }
            if(view.recurseIntoSubDirs){
                recurse(view.recurseIntoSubDirs)
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}
