package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class IndicatorTransfer extends Ac4yNoId {
	
	public IndicatorTransfer() {
		
	}
	
	public IndicatorTransfer(
			 int id
			 ,String oppositeIndicator
			 ,Date date
			 ,Date timestamp
			 ,float amount
			 ,String origin	
			) {
		
		setId(id);
		setOppositeIndicator(oppositeIndicator);
		setDate(date);
		setTimestamp(timestamp);
		setAmount(amount);
		setOrigin(origin);
		
	}
	
	private int id;
	protected Date date;
	protected Date timestamp;
	protected String oppositeIndicator;
	
	public String getOppositeIndicator() {
		return oppositeIndicator;
	}

	public void setOppositeIndicator(String oppositeIndicator) {
		this.oppositeIndicator = oppositeIndicator;
	}

	protected float amount;
	protected String origin;	

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}