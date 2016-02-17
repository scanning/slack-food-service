package ng.i.cann.s.food.state.mongo;

import java.util.Date;

import org.mongojack.Id;

public abstract class StateBase {

	@Id
	private Date day;

	protected StateBase() {
		this.day = MongoImplBase.getToday();
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "StateBase [day=" + day + "]";
	}

}
