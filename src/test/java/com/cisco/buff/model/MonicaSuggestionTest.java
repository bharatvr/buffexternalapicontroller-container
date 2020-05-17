/**
 * 
 */
package com.cisco.buff.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(MonicaSuggestion.class)
public class MonicaSuggestionTest {
	
	@Test
	public void textObject() {
		
		MonicaSuggestion monicaSuggestion= new MonicaSuggestion();
		
		Double confidenceScore = new Double("20");
		String text = "Inout";
		String profile = "profile";
		String vcaID = "VCA ID";
		
		monicaSuggestion.setConfidenceScore(confidenceScore);
		monicaSuggestion.setProfile(profile);
		monicaSuggestion.setText(text);
		monicaSuggestion.setVcaID(vcaID);
		
		assertEquals(vcaID, monicaSuggestion.getVcaID());
		assertEquals(profile, monicaSuggestion.getProfile());
		assertEquals(text, monicaSuggestion.getText());
		assertEquals(confidenceScore, monicaSuggestion.getConfidenceScore());
	}

	
}
