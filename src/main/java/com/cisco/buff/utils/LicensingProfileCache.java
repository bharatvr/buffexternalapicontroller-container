/**
 * 
 */
package com.cisco.buff.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bharatsi
 *
 */
public class LicensingProfileCache {

	private LicensingProfileCache() {

	}

	private static Map<String, String> licensingProfile = new ConcurrentHashMap<>();

	public static void loadLicensingProfile() {
	
		licensingProfile = new ConcurrentHashMap<>();
		licensingProfile.put("Traditional Licensing", "lic01");
		licensingProfile.put("Smart Licensing", "lic02");
		licensingProfile.put("Smart Account", "lic03");
		licensingProfile.put("Plug and Play Connect", "lic05");
		licensingProfile.put("My Cisco Entitlements", "lic06");
		licensingProfile.put("Enterprise Agreements", "lic04");

	}

	public static Map<String, String> getLicensingProfile() {

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.putAll(licensingProfile);
		return hm;
	}

}
