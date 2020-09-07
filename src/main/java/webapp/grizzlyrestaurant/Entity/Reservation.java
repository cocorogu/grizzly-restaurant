package webapp.grizzlyrestaurant.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date creationDate;
	
	@Column
	private String people;
	
	@Column
	private String startHour;
	
	@Column
	private String endHour;
	
	@Column
	private String day;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	
}
