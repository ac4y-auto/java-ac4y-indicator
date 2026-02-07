package ac4y.indicator.domain;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ac4yIndicatorTransferList extends Ac4yNoId {
	
	public Ac4yIndicatorTransferList() {
		setAc4yIndicatorTransfer(new ArrayList<Ac4yIndicatorTransfer>());
	}
	
	protected List<Ac4yIndicatorTransfer> ac4yIndicatorTransfer;

	public List<Ac4yIndicatorTransfer> getAc4yIndicatorTransfer() {
		return ac4yIndicatorTransfer;
	}

	public void setAc4yIndicatorTransfer(List<Ac4yIndicatorTransfer> ac4yIndicatorTransfer) {
		this.ac4yIndicatorTransfer = ac4yIndicatorTransfer;
	}


}
