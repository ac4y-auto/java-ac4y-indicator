package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import ac4y.base.Ac4yException;
import ac4y.base.database.DBConnection;
import ac4y.indicator.persistence.Ac4yIndicatorAnalyticsDBAdapter;
import ac4y.indicator.persistence.Ac4yIndicatorTransferDBAdapter;

import javax.xml.bind.JAXBException;

public class Ac4yIndicatorByOriginService {

	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();
	
	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}
	
	public void deleteAnalytics(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException {
		
		standard().stornoAnalytics(aOrigin);
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).deleteByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // deleteAnalytics
	
	public void stornoAnalytics(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // stornoAnalytics
	
	public void stornoTransfer(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // stornoTransfer
	
}	
