package ng.i.cann.s.food.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackConfiguration {

	@JsonProperty
	private String token;
	
	public String getToken() {
		return token;
	}
	
}
