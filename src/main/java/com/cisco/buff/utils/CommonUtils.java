package com.cisco.buff.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cisco.buff.response.ServiceError;
import com.cisco.buff.response.ServiceResponse;
import com.cisco.buff.response.ServiceResponseWrapper;



@Component
public class CommonUtils {

	/**
	 * 
	 */
	public  String getCurrentDate() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);

	}

	/**
	 * @param refrenceId
	 * @param requesPayload
	 * @param message
	 * @return
	 */
	public ServiceResponseWrapper<Object> getErrorResponseMessage(String refrenceId, String errorMessage) {
		ServiceResponseWrapper<Object> wrapper = new ServiceResponseWrapper<>();
		wrapper.setStatus(ServiceResponse.FAILURE_STATUS);
		wrapper.setClientTrxId(refrenceId);
		wrapper.setErrors(getErrorCode(errorMessage));
		return wrapper;
	}

	/**
	 * @param errorMessage
	 * @return
	 */
	private ServiceError getErrorCode(String errorMessage) {
		
		return BuffAPIErrorCode.getEfforCodeMessage(errorMessage);
	}

	/**
	 * @param clientTrxId
	 * @return
	 */
	public String getRefrenceId(String clientTrxId) {

		UUID uuid = UUID.randomUUID();

		String refrenceId = ( StringUtils.isNotBlank(refrenceId = clientTrxId)) ? refrenceId : uuid.toString();
		return refrenceId;
	}
	
	

  }
