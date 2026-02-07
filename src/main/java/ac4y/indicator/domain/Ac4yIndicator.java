package ac4y.indicator.domain;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.domain.Ac4y;

@XmlRootElement
public class Ac4yIndicator extends Ac4y {
	
	public Ac4yIndicator() {
		getAc4yIdentification().getTemplate().setHumanId(getClass().getName());
	}

}