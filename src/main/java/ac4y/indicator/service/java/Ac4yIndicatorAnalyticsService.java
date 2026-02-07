package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import ac4y.base.Ac4yException;
import ac4y.guid8humanid.service.Ac4yGUID8HumanIDService;
import ac4y.indicator.domain.Ac4yIndicatorAnalytics;

import javax.xml.bind.JAXBException;

public class Ac4yIndicatorAnalyticsService {

	Ac4yIndicatorAnalyticsByOriginService ac4yIndicatorAnalyticsByOriginService = new Ac4yIndicatorAnalyticsByOriginService();
	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();
	Ac4yIndicatorAnalyticsStandardService ac4yIndicatorAnalyticsStandardService = new Ac4yIndicatorAnalyticsStandardService();
	
	public Ac4yIndicatorAnalyticsStandardService analyticsStandard() {
		return ac4yIndicatorAnalyticsStandardService;
	}

	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}

	public Ac4yIndicatorAnalyticsByOriginService byOrigin() {
		return ac4yIndicatorAnalyticsByOriginService;
	}
/*
	public DBConnection getDBConnection() throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		return new DBConnection(this.getClass().getName()+".properties");
	} // getDBConnection

	*/

	public Ac4yIndicatorAnalytics analytics(
					String aIndicator
					,Date aDate
					,float aAmount
					,boolean aNegative
					,String aOrigin
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		return analyticsStandard().analytics(aIndicator, aDate, aAmount, aNegative, aOrigin, aStorno);
		
	} // analytics
	
	public Ac4yIndicatorAnalytics analytics(
					String aTemplateHumanID
					,String aHumanID
					,Date aDate
					,float aAmount
					,boolean aMinusValue
					,String aOrigin
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		return
			analytics(
				new Ac4yGUID8HumanIDService().setByHumanIDs(
					aTemplateHumanID
					,aHumanID
				).getGUID()
				,aDate
				,aAmount
				,aMinusValue
				,aOrigin
				,aStorno
			);
		
	} // analytics
		
	public void delete(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		analyticsStandard().delete(aId);
		
	} // delete

	public void delete(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		analyticsStandard().delete(aIndicator);
		
	} // delete
	
	public void delete(
			String aTemplateHumanID
			,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{

		analyticsStandard().delete(aTemplateHumanID, aHumanID);
		
	} // delete
	
	public Ac4yIndicatorAnalytics storno(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException {

		return analyticsStandard().storno(aId);
		
	} // storno

	public void storno(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		analyticsStandard().storno(aIndicator);
		
	} // storno
	
	public void storno(
					String aTemplateHumanID
					,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		analyticsStandard().storno(aTemplateHumanID, aHumanID);

	} // storno

}