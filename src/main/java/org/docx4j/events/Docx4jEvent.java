package org.docx4j.events;


import net.engio.mbassy.bus.MBassador;

public abstract class Docx4jEvent {
	
	/* The 3 constructors are designed to facilitate the one-liner:
	 * 
	 *    Docx4jEvent.publish(bus, new Docx4jEvent( Job.MERGE, EventType.PKG_STARTED));
	 */

	private PackageIdentifier pkgIdentifier=null; // where null, the event is assumed to be job level
	public PackageIdentifier getPkgIdentifier() {
		return pkgIdentifier;
	}
	
	/**
	 * Use this to signal the start or end of a Job.
	 * 
	 * @param job
	 * @param eventType
	 */
	public Docx4jEvent(JobIdentifier job) {
		this.job = job;
		
		
	}

	/**
	 * Use this to signal the start or end of work on a specific pkg in a job.
	 * 
	 * @param job
	 * @param eventType
	 */
	public Docx4jEvent(JobIdentifier job,  PackageIdentifier pkgIdentifier) {
		this.job = job;
		this.pkgIdentifier = pkgIdentifier;
	}
	
	/**
	 * Use this to signal the start or end of work on a specific pkg,
	 * where you didn't define an overall job.
	 * 
	 * @param job
	 * @param eventType
	 */
	public Docx4jEvent(PackageIdentifier pkgIdentifier) { 
		this.job = WellKnownJobTypes.ANONYMOUS;
		this.pkgIdentifier = pkgIdentifier;
	}
	
	/**
	 * Use this to signal the start or end of a process step,
	 * where you didn't define an overall job.
	 * 
	 * @param job
	 * @param eventType
	 */
	public Docx4jEvent(PackageIdentifier pkgIdentifier, ProcessStep processStep) {
		this.job = WellKnownJobTypes.ANONYMOUS;
		this.pkgIdentifier = pkgIdentifier;
		this.processStep = processStep;
	}
	
	/**
	 * Use this to signal the start or end of a process step,
	 * on some pkg in some overall job.
	 * 
	 * @param job
	 * @param eventType
	 */
	public Docx4jEvent(JobIdentifier job,  PackageIdentifier pkgIdentifier, ProcessStep processStep) {
		this.job = job;
		this.pkgIdentifier = pkgIdentifier;
		this.processStep = processStep;
	}

	
	public static void publish(MBassador<Docx4jEvent> bus, Docx4jEvent event) {
    	if (bus!=null) {
    		bus.publish(event);
    	}				
	}
	

	private JobIdentifier job; 
	public JobIdentifier getJob() {
		return job;
	}

	private ProcessStep processStep;
	/**
	 * Retrieve the step in the process, if set.  You can create your own set of steps
	 * (for things which happen in your user code ie outside docx4j's source code)
	 * by implementing the ProcessStep interface.  
	 * @return
	 */
	public ProcessStep getProcessStep() {
		return processStep;
	}
	public void setProcessStep(ProcessStep processStep) {
		this.processStep = processStep;
	}
	
//	private OpcPackage pkg;
//	/**
//	 * Retrieve the pkg, for example if you want to get some user data you've set on it.
//	 * @return
//	 */
//	public OpcPackage getPkg() {
//		return pkg;
//	}
//	public void setPkg(OpcPackage pkg) {
//		this.pkg = pkg;
//	}

	
}
