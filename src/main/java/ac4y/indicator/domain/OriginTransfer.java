package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class OriginTransfer extends Ac4yNoId {
	
	public OriginTransfer() {
		
	}
	
	public OriginTransfer(
			 int id
			 ,String plus
			 ,String minus
			 ,Date date
			 ,Date timestamp
			 ,float amount
			) {
		
		setId(id);
		setPlus(plus);
		setMinus(minus);
		setDate(date);
		setTimestamp(timestamp);
		setAmount(amount);
		
	}
	
	private int id;
	protected Date date;
	protected Date timestamp;
	protected String plus;
	protected String minus;
	protected float amount;

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

	public String getPlus() {
		return plus;
	}
	public void setPlus(String plus) {
		this.plus = plus;
	}
	public String getMinus() {
		return minus;
	}
	public void setMinus(String minus) {
		this.minus = minus;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}