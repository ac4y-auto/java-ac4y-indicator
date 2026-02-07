package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OriginTransferList extends Ac4yNoId {
	
	public OriginTransferList() {
		setOriginTransfer(new ArrayList<OriginTransfer>());
	}
	
	protected List<OriginTransfer> originTransfer;

	public List<OriginTransfer> getOriginTransfer() {
		return originTransfer;
	}

	public void setOriginTransfer(List<OriginTransfer> originTransfer) {
		this.originTransfer = originTransfer;
	}

}
