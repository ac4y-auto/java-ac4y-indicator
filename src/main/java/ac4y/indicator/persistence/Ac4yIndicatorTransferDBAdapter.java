package ac4y.indicator.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ac4y.base.Ac4yException;
import ac4y.indicator.domain.Ac4yIndicatorTransfer;
import ac4y.indicator.domain.Ac4yIndicatorTransferList;
import ac4y.indicator.domain.IndicatorTransfer;
import ac4y.indicator.domain.IndicatorTransferList;
import ac4y.indicator.domain.OriginTransfer;
import ac4y.indicator.domain.OriginTransferList;
import ac4y.utility.DateHandler;

public class Ac4yIndicatorTransferDBAdapter {

	public Ac4yIndicatorTransferDBAdapter(Connection aConnection){
		setConnection(aConnection); 
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private Connection connection;

	public Ac4yIndicatorTransfer get(ResultSet aResultSet) throws SQLException {

		return
			new Ac4yIndicatorTransfer(
					aResultSet.getInt("id")
					,aResultSet.getString("plus")
					,aResultSet.getString("minus")
					,aResultSet.getDate("date")
					,aResultSet.getDate("timestamp")
					,aResultSet.getFloat("amount")
					,aResultSet.getString("origin")
			);
		
	} // getByResultSet	
	
	public Ac4yIndicatorTransfer get(int aId) throws SQLException, Ac4yException{

		Ac4yIndicatorTransfer result = null;
		
		String 	sqlStatement = 
						"SELECT"
						+ " *" 
						+ " FROM Ac4yIndicatorTransfer"
						+ " WHERE"
						+ " id = " + aId
						;

		Statement statement = null;
			
		statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		if (resultSet.next())
			result = get(resultSet);
		else
			throw new Ac4yException("such transfer does not exist!");	
		
		resultSet.close();

		statement.close();
		
		return result;
		
	} // get


	public Ac4yIndicatorTransfer getStornoPair(Ac4yIndicatorTransfer aAc4yIndicatorTransfer) {

		return
			new Ac4yIndicatorTransfer(
					aAc4yIndicatorTransfer.getMinus()
					,aAc4yIndicatorTransfer.getPlus()
					,aAc4yIndicatorTransfer.getDate()
					,aAc4yIndicatorTransfer.getAmount()
					,aAc4yIndicatorTransfer.getOrigin()
				);

	} // getStornoPair
	
	
	public Ac4yIndicatorTransfer insert(
					String aPlus
					,String aMinus
					,Date aDate
					,float aAmount
					,String aOrigin	
					,boolean aStorno
				) throws SQLException, Ac4yException {
		
		if (aPlus == null || aPlus == "")
			throw new Ac4yException("plus is empty!");	
		
		if (aMinus == null || aMinus == "")
			throw new Ac4yException("minus is empty!");	
		
		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");	

		if (aDate == null)
			throw new Ac4yException("date from is empty!");

		Ac4yIndicatorTransfer result = null;
		
		String vSQLStatement = 
				"INSERT INTO Ac4yIndicatorTransfer"
				+ "(plus, minus, date, amount, origin)"
				+ "VALUES (?, ?, ?, ?, ?) ";
		
		PreparedStatement preparedStatement = 
			getConnection().prepareStatement(
				vSQLStatement
				,Statement.RETURN_GENERATED_KEYS
			);
		
		preparedStatement.setString(1, aPlus);
		preparedStatement.setString(2, aMinus);
		preparedStatement.setString(3, new DateHandler().getStringFromDate(aDate));
		preparedStatement.setFloat(4, aAmount);
		preparedStatement.setString(5, aOrigin);		
		 
		preparedStatement.executeUpdate();				

		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		
		result = 
			new Ac4yIndicatorTransfer(
					aPlus
					,aMinus
					,aDate
					,aAmount
					,aOrigin
			);

		if (resultSet.next()){
			result.setId(resultSet.getInt(1));
		}
		else
			result.setId(-1);

		preparedStatement.close();
		
		return result;
		
	} // insert
	
	public Ac4yIndicatorTransfer insert(Ac4yIndicatorTransfer aAc4yIndicatorTransfer) throws SQLException, Ac4yException {

		String vSQLStatement = 
				"INSERT INTO Ac4yIndicatorTransfer"
				+ "(plus, minus, date, amount, origin)"
				+ "VALUES (?, ?, ?, ?, ?) ";
		
		PreparedStatement preparedStatement = getConnection().prepareStatement(vSQLStatement);
		
		preparedStatement.setString(1, aAc4yIndicatorTransfer.getPlus());
		preparedStatement.setString(2, aAc4yIndicatorTransfer.getMinus());
		preparedStatement.setString(3, new DateHandler().getStringFromDate(aAc4yIndicatorTransfer.getDate()));
		preparedStatement.setFloat(4, aAc4yIndicatorTransfer.getAmount());
		preparedStatement.setString(5, aAc4yIndicatorTransfer.getOrigin());		
		 
		preparedStatement.executeUpdate();				
		
		preparedStatement.close();
		
		return aAc4yIndicatorTransfer;
		
	} // insert

	
	public void delete(int aId) throws SQLException, Ac4yException{

		String 	sqlStatement = 
						"DELETE"
						+ " FROM Ac4yIndicatorTransfer"
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
				+ " FROM Ac4yIndicatorTransfer WHERE" 
				+ " plus = '" + aIndicator + "'"
				+ " or minus = '" + aIndicator + "'"
				;

		Statement statement = getConnection().createStatement();
		
		statement.executeUpdate(sqlStatement);
		
		statement.close();
		
	} // delete	
	
	
	public Ac4yIndicatorTransfer storno(Ac4yIndicatorTransfer aAc4yIndicatorTransfer) throws SQLException, Ac4yException, ParseException {
		
		Ac4yIndicatorTransfer result = null; 
		
		result = insert(getStornoPair(aAc4yIndicatorTransfer));
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(getConnection()).maintain(
				result.getPlus()
				,result.getDate()
				,0
				,result.getAmount()
		);
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(getConnection()).maintain(
				result.getMinus()
				,result.getDate()
				,result.getAmount()
				,0
		);
		
		return result;
		
	} // storno
	
	public Ac4yIndicatorTransfer storno(int aId) throws SQLException, Ac4yException, ParseException {
		
		return storno(getStornoPair(get(aId)));
		
		/*
		Ac4yIndicatorTransfer result = null;
		
		result = insert(getStornoPair(get(aId)));

		new Ac4yIndicatorBalanceHistoryDBAdapter(getConnection()).maintain(
				result.getPlus()
				,result.getDate()
				,0
				,result.getAmount()
		);
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(getConnection()).maintain(
				result.getMinus()
				,result.getDate()
				,result.getAmount()
				,0
		);
		
		return result;
		*/
	} // storno

	public void storno(String aIndicator) throws SQLException, Ac4yException, ParseException{
		
		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");

		String sqlStatement = 
			"SELECT * FROM Ac4yIndicatorTransfer WHERE" 
			+ " plus = '" + aIndicator + "'"
			+ " or minus = '" + aIndicator + "'"
			;
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			storno(get(resultSet));
		
		statement.close();
				
	} // storno
	
	public void stornoByOrigin(String aOrigin) throws SQLException, Ac4yException, ParseException{
		
		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");

		String sqlStatement = "SELECT * FROM Ac4yIndicatorTransfer WHERE origin = '" + aOrigin + "'";
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			storno(get(resultSet));
		
		statement.close();
				
	} // stornoByOrigin

	
	public ResultSet getListResultSet(Statement aStatement, String aWhere) throws SQLException, Ac4yException{
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	

		String where = "";
		
		if ((aWhere==null)||(aWhere==""))
			where = "1=1";
		else
			where = aWhere;
			
		String sqlStatement = "SELECT * FROM Ac4yIndicatorTransfer WHERE " + where;
		
		return aStatement.executeQuery(sqlStatement);
		
	} // getListResultSet

	public Ac4yIndicatorTransferList getList(String aWhere) throws SQLException, Ac4yException{
		
		Ac4yIndicatorTransferList result = new Ac4yIndicatorTransferList();
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getListResultSet(statement, aWhere);
		
		while (resultSet.next())
			result.getAc4yIndicatorTransfer().add(
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
	
	
	public IndicatorTransfer getIndicatorTransferAsPlus(ResultSet aResultSet) throws SQLException {

		return 
			new IndicatorTransfer(
					aResultSet.getInt("id")
					,aResultSet.getString("minus")
					,aResultSet.getDate("date")
					,aResultSet.getDate("timestamp")
					,aResultSet.getFloat("amount")
					,aResultSet.getString("origin")
			);

	} // getIndicatorTransferAsPlus	

	public IndicatorTransfer getIndicatorTransferAsMinus(ResultSet aResultSet) throws SQLException {

		return 
			new IndicatorTransfer(
					aResultSet.getInt("id")
					,aResultSet.getString("plus")
					,aResultSet.getDate("date")
					,aResultSet.getDate("timestamp")
					,aResultSet.getFloat("amount")*-1
					,aResultSet.getString("origin")
			);

	} // getIndicatorTransferAsMinus	

	public OriginTransfer getOriginTransfer(ResultSet aResultSet) throws SQLException {

		return 
			new OriginTransfer(
					aResultSet.getInt("id")
					,aResultSet.getString("plus")					
					,aResultSet.getString("minus")
					,aResultSet.getDate("date")
					,aResultSet.getDate("timestamp")
					,aResultSet.getFloat("amount")
			);

	} // getOriginTransfer	
	
	
	public IndicatorTransferList getIndicatorTransferList(String aIndicator) throws SQLException, Ac4yException{

		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");	
		
		IndicatorTransferList result = new IndicatorTransferList();
		
		Statement statement = null;
		ResultSet resultSet = null;
		String 	sqlStatement = "";

		statement = getConnection().createStatement();
		
		sqlStatement = 
			"SELECT * "
			+ " FROM Ac4yIndicatorTransfer"
			+ " WHERE"
			+ " plus = '" + aIndicator + "'"
			;
		
		resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			result.getIndicatorTransfer().add(
					getIndicatorTransferAsPlus(resultSet)
			);
		
		statement.close();

		statement = getConnection().createStatement();
				
		sqlStatement = 
			"SELECT * "
			+ " FROM Ac4yIndicatorTransfer"
			+ " WHERE"
			+ " minus = '" + aIndicator + "'"
			;
		
		resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			result.getIndicatorTransfer().add(
					getIndicatorTransferAsMinus(resultSet)
			);
		
		statement.close();

		return result;
				
	} // getIndicatorAnalyticsList

	public OriginTransferList getOriginTransferList(String aOrigin) throws SQLException, Ac4yException{

		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");	
		
		OriginTransferList result = new OriginTransferList();
		
		Statement statement = getConnection().createStatement();

		String 	sqlStatement = 
				"SELECT * "
				+ " FROM Ac4yIndicatorTransfer"
				+ " WHERE"
				+ " origin = '" + aOrigin + "'"
				;
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			result.getOriginTransfer().add(
					getOriginTransfer(resultSet)
			);
		
		statement.close();

		return result;
				
	} // getOriginAnalyticsList
	
} // Ac4yIndicatorBalanceHistoryDBAdapter