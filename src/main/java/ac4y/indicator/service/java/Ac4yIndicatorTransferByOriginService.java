package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import ac4y.base.Ac4yException;
import ac4y.base.database.DBConnection;
import ac4y.indicator.persistence.Ac4yIndicatorTransferDBAdapter;

public class Ac4yIndicatorTransferByOriginService {

	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();

	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}
	
	public void storno(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // stornoTransferByOrigin

}	
