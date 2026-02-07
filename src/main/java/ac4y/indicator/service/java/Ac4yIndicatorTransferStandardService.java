package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import ac4y.base.Ac4yException;
import ac4y.base.database.DBConnection;
import ac4y.guid8humanid.service.Ac4yGUID8HumanIDService;
import ac4y.indicator.domain.Ac4yIndicatorTransfer;
import ac4y.indicator.persistence.Ac4yIndicatorTransferDBAdapter;
import ac4y.service.domain.Ac4yServiceOnDB;

public class Ac4yIndicatorTransferStandardService extends Ac4yServiceOnDB {

	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();

	Ac4yIndicatorAnalyticsService ac4yIndicatorAnalyticsService = new Ac4yIndicatorAnalyticsService();
	
	public Ac4yIndicatorAnalyticsService analytics() {
		return ac4yIndicatorAnalyticsService;
	}

	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}

	public Ac4yIndicatorTransfer transfer(
					String aPlus
					,String aMinus
					,Date aDate
					,float aAmount
					,String aOrigin
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = getDBConnection();		
		
		Ac4yIndicatorTransfer ac4yIndicatorTransfer = 
				new Ac4yIndicatorTransferDBAdapter(
						dBConnection.getConnection()
				).insert(
					aPlus
					,aMinus
					,aDate
					,aAmount
					,aOrigin
					,aStorno
				);
		
		analytics().analytics(aPlus, aDate, aAmount, false, aOrigin, aStorno);
		analytics().analytics(aMinus, aDate, aAmount, true, aOrigin, aStorno);
		
		dBConnection.getConnection().close();
		
		return ac4yIndicatorTransfer;
		
		} // transfer

	public Ac4yIndicatorTransfer transfer(
					String aPlusTemplateHumanID
					,String aPlusHumanID
					,String aMinusTemplateHumanID
					,String aMinusHumanID
					,Date aDate
					,float aAmount
					,String aOrigin
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		return
			transfer(
				new Ac4yGUID8HumanIDService().setByHumanIDs(
					aPlusTemplateHumanID
					,aPlusHumanID
				).getGUID()
				,new Ac4yGUID8HumanIDService().setByHumanIDs(
					aMinusTemplateHumanID
					,aMinusHumanID
				).getGUID()
				,aDate
				,aAmount
				,aOrigin
				,aStorno
			);
		
	} // transfer

	
	public Ac4yIndicatorTransfer storno(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		Ac4yIndicatorTransfer result = null;
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		result = new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).storno(aId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // storno

	public void storno(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).storno(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // storno
	
	public void storno(
					String aTemplateHumanID
					,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).storno(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
			);
		
		dBConnection.getConnection().close();
		
	} // storno
	
}	