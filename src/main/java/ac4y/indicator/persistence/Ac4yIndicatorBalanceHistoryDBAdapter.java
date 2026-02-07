package ac4y.indicator.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ac4y.base.Ac4yException;
import ac4y.indicator.domain.Ac4yIndicatorBalanceHistory;
import ac4y.indicator.domain.Ac4yIndicatorBalanceHistoryList;
import ac4y.indicator.domain.IndicatorBalanceHistory;
import ac4y.indicator.domain.IndicatorBalanceHistoryList;
import ac4y.utility.DateHandler;

public class Ac4yIndicatorBalanceHistoryDBAdapter {

//	private static Date MINDATE = new DateHandler().getDateFromString("0001-01-01");
//	private static Date MAXDATE = new DateHandler().getDateFromString("2999-12-31");
	
	public Ac4yIndicatorBalanceHistoryDBAdapter(Connection aConnection){
		setConnection(aConnection); 
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private Connection connection;

	public Ac4yIndicatorBalanceHistory get(ResultSet aResultSet) throws SQLException, ParseException {

		return 
			new Ac4yIndicatorBalanceHistory(
					aResultSet.getInt("id")
					,aResultSet.getString("indicator")
					,new DateHandler().getDateFromString(aResultSet.getString("validFrom"))
					,new DateHandler().getDateFromString(aResultSet.getString("validTo"))
					,aResultSet.getFloat("plus")
					,aResultSet.getFloat("minus")
					,aResultSet.getFloat("balance")
			);
		
	} // get
	
	public Ac4yIndicatorBalanceHistory get(String aIndicator, Date aDate) throws SQLException, Ac4yException, ParseException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");		

		if (aDate == null)
			throw new Ac4yException("date is empty!");		

		Ac4yIndicatorBalanceHistory result = null;
		
		String 	sqlStatement = 
						"SELECT"
						+ " *" 
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " indicator = '" + aIndicator + "'"
						+ " and '" + new DateHandler().getStringFromDate(aDate) + "' between"
						+ " validFrom and validTo";

		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		if (resultSet.next())
			result = get(resultSet);
		else
			throw new Ac4yException("such indicator balance history does not exist!");			
		
		resultSet.close();

		statement.close();
		
		return result;
		
	} // get

	public Ac4yIndicatorBalanceHistory get(int aId) throws SQLException, Ac4yException, ParseException{

		Ac4yIndicatorBalanceHistory result = null;
		
		String 	sqlStatement = 
						"SELECT"
						+ " *" 
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " id = " + aId
						;

		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		if (resultSet.next())
			result = get(resultSet);
		else
			throw new Ac4yException("such analitics does not exist!");	
		
		resultSet.close();

		statement.close();
		
		return result;
		
	} // get

	public boolean isExist(String aIndicator, Date aDate) throws SQLException, Ac4yException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");		

		if (aDate == null)
			throw new Ac4yException("date is empty!");	
		
		boolean isExists = false;
		
		String 	sqlStatement = 
						"SELECT"
						+ " id" 
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " indicator = '" + aIndicator + "'"
						+ " and '" + new DateHandler().getStringFromDate(aDate) + "' between"
						+ " validFrom and validTo";

		Statement statement = null;
			
		statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		isExists = resultSet.isBeforeFirst();
		
		resultSet.close();

		statement.close();

