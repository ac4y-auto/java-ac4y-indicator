package ac4y.indicator.service.java;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import ac4y.base.Ac4yContext;
import ac4y.base.Ac4yException;
import ac4y.base.database.DBConnection;
import ac4y.guid8humanid.service.Ac4yGUID8HumanIDService;
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

public class Ac4yIndicatorStandardService extends Ac4yServiceOnDB {

	public Ac4yIndicatorAnalytics analytics(
					String aIndicator
					,Date aDate
					,float aAmount
					,boolean aNegative
					,String aOrigin		
					,boolean aStorno
				) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = getDBConnection();		

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
	
		maintain(
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
		
		analytics(aPlus, aDate, aAmount, false, aOrigin, aStorno);
		analytics(aMinus, aDate, aAmount, true, aOrigin, aStorno);
		
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

	
	public void maintain(
			String aIndicator
			, Date aDate
			, float aPlus
			, float aMinus
		) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(
			dBConnection.getConnection()
		).maintain(
			aIndicator
			,aDate
			,aPlus
			,aMinus
		);
	
		dBConnection.getConnection().close();
		
	} // maintain

	public void maintain(
			String aTemplateHumanID
			,String aHumanID
			,Date aDate
			,float aPlus
			,float aMinus
		) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		maintain(
			new Ac4yGUID8HumanIDService().setByHumanIDs(
				aTemplateHumanID
				,aHumanID
			).getGUID()
			,aDate
			,aPlus
			,aMinus
		);
		
	} // maintain

	
	public void deleteAnalytics(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		stornoAnalytics(aId);
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(aId);
	
		dBConnection.getConnection().close();
		
	} // deleteAnalytics

	public void deleteAnalytics(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		stornoAnalytics(aIndicator);
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // deleteAnalytics
	
	public void deleteAnalytics(
			String aTemplateHumanID
			,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{

		stornoAnalytics(aTemplateHumanID, aHumanID);
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(
			new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
				aTemplateHumanID
				,aHumanID
			)
		);
	
		dBConnection.getConnection().close();
		
	} // deleteAnalytics
	
	public void deleteAnalyticsByOrigin(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		stornoAnalytics(aOrigin);
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).deleteByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // deleteAnalyticsByOrigin

	
	public void deleteIndicatorBalanceHistory(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{

		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(
			new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).get(aId).getIndicator()
		);
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).delete(aId);
	
		dBConnection.getConnection().close();
		
	} // deleteIndicatorBalanceHistory

	public void deleteIndicatorBalanceHistory(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(aIndicator);
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).delete(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // deleteIndicatorBalanceHistory
	
	public void deleteIndicatorBalanceHistory(
			String aTemplateHumanID
			,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).delete(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
			);		
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).delete(
			new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
				aTemplateHumanID
				,aHumanID
			)
		);
	
		dBConnection.getConnection().close();
		
	} // deleteIndicatorBalanceHistory
	
	
	public Ac4yIndicatorAnalytics stornoAnalytics(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException {
		
		Ac4yIndicatorAnalytics result = null;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).storno(aId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // stornoAnalytics

	public void stornoAnalytics(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).storno(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // stornoAnalytics
	
	public void stornoAnalytics(
					String aTemplateHumanID
					,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).storno(
			new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
				aTemplateHumanID
				,aHumanID
			)
		);
	
		dBConnection.getConnection().close();
		
	} // stornoAnalytics
	
	public void stornoAnalyticsByOrigin(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException, JAXBException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorAnalyticsDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // stornoAnalyticsByOrigin
	

	
	public Ac4yIndicatorTransfer stornoTransfer(int aId) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		Ac4yIndicatorTransfer result = null;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).storno(aId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // stornoTransfer

	public void stornoTransfer(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).storno(aIndicator);
	
		dBConnection.getConnection().close();
		
	} // stornoTransfer
	
	public void stornoTransfer(
					String aTemplateHumanID
					,String aHumanID) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).storno(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
			);
		
		dBConnection.getConnection().close();
		
	} // stornoTransfer
		
	public void stornoTransferByOrigin(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{
		
		DBConnection dBConnection = getDBConnection();		
		
		new Ac4yIndicatorTransferDBAdapter(dBConnection.getConnection()).stornoByOrigin(aOrigin);
	
		dBConnection.getConnection().close();
		
	} // stornoTransferByOrigin
	
	
	public float getBalance(String aIndicator, Date aDate) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		  
		float result = -2;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getBalance(aIndicator, aDate);
	
		dBConnection.getConnection().close();
	
		return result;
		
	} // getBalance

	public float getBalance(String aIndicator) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		  
		float result = -2;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getBalance(aIndicator, new Date());
	
		dBConnection.getConnection().close();
	
		return result;
		
	} // getBalance

	public float getBalanceByHumanIDs(
					String aTemplateHumanID
					,String aHumanID
					,Date aDate) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		  
		return
			getBalance(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
				,aDate
			);
		
	} // getBalanceByHumanIDs
	
	public float getPlus(String aIndicator, Date aDate) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		  
		float result = -2;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getPlus(aIndicator, aDate);
	
		dBConnection.getConnection().close();
	
		return result;
		
	} // getPlus
		
	public float getPlusByHumanIDs(
					String aTemplateHumanID
					,String aHumanID
					,Date aDate) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		  
		return
			getPlus(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
				,aDate
			);
		
	} // getPlus
	
	public float getPeriodPlus(String aIndicator, Date aFrom, Date aTo) throws ClassNotFoundException, SQLException, IOException, Ac4yException{	
		  
		float result = -2;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getPeriodPlus(aIndicator, aFrom, aTo);
	
		dBConnection.getConnection().close();
	
		return result;
		
	} // getPeriodPlus
		
	public float getPeriodPlusByHumanIDs(
					String aTemplateHumanID
					,String aHumanID
					,Date aFrom
					,Date aTo) throws ClassNotFoundException, SQLException, IOException, Ac4yException{	
		  
		return
			getPeriodPlus(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
				,aFrom
				,aTo
			);
		
	} // getPeriodPlusByHumanIDs
	
	public float getMinus(String aIndicator, Date aDate) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		  
		float result = -2;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getMinus(aIndicator, aDate);
	
		dBConnection.getConnection().close();
	
		return result;
		
	} // getMinus

	public float getMinusByHumanIDs(
					String aTemplateHumanID
					,String aHumanID
					,Date aDate) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException{	
		return
			getMinus(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
				,aDate
			);
		
	} // getMinus

	public float getPeriodMinus(String aIndicator, Date aFrom, Date aTo) throws ClassNotFoundException, SQLException, IOException, Ac4yException{	
		  
		float result = -2;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getPeriodMinus(aIndicator, aFrom, aTo);
	
		dBConnection.getConnection().close();
	
		return result;
		
	} // getPeriodMinus
	
	public float getPeriodMinusByHumanIDs(
					String aTemplateHumanID
					,String aHumanID
					,Date aFrom
					,Date aTo) throws ClassNotFoundException, SQLException, IOException, Ac4yException{	
		return
			getPeriodMinus(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
				,aFrom
				,aTo
			);
		
	} // getPeriodMinusByHumanIDs

	public float getPeriodBalance(String aIndicator, Date aFrom, Date aTo) throws ClassNotFoundException, SQLException, IOException, Ac4yException{	
		  
		return
			getPeriodPlus(aIndicator, aFrom, aTo)
			-
			getPeriodMinus(aIndicator, aFrom, aTo)
			;
		
	} // getPeriodBalance

	public float getPeriodBalanceByHumanIDs(
					String aTemplateHumanID
					,String aHumanID
					,Date aFrom
					,Date aTo) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		
		return
			getPeriodBalance(
				new Ac4yGUID8HumanIDService().getGUIDByHumanIDs(
					aTemplateHumanID
					,aHumanID
				)
				,aFrom
				,aTo
			);
		
	} // getPeriodBalanceByHumanIDs

	
	public int getBalanceHistoryListCount(String aWhere) throws ClassNotFoundException, SQLException, IOException, Ac4yException {
		
		int result = -2;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getListCount(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getBalanceHistoryListCount
	


	public IndicatorBalanceHistoryList getIndicatorBalanceHistoryList(String aOrigin) throws ClassNotFoundException, SQLException, IOException, Ac4yException, ParseException {
		
		IndicatorBalanceHistoryList result = null;
		
		DBConnection dBConnection = getDBConnection();
		
		result = new Ac4yIndicatorBalanceHistoryDBAdapter(dBConnection.getConnection()).getIndicatorBalanceHistoryList(aOrigin);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // getTransferList
	
}