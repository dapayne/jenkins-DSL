package com.tcfbank.is.jenkins.elements

import com.tcfbank.is.jenkins.elements.subelements.TransferSet

/**
 * Created by t910425 on 3/25/2016.
 */
class PublishOverSSHJob extends Job{

    String gitUrl
    String gitCredentials
    String branchName
    String cronTrigger
    String serverName
    List<TransferSet> transferSets

}
