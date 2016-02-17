package ng.i.cann.s.food.state.mongo;

import java.util.Date;

import org.mongojack.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class StateBase {

	@Id
	private String id;

	@JsonProperty
	private Date day;

	@JsonProperty
	private boolean deleted = false;

	protected StateBase() {
		this.day = MongoImplBase.getToday();
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
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
		StateBase other = (StateBase) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (deleted != other.deleted)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StateBase [day=" + day + ", deleted=" + deleted + "]";
	}

}
