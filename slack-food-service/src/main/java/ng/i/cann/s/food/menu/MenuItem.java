package ng.i.cann.s.food.menu;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuItem {

	private String id;
	private String description;
	private BigDecimal price;

	private MenuItem() {
		super();
	}

	public MenuItem(String id, String description, BigDecimal price) {
		this();
		this.id = id;
		this.description = description;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@JsonIgnore
	public String getFormattedString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getId());
		sb.append(" - ");
		sb.append(getDescription());
		sb.append("\t\t");
		sb.append(NumberFormat.getCurrencyInstance().format(getPrice()));
		sb.append(System.lineSeparator());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		MenuItem other = (MenuItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", description=" + description + ", price=" + price + "]";
	}

}
