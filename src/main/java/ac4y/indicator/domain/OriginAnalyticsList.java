package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OriginAnalyticsList extends Ac4yNoId {
	
	public OriginAnalyticsList() {
		setOriginAnalytics(new ArrayList<OriginAnalytics>());
	}
	
	protected List<OriginAnalytics> originAnalytics;

	public List<OriginAnalytics> getOriginAnalytics() {
		return originAnalytics;
	}

	public void setOriginAnalytics(List<OriginAnalytics> originAnalytics) {
		this.originAnalytics = originAnalytics;
	}

}
