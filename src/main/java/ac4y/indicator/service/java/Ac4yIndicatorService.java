package ac4y.indicator.service.java;

public class Ac4yIndicatorService {

	Ac4yIndicatorByOriginService ac4yIndicatorByOriginService = new Ac4yIndicatorByOriginService();
	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();
	Ac4yIndicatorAnalyticsService ac4yIndicatorAnalyticsService = new Ac4yIndicatorAnalyticsService();
	Ac4yIndicatorTransferService ac4yIndicatorTransferService = new Ac4yIndicatorTransferService();
	
	public Ac4yIndicatorTransferService transfer() {
		return ac4yIndicatorTransferService;
	}

	public Ac4yIndicatorAnalyticsService analytics() {
		return ac4yIndicatorAnalyticsService;
	}

	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}

	public Ac4yIndicatorByOriginService byOrigin() {
		return ac4yIndicatorByOriginService;
	}
	
} // Ac4yIndicatorService	