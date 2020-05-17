package com.cisco.buff.service;

import org.springframework.stereotype.Service;

import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceResponseWrapper;
import com.cisco.buff.utils.BuffServiceException;

@Service
public interface MonicaServiceCall {

	/**
	 * @param requesPayload
	 * @return
	 * @throws BuffServiceException 
	 */
	ServiceResponseWrapper<Object> getMonicaQAResponse(RequesPayload requesPayload, String refrenceId) throws BuffServiceException;

	

}
