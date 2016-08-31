import com.tcfbank.is.jenkins.ciJobs.jobs.DefaultAPIPolicyDeployJob
import com.tcfbank.is.jenkins.ciJobs.jobs.DefaultServiceJob
import com.tcfbank.is.jenkins.ciJobs.jobs.DefaultJob
import com.tcfbank.is.jenkins.utils.JobsCreator
import com.tcfbank.is.jenkins.elements.Folder
import com.tcfbank.is.jenkins.utils.FolderCreator

boolean isDisabled = false


FolderCreator.createFolder(this, new Folder(name: "eis", displayName: "eis"));

DefaultJob jobDJ = new DefaultJob("base-job","dtsj");


JobsCreator.createGradleJob(this,jobDJ.setAsDevJob(),isDisabled)
