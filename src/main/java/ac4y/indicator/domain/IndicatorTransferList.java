package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IndicatorTransferList extends Ac4yNoId {
	
	public IndicatorTransferList() {
		setIndicatorTransfer(new ArrayList<IndicatorTransfer>());
	}
	
	protected List<IndicatorTransfer> indicatorTransfer;

	public List<IndicatorTransfer> getIndicatorTransfer() {
		return indicatorTransfer;
	}

	public void setIndicatorTransfer(List<IndicatorTransfer> indicatorTransfer) {
		this.indicatorTransfer = indicatorTransfer;
	}

}
