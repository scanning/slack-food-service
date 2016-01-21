package ng.i.cann.s.food.menu;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Menu {

	private Restaurant restaurant;
	private LinkedList<MenuItem> items;

	@JsonIgnore
	private Map<String, MenuItem> itemsById;

	private Menu() {
		super();
	}

	public Menu(Restaurant restaurant, LinkedList<MenuItem> items) {
		this();
		this.restaurant = restaurant;
		this.items = items;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public LinkedList<MenuItem> getItems() {
		return items;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public void setItems(LinkedList<MenuItem> items) {
		this.items = items;
	}

	@JsonIgnore
	public MenuItem getItemById(String id) {
		if (itemsById == null) {
			itemsById = populateItems();
		}
		return itemsById.get(id);
	}

	@JsonIgnore
	private synchronized Map<String, MenuItem> populateItems() {
		Map<String, MenuItem> result = new HashMap<>();
		for (MenuItem item : items) {
			result.put(item.getId(), item);
		}
		return result;
	}

	@JsonIgnore
	public String getFormattedString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < restaurant.getName().length(); i++) {
			sb.append("-");
		}
		sb.append(System.lineSeparator());
		sb.append(restaurant.getName());
		sb.append(System.lineSeparator());
		for (int i = 0; i < restaurant.getName().length(); i++) {
			sb.append("-");
		}
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		for (MenuItem item : items) {
			sb.append(item.getFormattedString());
		}
		for (int i = 0; i < restaurant.getName().length(); i++) {
			sb.append("-");
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Menu [restaurant=" + restaurant + ", items=" + items + "]";
	}

	public static void main(String[] args) throws Exception {

		List<Menu> menus = new LinkedList<>();

		/**
		 * Yum Cha Noodle Haus
		 */
		Restaurant r = new Restaurant("Yum Cha Noodle Haus",
				"Shop G25, Hilton Surfers Paradise, 3113 Surfers Paradise Blvd, Surfers Paradise QLD 4217", "(07) 5504 7667");

		LinkedList<MenuItem> items = new LinkedList<>();
		items.add(new MenuItem("N1", "Combination Laksa Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N2a", "Roast Duck Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N2b", "Roast Duck on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N3a", "BBQ Pork Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N3b", "BBQ Pork on Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N4", "XO Prawn Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N5", "Prawn Won Ton Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N6", "Prawn Won Ton Soup", new BigDecimal(9.90)));

		items.add(new MenuItem("N7a", "Deep Fried Beancurd Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N7b", "Deep Fried Beancurd on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N8a", "Beef Brisket Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N8b", "Beef Brisket on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N9a", "Soy Chicken Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N9b", "Soy Chicken on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N10a", "Steamed Chicken Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N10b", "Steamed Chicken on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N11a", "Pan Fried Beef Fillet Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N11b", "Pan Fried Beef Fillet on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N12", "Beef Fillet with Honey & Black Pepper Sauce on Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N13", "Deep Fried Garlic Chicken Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N14a", "Curry Beef Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N14b", "Curry Beef on Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N15", "Chicken Mince on Dry Noodle", new BigDecimal(9.90)));
		items.add(new MenuItem("N16", "Beef Brisket on Dry Noodle", new BigDecimal(9.90)));
		items.add(new MenuItem("N17a", "Curry Chicken Noodle Soup", new BigDecimal(9.90)));
		items.add(new MenuItem("N17b", "Curry Chicken on Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N18", "Steamed Pork Ribs with Black Sauce on Rice", new BigDecimal(8.80)));
		items.add(new MenuItem("N19", "MAPO Tofu on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N20", "Deep Fried Chicken with Honey & Black Pepper Sauce on Rice", new BigDecimal(9.90)));

		items.add(new MenuItem("N21", "Crispy Pork Noodle Soup", new BigDecimal(9.90)));

		items.add(new MenuItem("N22", "Deep Fried Beancurd on Dry Rice Vermicelli", new BigDecimal(8.80)));
		items.add(new MenuItem("N23", "Curry Tofu on Rice", new BigDecimal(8.80)));

		items.add(new MenuItem("N24", "Plain Fried Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N25", "Roast Duck Fried Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N26", "BBQ Pork Fried Rice", new BigDecimal(9.90)));
		items.add(new MenuItem("N27", "Shrimp Fried Rice", new BigDecimal(9.90)));

		Menu m = new Menu(r, items);

		/**
		 * Wok in a box
		 */

		Restaurant wokInABox = new Restaurant("Wok in a Box - Broadbeach", "4/2791 Gold Coast highway, Broadbeach QLD 4218", "(07) 5538 4813");
		LinkedList<MenuItem> wokItems = new LinkedList<>();

		wokItems.add(new MenuItem("1", "Satay Chicken", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("2", "Singapore Noodles", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("3", "Hot & Spicy Noodles", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("4", "Seafood Mee Goreng", new BigDecimal(13.95)));
		wokItems.add(new MenuItem("5", "Beef & Black Bean", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("6", "Hokkien Mee", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("7", "Kwai Teow", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("8", "Pud Thai", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("9", "Sweet & Sour Pork", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("10", "Honey Soy Chicken with Cashews", new BigDecimal(13.95)));
		wokItems.add(new MenuItem("11", "Thai Green Chicken Curry", new BigDecimal(13.95)));
		wokItems.add(new MenuItem("12", "Teriyaki Beef", new BigDecimal(11.95)));
		wokItems.add(new MenuItem("13", "Teriyaki Chicken", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("14", "Mongolian Beef", new BigDecimal(11.95)));
		wokItems.add(new MenuItem("15", "Chicken with Chilli Basil", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("16", "Lemon Grass Chicken", new BigDecimal(11.95)));
		wokItems.add(new MenuItem("17", "Special Friend Rice", new BigDecimal(11.95)));
		wokItems.add(new MenuItem("18", "Seafood Nasi Goreng", new BigDecimal(13.95)));
		wokItems.add(new MenuItem("19", "Nasi Goreng", new BigDecimal(12.95)));
		wokItems.add(new MenuItem("20", "Teriyaki Chicken Bento", new BigDecimal(11.50)));
		wokItems.add(new MenuItem("21", "Katsu Chicken Bento", new BigDecimal(12.50)));
		wokItems.add(new MenuItem("22", "Combination Noodle Soup", new BigDecimal(10.95)));
		wokItems.add(new MenuItem("23", "Chicken Laksa Soup", new BigDecimal(12.50)));
		wokItems.add(new MenuItem("24", "Seafood Laksa Soup", new BigDecimal(13.50)));

		Menu wokInABoxMenu = new Menu(wokInABox, wokItems);

		menus.add(m);
		menus.add(wokInABoxMenu);

		ObjectMapper om = new ObjectMapper();
		System.out.println(om.writeValueAsString(menus));

	}
}