		return isExists;
		
	} // isExists

	public void update(
					int aId
					, Date aValidFrom
					, Date aValidTo
					, float aPlus
					, float aMinus
					, float aBalance
				) throws SQLException, Ac4yException{

		if (aValidFrom == null)
			throw new Ac4yException("valid from is empty!");
		
		if (aValidTo == null)
			throw new Ac4yException("valid to is empty!");
		
		PreparedStatement preparedStatement = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
		String vSQLStatement = "UPDATE Ac4yIndicatorBalanceHistory "
				+ "SET validFrom = ?, validTo = ?, plus = ?, minus = ?, balance = ? "
				+ "WHERE id = ? ";
		
		preparedStatement = getConnection().prepareStatement(vSQLStatement);
		
		preparedStatement.setString(1, simpleDateFormat.format(aValidFrom));
		preparedStatement.setString(2, simpleDateFormat.format(aValidTo));
		preparedStatement.setFloat(3, aPlus);
		preparedStatement.setFloat(4, aMinus);
		preparedStatement.setFloat(5, aBalance);
		preparedStatement.setInt(6, aId);
		 
		preparedStatement.executeUpdate();				
		
		preparedStatement.close();
		
	} // update

	public void updateBalanceByIndicator(
			String aIndicator
			,Date aDate
			,float aPlus
			,float aMinus
		) throws SQLException, Ac4yException{

			if (aIndicator == null || aIndicator == "")
				throw new Ac4yException("indicator is empty!");		
		
			if (aDate == null)
				throw new Ac4yException("date is empty!");		
		
			String vSQLStatement = "UPDATE Ac4yIndicatorBalanceHistory "
					+ "SET balance = ( balance + ? - ? )"
					+ "WHERE "
					+ "indicator = ? "
					+ "AND validFrom > ? ";
			
			PreparedStatement preparedStatement = getConnection().prepareStatement(vSQLStatement);
			
			preparedStatement.setFloat(1, aPlus);
			preparedStatement.setFloat(2, aMinus);
			preparedStatement.setString(3, aIndicator);
			preparedStatement.setString(4, new DateHandler().getStringFromDate(aDate) );
			
			 
			preparedStatement.executeUpdate();				
			
			preparedStatement.close();
					
	} // updateBalanceByIndicator

	public void insert(
					String aIndicator
					, Date aValidFrom
					, Date aValidTo
					, float aPlus
					, float aMinus
					, float aBalance
				) throws SQLException, Ac4yException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");	

		if (aValidFrom == null)
			throw new Ac4yException("valid from is empty!");
		
		if (aValidTo == null)
			throw new Ac4yException("valid to is empty!");
		
		PreparedStatement preparedStatement = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
		String vSQLStatement = "INSERT INTO Ac4yIndicatorBalanceHistory "
				+ "(indicator, validFrom, validTo, plus, minus, balance) "
				+ "VALUES (?, ?, ?, ?, ?, ?) ";
		
		preparedStatement = getConnection().prepareStatement(vSQLStatement);
		
		preparedStatement.setString(1, aIndicator);
		preparedStatement.setString(2, simpleDateFormat.format(aValidFrom));
		preparedStatement.setString(3, simpleDateFormat.format(aValidTo));
		preparedStatement.setFloat(4, aPlus);
		preparedStatement.setFloat(5, aMinus);
		preparedStatement.setFloat(6, aBalance);
		 
		preparedStatement.executeUpdate();				
		
		preparedStatement.close();
		
	} // insert

	public void maintain(
			String aIndicator
			, Date aDate
			, float aPlus
			, float aMinus
		) throws SQLException, Ac4yException, ParseException{
		
			if (isExist(aIndicator, aDate)){

				Ac4yIndicatorBalanceHistory ac4yIndicatorBalanceHistory = get(aIndicator, aDate);

				if (aDate.equals(ac4yIndicatorBalanceHistory.getValidFrom()))
					update(
						ac4yIndicatorBalanceHistory.getId()
						,ac4yIndicatorBalanceHistory.getValidFrom()
						,ac4yIndicatorBalanceHistory.getValidTo()
						,ac4yIndicatorBalanceHistory.getPlus()+aPlus
						,ac4yIndicatorBalanceHistory.getMinus()+aMinus
						,ac4yIndicatorBalanceHistory.getBalance()+aPlus-aMinus
					);
				else{

					update(
							ac4yIndicatorBalanceHistory.getId()
							,ac4yIndicatorBalanceHistory.getValidFrom()
							,new DateHandler().getShiftedDate(aDate,-1)
							,ac4yIndicatorBalanceHistory.getPlus()
							,ac4yIndicatorBalanceHistory.getMinus()
							,ac4yIndicatorBalanceHistory.getBalance()
					);

					insert(
							aIndicator
							,aDate
							,ac4yIndicatorBalanceHistory.getValidTo()
							,aPlus
							,aMinus
							,ac4yIndicatorBalanceHistory.getBalance()+aPlus-aMinus
						);

							
				}
				
				if (ac4yIndicatorBalanceHistory.getValidTo().before(new DateHandler().getDateFromString("2999-12-31")) )
					updateBalanceByIndicator(
							ac4yIndicatorBalanceHistory.getIndicator()
							,aDate
							,aPlus
							,aMinus
					);
				
					/*
					public void update(
							int aId
							, Date aValidFrom
							, Date aValidTo
							, float aPlus
							, float aMinus
							, float aBalance
						){*/

			}
			else {

				insert(
						aIndicator
						,new DateHandler().getDateFromString("0001-01-01")
						,new DateHandler().getShiftedDate(aDate,-1)
						,0
						,0
						,0
					);

				insert(
						aIndicator
						,aDate
						,new DateHandler().getDateFromString("2999-12-31")
						,aPlus
						,aMinus
						,aPlus-aMinus
					);

			}
		
		
		} // maintain	
	
	public void delete(int aId) throws SQLException, Ac4yException{

		String 	sqlStatement = 
						"DELETE"
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " id = " + aId
						;

		Statement statement = getConnection().createStatement();
		
		statement.executeUpdate(sqlStatement);
		
		statement.close();
		
	} // delete
	
	public void delete(String aIndicator) throws SQLException, Ac4yException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");	
		
		String 	sqlStatement = 
						"DELETE"
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " indicator = '" + aIndicator + "'"
						;

		Statement statement = getConnection().createStatement();
		
		statement.executeUpdate(sqlStatement);
		
		statement.close();
		
	} // delete	
		
	public float getBalance(String aIndicator, Date aDate) throws SQLException, Ac4yException, ParseException{

		Ac4yIndicatorBalanceHistory ac4yIndicatorBalanceHistory = get(aIndicator, aDate);
		
		if (ac4yIndicatorBalanceHistory==null)
			return 0;
		else
			return ac4yIndicatorBalanceHistory.getBalance();
		
	} // getBalance

	public float getPlus(String aIndicator, Date aDate) throws SQLException, Ac4yException, ParseException{

		Ac4yIndicatorBalanceHistory ac4yIndicatorBalanceHistory = get(aIndicator, aDate);
		
		if (ac4yIndicatorBalanceHistory==null)
			return 0;
		else
			return ac4yIndicatorBalanceHistory.getPlus();
		
	} // getPlus

	public float getMinus(String aIndicator, Date aDate) throws SQLException, Ac4yException, ParseException{

		Ac4yIndicatorBalanceHistory ac4yIndicatorBalanceHistory = get(aIndicator, aDate);
		
		if (ac4yIndicatorBalanceHistory==null)
			return 0;
		else
			return ac4yIndicatorBalanceHistory.getMinus();
		
	} // getMinus

	public float getPeriodPlus(String aIndicator, Date aFrom, Date aTo) throws SQLException, Ac4yException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");		

		if (aFrom == null)
			throw new Ac4yException("from is empty!");
		
		if (aTo == null)
			throw new Ac4yException("to is empty!");		
		
		float result = 0;
		
		String 	sqlStatement = 
						"SELECT"
						+ " sum(plus) result"
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " indicator = '" + aIndicator + "'"
/*
 * 						+ " and '" + new DateHandler().getStringFromDate(aFrom) + "' <= validFrom"
						+ " and validTo <= '" + new DateHandler().getStringFromDate(aTo) + "'"		
 */
 						+ " and validFrom between '" + new DateHandler().getStringFromDate(aFrom) + "'"
 						+ " and '" + new DateHandler().getStringFromDate(aTo) + "'"
						;
		
		Statement statement = null;
			
		statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		if (resultSet.next())
			result = resultSet.getFloat(1);
		
		resultSet.close();

		statement.close();
		
		return result;
		
	} // getPeriodPlus

	public float getPeriodMinus(String aIndicator, Date aFrom, Date aTo) throws SQLException, Ac4yException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");		

		if (aFrom == null)
			throw new Ac4yException("from is empty!");
		
		if (aTo == null)
			throw new Ac4yException("to is empty!");		
		
		float result = 0;
		
		String 	sqlStatement = 
						"SELECT"
						+ " sum(minus) result"
						+ " FROM Ac4yIndicatorBalanceHistory"
						+ " WHERE"
						+ " indicator = '" + aIndicator + "'"
