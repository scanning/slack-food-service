package ng.i.cann.s.food.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackConfiguration {

	@JsonProperty
	private String token;

	public String getToken() {
		if (token == null) {
			token = System.getenv("SLACK_TOKEN");
		}
		return token;
	}

}
