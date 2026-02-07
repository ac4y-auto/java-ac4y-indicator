package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import ac4y.base.Ac4yException;
import ac4y.base.database.DBConnection;
import ac4y.indicator.persistence.Ac4yIndicatorAnalyticsDBAdapter;

import javax.xml.bind.JAXBException;

public class Ac4yIndicatorAnalyticsByOriginService {

	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();
	Ac4yIndicatorAnalyticsStandardService ac4yIndicatorAnalyticsStandardService = new Ac4yIndicatorAnalyticsStandardService();
	
	public Ac4yIndicatorAnalyticsStandardService analyticsStandard() {
		return ac4yIndicatorAnalyticsStandardService;
	}
	
	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}
	
	public void delete(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		analyticsStandard().storno(aOrigin);
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).deleteByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // delete
	
	public void storno(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException {
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // storno
	
}