/*						
						+ " and '" + new DateHandler().getStringFromDate(aFrom) + "' <= validFrom"
						+ " and validTo <= '" + new DateHandler().getStringFromDate(aTo) + "'"
*/
 						+ " and validFrom between '" + new DateHandler().getStringFromDate(aFrom) + "'"
 						+ " and '" + new DateHandler().getStringFromDate(aTo) + "'"
						;
		
		Statement statement = null;
			
		statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		if (resultSet.next())
			result = resultSet.getFloat(1);
		
		resultSet.close();

		statement.close();
		
		return result;
		
	} // getPeriodMinus
	
	
	public ResultSet getListResultSet(Statement aStatement, String aWhere) throws SQLException, Ac4yException{
	
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	
		
		String where = "";
		
		if ((aWhere==null)||(aWhere==""))
			where = "1=1";
		else
			where = aWhere;
			
		String sqlStatement = "SELECT * FROM Ac4yIndicatorBalanceHistory WHERE " + where;
		
		return aStatement.executeQuery(sqlStatement);
				
	} // getList

	public Ac4yIndicatorBalanceHistoryList getList(String aWhere) throws SQLException, Ac4yException, ParseException{
		
		Ac4yIndicatorBalanceHistoryList result = new Ac4yIndicatorBalanceHistoryList();
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getListResultSet(statement, aWhere);

		while (resultSet.next())
			result.getAc4yIndicatorBalanceHistory().add(
				get(resultSet)
			);
		
		statement.close();

		return result;
				
	} // getList

	public int getListCount(String aWhere) throws SQLException, Ac4yException{
		
		int result = -2;
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getListResultSet(statement, aWhere);

        resultSet.last();
        
        result = resultSet.getRow();

        statement.close();

		return result;
				
	} // getListCount
	
	
