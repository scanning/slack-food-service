package ng.i.cann.s.food.config;

import io.dropwizard.Configuration;

/**
 * Configuration for the Slack food application.
 * 
 * @author scanning
 *
 */
public class FoodApplicationConfiguration extends Configuration {
	
	private SlackConfiguration slack = new SlackConfiguration();
	
	public SlackConfiguration getSlack() {
		return slack;
	}

}
