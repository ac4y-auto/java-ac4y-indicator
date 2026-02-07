package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

public class Ac4yIndicatorBalanceHistoryList extends Ac4yNoId {
	
	public Ac4yIndicatorBalanceHistoryList() {
		setAc4yIndicatorBalanceHistory(new ArrayList<Ac4yIndicatorBalanceHistory>());
	}
	
	protected List<Ac4yIndicatorBalanceHistory> ac4yIndicatorBalanceHistory;

	public List<Ac4yIndicatorBalanceHistory> getAc4yIndicatorBalanceHistory() {
		return ac4yIndicatorBalanceHistory;
	}

	public void setAc4yIndicatorBalanceHistory(List<Ac4yIndicatorBalanceHistory> ac4yIndicatorBalanceHistory) {
		this.ac4yIndicatorBalanceHistory = ac4yIndicatorBalanceHistory;
	}

}
