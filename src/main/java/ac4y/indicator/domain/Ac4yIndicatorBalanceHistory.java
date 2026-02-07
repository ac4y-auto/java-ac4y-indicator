package ac4y.indicator.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class Ac4yIndicatorBalanceHistory extends Ac4yNoId {
	
	public Ac4yIndicatorBalanceHistory() {
	}
	
	public Ac4yIndicatorBalanceHistory(
			 int id
			 ,String indicator
			 ,Date validFrom
			 ,Date validTo
			 ,float plus
			 ,float minus
			 ,float balance
			) {
		
		setId(id);
		setIndicator(indicator);
		setValidFrom(validFrom);
		setValidTo(validTo);
		setPlus(plus);
		setMinus(minus);
		setBalance(balance);
		
	}
	
	private int id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String indicator;
	private Date validFrom;
	private Date validTo;
	private float plus;
	private float minus;
	private float balance;
	
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
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
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}

}
