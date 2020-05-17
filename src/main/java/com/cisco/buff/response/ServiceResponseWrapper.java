/**
 * 
 */
package com.cisco.buff.response;

/**
 * Response wrapper class for all buff service calls
 * 
 * @author bharatsi
 *
 */
public class ServiceResponseWrapper<T> {
	
	
	public ServiceResponseWrapper() {
		this.setResponseStatus(new ServiceResponse());
	}

	private T responseBody;
	private ServiceResponse responseStatus;
	private  String clientTrxId;

	/**
	 * @return the responseBody
	 */
	public T getResponseBody() {
		return responseBody;
	}

	/**
	 * @param responseBody
	 *            the responseBody to set
	 */
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}

	/**
	 * @return the responseStatus
	 */
	public ServiceResponse getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @param responseStatus
	 *            the responseStatus to set
	 */
	public void setResponseStatus(ServiceResponse responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	public void setStatus(String status) {
		responseStatus.setStatus(status);
	}
	public void setErrors(ServiceError errors) {
		responseStatus.setErrors(errors);
	}

	/**
	 * @return the clientTrxId
	 */
	public String getClientTrxId() {
		return clientTrxId;
	}

	/**
	 * @param clientTrxId the clientTrxId to set
	 */
	public void setClientTrxId(String clientTrxId) {
		this.clientTrxId = clientTrxId;
	}
}
