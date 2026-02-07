package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class Ac4yIndicatorTransfer extends Ac4yNoId {
	
	public Ac4yIndicatorTransfer() {
		
	}
	
	public Ac4yIndicatorTransfer(
			 int id
			 ,String plus
			 ,String minus
			 ,Date date
			 ,Date timestamp
			 ,float amount
			 ,String origin	
			) {
		
		setId(id);
		setPlus(plus);
		setMinus(minus);
		setDate(date);
		setTimestamp(timestamp);
		setAmount(amount);
		setOrigin(origin);
		
	}
	
	public Ac4yIndicatorTransfer(
			 int id
			 ,String plus
			 ,String minus
			 ,Date date
			 ,float amount
			 ,String origin	
			) {
		
		setId(id);
		setPlus(plus);
		setMinus(minus);
		setDate(date);
		setAmount(amount);
		setOrigin(origin);
		
	}
	
	public Ac4yIndicatorTransfer(
			 String plus
			 ,String minus
			 ,Date date
			 ,float amount
			 ,String origin	
			) {
		
		setDate(date);
		setPlus(plus);
		setMinus(minus);
		setAmount(amount);
		setOrigin(origin);
		
	}
	
	private int id;
	protected Date date;
	protected Date timestamp;
	protected String plus;
	protected String minus;
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