/*
 * 
 * 	public IndicatorBalanceHistory(
			 int id
			 ,Date validFrom
			 ,Date validTo
			 ,float plus
			 ,float minus
			 ,float balance
			) {	
 */
	public IndicatorBalanceHistory getIndicatorBalanceHistory(ResultSet aResultSet) throws SQLException, ParseException {

		return 
			new IndicatorBalanceHistory(
					aResultSet.getInt("id")
					,new DateHandler().getDateFromString(aResultSet.getString("validFrom"))
					,new DateHandler().getDateFromString(aResultSet.getString("validTo"))
					,aResultSet.getFloat("plus")
					,aResultSet.getFloat("minus")
					,aResultSet.getFloat("balance")
			);
		
	} // getIndicatorBalanceHistory
		
	public IndicatorBalanceHistoryList getIndicatorBalanceHistoryList(String aIndicator) throws SQLException, Ac4yException, ParseException{
		
		IndicatorBalanceHistoryList result = new IndicatorBalanceHistoryList();
		
		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");
		
		Statement statement = getConnection().createStatement();

		String sqlStatement = "SELECT * FROM Ac4yIndicatorBalanceHistory WHERE indicator = '" + aIndicator + "'";
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			result.getIndicatorBalanceHistory().add(
					getIndicatorBalanceHistory(resultSet)
			);
		
		statement.close();

		return result;
				
	} // getIndicatorBalanceHistoryList
	
	
} // Ac4yIndicatorBalanceHistoryDBAdapter