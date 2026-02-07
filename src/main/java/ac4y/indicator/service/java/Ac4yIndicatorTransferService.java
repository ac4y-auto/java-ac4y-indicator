package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import ac4y.base.Ac4yException;
import ac4y.indicator.domain.Ac4yIndicatorTransfer;

public class Ac4yIndicatorTransferService {

	Ac4yIndicatorTransferStandardService ac4yIndicatorTransferStandardService = new Ac4yIndicatorTransferStandardService();	
	Ac4yIndicatorTransferByOriginService ac4yIndicatorTransferByOriginService = new Ac4yIndicatorTransferByOriginService();
	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();
	Ac4yIndicatorAnalyticsService ac4yIndicatorAnalyticsService = new Ac4yIndicatorAnalyticsService();

	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}	
	public Ac4yIndicatorTransferStandardService transferStandard() {
		return ac4yIndicatorTransferStandardService;
	}

	public Ac4yIndicatorTransferByOriginService byOrigin() {
		return ac4yIndicatorTransferByOriginService;
	}
	
	public Ac4yIndicatorTransfer transfer(
					String aPlus
					,String aMinus
					,Date aDate
					,float aAmount
					,String aOrigin
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		if (aPlus == null)
			throw new Ac4yException("plus is empty!");
		
		if (aMinus == null)
			throw new Ac4yException("minus is empty!");
		
		if (aDate == null)
			throw new Ac4yException("date is empty!");
		
		return transferStandard().transfer(aPlus, aMinus, aDate, aAmount, aOrigin, aStorno);
		
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
		
		return transferStandard().transfer(aPlusTemplateHumanID, aPlusHumanID, aMinusTemplateHumanID, aMinusHumanID, aDate, aAmount, aOrigin, aStorno);

	} // transfer

	
	public Ac4yIndicatorTransfer storno(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		return transferStandard().storno(aId);
		
	} // storno

	public void storno(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{

		transferStandard().storno(aIndicator);
		
	} // storno
	
	public void storno(
					String aTemplateHumanID
					,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		transferStandard().storno(aTemplateHumanID, aHumanID);
		
	} // storno
		
	public void stornoTransferByOrigin(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{

		byOrigin().storno(aOrigin);
		
	} // stornoTransferByOrigin
	
}	
