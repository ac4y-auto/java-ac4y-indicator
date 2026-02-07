package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class Ac4yIndicatorAnalytics extends Ac4yNoId {
	
	public Ac4yIndicatorAnalytics() {
		
	}
	
	public Ac4yIndicatorAnalytics(
			 int id
			 ,String indicator
			 ,Date date
			 ,Date timestamp
			 ,float plus
			 ,float minus
			 ,float amount
			 ,boolean negative
			 ,String origin
			 ,boolean storno
			) {
		
		setId(id);
		setIndicator(indicator);
		setDate(date);
		setTimestamp(timestamp);
		setPlus(plus);
		setMinus(minus);
		setAmount(amount);
		setNegative(negative);
		setOrigin(origin);
		setStorno(storno);
		
	}
	
	public Ac4yIndicatorAnalytics(
			 String indicator
			 ,Date date
			 ,float plus
			 ,float minus
			 ,float amount
			 ,boolean negative
			 ,String origin
			 ,boolean storno
			) {
		
		setIndicator(indicator);
		setDate(date);
		setPlus(plus);
		setMinus(minus);
		setAmount(amount);
		setNegative(negative);
		setOrigin(origin);
		setStorno(storno);
		
	}

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
	public float getPlus() {
		return plus;
	}
	public void setPlus(float plus) {
		this.plus = plus;
	}
	public float getMinus() {
		return minus;
	}
	public void setMinus(float minus) {
		this.minus = minus;
	}

	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	protected int id;
	protected String indicator;
	protected Date date;
	protected Date timestamp;
	protected float plus;
	protected float minus;
	protected float amount;
	protected boolean negative;
	protected String origin;	
	protected boolean storno;
	
	public float getAmount() {
		return amount;
	}

	public boolean isStorno() {
		return storno;
	}

	public void setStorno(boolean storno) {
		this.storno = storno;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public boolean isNegative() {
		return negative;
	}

	public void setNegative(boolean negative) {
		this.negative = negative;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

}