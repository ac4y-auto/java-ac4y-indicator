package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IndicatorBalanceHistoryList extends Ac4yNoId {
	
	public IndicatorBalanceHistoryList() {
		setIndicatorBalanceHistory(new ArrayList<IndicatorBalanceHistory>());
	}
	
	protected List<IndicatorBalanceHistory> indicatorBalanceHistory;

	public List<IndicatorBalanceHistory> getIndicatorBalanceHistory() {
		return indicatorBalanceHistory;
	}

	public void setIndicatorBalanceHistory(List<IndicatorBalanceHistory> indicatorBalanceHistory) {
		this.indicatorBalanceHistory = indicatorBalanceHistory;
	}



}
