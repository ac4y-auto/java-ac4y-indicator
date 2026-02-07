package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IndicatorAnalyticsList extends Ac4yNoId {
	
	public IndicatorAnalyticsList() {
		setIndicatorAnalytics(new ArrayList<IndicatorAnalytics>());
	}
	
	protected List<IndicatorAnalytics> indicatorAnalytics;

	public List<IndicatorAnalytics> getIndicatorAnalytics() {
		return indicatorAnalytics;
	}

	public void setIndicatorAnalytics(List<IndicatorAnalytics> indicatorAnalytics) {
		this.indicatorAnalytics = indicatorAnalytics;
	}



}
