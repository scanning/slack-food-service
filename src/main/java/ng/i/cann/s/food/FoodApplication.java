package ng.i.cann.s.food;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import ng.i.cann.s.food.config.FoodApplicationConfiguration;
import ng.i.cann.s.food.config.MongoDbConfiguration;
import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.resources.FoodResource;
import ng.i.cann.s.food.resources.MenuResource;
import ng.i.cann.s.food.resources.OrderResource;
import ng.i.cann.s.food.resources.OrdersResource;
import ng.i.cann.s.food.state.IMenuState;
import ng.i.cann.s.food.state.IOrderState;
import ng.i.cann.s.food.state.IOrdersState;
import ng.i.cann.s.food.state.mongo.MenuStateMongoImpl;
import ng.i.cann.s.food.state.mongo.MongoManaged;
import ng.i.cann.s.food.state.mongo.OrderStateMongoImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.Resources;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * The Slack food application.
 * 
 * @author scanning
 *
 */
public class FoodApplication extends Application<FoodApplicationConfiguration> {

	private final static Logger log = LoggerFactory.getLogger(FoodApplication.class);

	public static void main(String[] args) throws Exception {
		new FoodApplication().run(args);
	}

	@Override
	public String getName() {
		return "Food Service";
	}

	@Override
	public void initialize(Bootstrap<FoodApplicationConfiguration> bootstrap) {

	}

	@Override
	public void run(FoodApplicationConfiguration configuration, Environment environment) throws Exception {

		log.info("Starting food application");
		log.info("Default charset: {}", Charset.defaultCharset().name());
		log.info("Configuration:");

		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer().withFeatures(SerializationFeature.INDENT_OUTPUT);
		String json = writer.writeValueAsString(configuration);

		LineNumberReader lnr = new LineNumberReader(new StringReader(json));

		String line;
		for (line = lnr.readLine(); line != null; line = lnr.readLine()) {
			log.info("    {}", line);
		}

		List<Menu> menus = Collections.emptyList();
		URL menuUrl = Resources.getResource("menus.json");
		try {
			ObjectMapper om = new ObjectMapper();
			ObjectReader or = om.reader(Menu.class);
			MappingIterator<Menu> iter = or.readValues(menuUrl);
			menus = iter.readAll();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		log.info("Creating Mongo objects");
		MongoDbConfiguration mongoConfig = configuration.getMongo();
		MongoClientURI uri = new MongoClientURI(mongoConfig.getURI());
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());

		MongoManaged mongoManaged = new MongoManaged(client);
		environment.lifecycle().manage(mongoManaged);

		log.info("Creating state objects");
		IMenuState menuState = new MenuStateMongoImpl(db);
		OrderStateMongoImpl orderStateMongoImpl = new OrderStateMongoImpl(db);

		IOrderState orderState = orderStateMongoImpl;
		IOrdersState ordersState = orderStateMongoImpl;

		log.info("Setting up menu resource");
		MenuResource menuResource = new MenuResource(menus, menuState);
		environment.jersey().register(menuResource);

		log.info("Setting up order resource");
		OrderResource orderResource = new OrderResource(menus, orderState, menuState);
		environment.jersey().register(orderResource);

		log.info("Setting up orders resource");
		OrdersResource ordersResource = new OrdersResource(menus, ordersState, menuState);
		environment.jersey().register(ordersResource);

		log.info("Setting up food resource");
		FoodResource foodResource = new FoodResource(menus, menuState, orderState, ordersState);
		environment.jersey().register(foodResource);

		log.info("Initialization complete");
	}
}
