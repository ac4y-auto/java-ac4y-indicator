package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ac4yIndicatorAnalyticsList extends Ac4yNoId {
	
	public Ac4yIndicatorAnalyticsList() {
		setAc4yIndicatorAnalytics(new ArrayList<Ac4yIndicatorAnalytics>());
	}
	
	protected List<Ac4yIndicatorAnalytics> ac4yIndicatorAnalytics;

	public List<Ac4yIndicatorAnalytics> getAc4yIndicatorAnalytics() {
		return ac4yIndicatorAnalytics;
	}

	public void setAc4yIndicatorAnalytics(List<Ac4yIndicatorAnalytics> ac4yIndicatorAnalytics) {
		this.ac4yIndicatorAnalytics = ac4yIndicatorAnalytics;
	}

}
