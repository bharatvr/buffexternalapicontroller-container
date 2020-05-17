package com.cisco.buff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class to convert configuration properties into java object.
 *
 * @author bharatsi
 */

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfigProperties {

	/** The env host. */
	@Value("${spring.env.type}")
	private String envType;

	/** The monica service url. */
	@Value("${spring.search.service.url}")
	private String serviceURL;

	/** The skill builder service url. */
	@Value("${spring.skill-builder.service.url}")
	private String skillBuilderServiceURL;

	/**
	 * @return the envType
	 */
	public String getEnvType() {
		return envType;
	}

	/**
	 * @param envType the envType to set
	 */
	public void setEnvType(String envType) {
		this.envType = envType;
	}

	/**
	 * @return the monicaServiceURL
	 */
	public String getServiceURL() {
		return serviceURL;
	}

	/**
	 * @param monicaServiceURL the monicaServiceURL to set
	 */
	public void setMonicaServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	/**
	 * @return the skillBuilderServiceURL
	 */
	public String getSkillBuilderServiceURL() {
		return skillBuilderServiceURL;
	}

	/**
	 * @param skillBuilderServiceURL the skillBuilderServiceURL to set
	 */
	public void setSkillBuilderServiceURL(String skillBuilderServiceURL) {
		this.skillBuilderServiceURL = skillBuilderServiceURL;
	}
	
	


}
