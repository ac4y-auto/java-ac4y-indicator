package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import ac4y.base.Ac4yException;
import ac4y.base.database.DBConnection;
import ac4y.guid8humanid.service.Ac4yGUID8HumanIDService;
import ac4y.indicator.domain.Ac4yIndicator;
import ac4y.indicator.domain.Ac4yIndicatorAnalytics;
import ac4y.indicator.domain.Ac4yIndicatorAnalyticsList;
import ac4y.indicator.domain.Ac4yIndicatorBalanceHistoryList;
import ac4y.indicator.domain.Ac4yIndicatorTransfer;
import ac4y.indicator.domain.Ac4yIndicatorTransferList;
import ac4y.indicator.domain.IndicatorAnalyticsList;
import ac4y.indicator.domain.IndicatorBalanceHistoryList;
import ac4y.indicator.domain.IndicatorTransferList;
import ac4y.indicator.domain.OriginAnalyticsList;
import ac4y.indicator.domain.OriginTransferList;
import ac4y.indicator.persistence.Ac4yIndicatorAnalyticsDBAdapter;
import ac4y.indicator.persistence.Ac4yIndicatorBalanceHistoryDBAdapter;
import ac4y.indicator.persistence.Ac4yIndicatorTransferDBAdapter;
import ac4y.service.domain.Ac4yServiceOnDB;

import javax.xml.bind.JAXBException;

public class Ac4yIndicatorAnalyticsStandardService extends Ac4yServiceOnDB {

	Ac4yIndicatorStandardService ac4yIndicatorStandardService = new Ac4yIndicatorStandardService();
	
	public Ac4yIndicatorStandardService standard() {
		return ac4yIndicatorStandardService;
	}

	public Ac4yIndicatorAnalytics analytics(
					String aIndicator
					,Date aDate
					,float aAmount
					,boolean aNegative
					,String aOrigin
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = standard().getDBConnection();		

		Ac4yIndicatorAnalytics result = 
				new Ac4yIndicatorAnalyticsDBAdapter(
						dBConnection.getConnection()
				).insert(
					aIndicator
					,aDate
					,aAmount
					,aNegative
					,aOrigin	
					,aStorno
				);
	
		standard().maintain(
			aIndicator
			,aDate
			,result.getPlus()
			,result.getMinus()
		);
	
		dBConnection.getConnection().close();
		
		return result;
		
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
		
		storno(aId);
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(aId);
	
		dBConnection.getConnection().close();
		
	} // delete

	public void delete(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		storno(aIndicator);
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // delete
	
	public void delete(
			String aTemplateHumanID
			,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{

		storno(aTemplateHumanID, aHumanID);
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(
			new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
				aTemplateHumanID
				,aHumanID
			)
		);
	
		dBConnection.getConnection().close();
		
	} // delete
	
	/*
	public void deleteByOrigin(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		
		stornoAnalytics(aOrigin);
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).deleteByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // deleteByOrigin
*/
		
	public Ac4yIndicatorAnalytics storno(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException {
		
		Ac4yIndicatorAnalytics result = null;
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).storno(aId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // storno

	public void storno(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).storno(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // storno
	
	public void storno(
					String aTemplateHumanID
					,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).storno(
			new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
				aTemplateHumanID
				,aHumanID
			)
		);
	
		dBConnection.getConnection().close();
		
	} // storno
/*	
	public void stornoByOrigin(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		
		DBConnection dBConnection = standard().getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // stornoByOrigin
*/
	

	public int getOriginAnalyticsListCount(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		int result = -2;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).getOriginAnalyticsListCount(aOrigin);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getOriginAnalyticsListCount
	
	
	public Ac4yIndicatorTransferList getTransferList(String aWhere) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		Ac4yIndicatorTransferList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).getList(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getTransferList

	
	public int getTransferListCount(String aWhere) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		int result = -2;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).getListCount(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getTransferListCount
	
	
	
	public IndicatorTransferList getIndicatorTransferList(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		IndicatorTransferList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).getIndicatorTransferList(aIndicator);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getTransferList

	public OriginTransferList getOriginTransferList(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		OriginTransferList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).getOriginTransferList(aOrigin);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getTransferList
	
	
	public Ac4yIndicatorBalanceHistoryList getBalanceHistoryList(String aWhere) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException {
		
		Ac4yIndicatorBalanceHistoryList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getList(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getTransferList

	
	
	public Ac4yIndicatorAnalyticsList getAnalyticsList(String aWhere) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException {
		
		Ac4yIndicatorAnalyticsList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).getList(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getAnalyticsList
	
	public int getAnalyticsListCount(String aWhere) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		int result = -2;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).getListCount(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getAnalyticsListCount
	
	
	public IndicatorAnalyticsList getIndicatorAnalyticsList(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		IndicatorAnalyticsList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).getIndicatorAnalyticsList(aIndicator);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getAnalyticsList

	public int getIndicatorAnalyticsListCount(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		int result = -2;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).getIndicatorAnalyticsListCount(aIndicator);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getIndicatorAnalyticsListCount
	
	

	public OriginAnalyticsList getOriginAnalyticsList(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		OriginAnalyticsList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).getOriginAnalyticsList(aOrigin);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getAnalyticsList
		
	
} // Ac4yIndicatorAnalyticsStandardService