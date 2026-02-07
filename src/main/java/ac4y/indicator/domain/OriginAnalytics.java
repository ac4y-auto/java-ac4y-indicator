package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class OriginAnalytics extends Ac4yNoId {
	
	public OriginAnalytics() {
		
	}
	
	public OriginAnalytics(
			 int id
			 ,String indicator
			 ,Date date
			 ,Date timestamp
			 ,float plus
			 ,float minus
			 ,float value
			 ,boolean negative			
			) {
		
		setId(id);
		setIndicator(indicator);
		setDate(date);
		setTimestamp(timestamp);
		setPlus(plus);
		setMinus(minus);
		setValue(value);
		setNegative(negative);
		
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

	protected int id;
	protected String indicator;
	protected Date date;
	protected Date timestamp;
	protected float plus;
	protected float minus;
	protected float value;
	protected boolean negative;
	
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