package com.tcfbank.is.jenkins.elements

import com.tcfbank.is.jenkins.config.Constants

/**
 * Created by t910425 on 3/30/2016.
 */
class Folder {
    String name
    String displayName

    Folder(){
        displayName ="${Constants.baseFolder}"
        name = "${Constants.baseFolder}"
    }
    Folder(String subFolder){
        displayName = "${subFolder}"
        name = "${Constants.baseFolder}/${displayName}"
    }
}
