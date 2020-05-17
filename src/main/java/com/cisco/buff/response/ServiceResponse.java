/**
 * 
 */
package com.cisco.buff.response;

/**
 * @author bharatsi
 *
 */
public class ServiceResponse {
	
	public final static String SUCCESS_STATUS = "Success";
	public final static String FAILURE_STATUS = "Failure";
	
	private String status;
	private ServiceError errors;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the errors
	 */
	public ServiceError getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(ServiceError errors) {
		this.errors = errors;
	}

	
	
	
}
