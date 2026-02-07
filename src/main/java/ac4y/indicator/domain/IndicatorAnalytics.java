package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class IndicatorAnalytics extends Ac4yNoId {
	
	public IndicatorAnalytics() {
		
	}
	
	public IndicatorAnalytics(
			 int id
			 ,Date date
			 ,Date timestamp
			 ,float plus
			 ,float minus
			 ,float value
			 ,boolean negative
			 ,String origin		
			 ,boolean storno
			) {
		
		setId(id);
		setDate(date);
		setTimestamp(timestamp);
		setPlus(plus);
		setMinus(minus);
		setValue(value);
		setNegative(negative);
		setOrigin(origin);
		
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
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}

	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	protected int id;
	protected Date date;
	protected Date timestamp;
	protected float plus;
	protected float minus;
	protected float value;
	protected boolean negative;
	protected int storno;
	
	public int getStorno() {
		return storno;
	}

	public void setStorno(int storno) {
		this.storno = storno;
	}

	public boolean isNegative() {
		return negative;
	}

	public void setNegative(boolean negative) {
		this.negative = negative;
	}

	protected String origin;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}