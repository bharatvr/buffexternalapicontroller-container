package com.cisco.buff.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceResponseWrapper;
import com.cisco.buff.service.LogExecutionTime;
import com.cisco.buff.service.LoggerService;
import com.cisco.buff.service.MonicaServiceCall;
import com.cisco.buff.utils.BuffServiceException;
import com.cisco.buff.utils.CommonUtils;

@RestController
public class SCMIntergationController {
	private static final Logger logger = LoggerFactory.getLogger(SCMIntergationController.class);

	@Autowired
	MonicaServiceCall monicaServiceCall;

	@Autowired
	LoggerService loggerService;
	
	@Autowired
	CommonUtils commonUtils;

	@LogExecutionTime
	@PostMapping(value = "/v1/suggestions")
	public Object suggestionsKAV1(@RequestBody RequesPayload requesPayload, HttpServletRequest request) {
		Date startDate = new Date();
		ServiceResponseWrapper<Object> wrapper = new ServiceResponseWrapper<>();

		String refrenceId = commonUtils.getRefrenceId(requesPayload.getClientTrxId());
		try {
			logger.debug("Request to get KA suggestions");

			wrapper = monicaServiceCall.getMonicaQAResponse(requesPayload, refrenceId);
			return wrapper;
		}

		catch (BuffServiceException buffExp) {
			logger.error("Exception while calling getMonicaQAResponse :  {} ", buffExp.toString());
			wrapper = commonUtils.getErrorResponseMessage(refrenceId, buffExp.getMessage());
			return wrapper;

		} finally {
			Date resDate = new Date();
			long diffInMillies = Math.abs(resDate.getTime() - startDate.getTime());
            logger.info("Elapsed time for clientTrxId : {} :  {}", refrenceId, diffInMillies );
			loggerService.logResponse(refrenceId, wrapper, requesPayload, startDate, resDate, diffInMillies);
		}

	}

}
