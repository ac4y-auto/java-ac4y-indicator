package ac4y.indicator.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ac4y.base.Ac4yException;
import ac4y.base.domain.Ac4y;
import ac4y.indicator.domain.Ac4yIndicatorAnalytics;
import ac4y.indicator.domain.Ac4yIndicatorAnalyticsList;
import ac4y.indicator.domain.IndicatorAnalytics;
import ac4y.indicator.domain.IndicatorAnalyticsList;
import ac4y.indicator.domain.OriginAnalytics;
import ac4y.indicator.domain.OriginAnalyticsList;
import ac4y.utility.DateHandler;

import javax.xml.bind.JAXBException;

public class Ac4yIndicatorAnalyticsDBAdapter extends Ac4y {

	public Ac4yIndicatorAnalyticsDBAdapter(Connection aConnection){
		setConnection(aConnection); 
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private Connection connection;

	public Ac4yIndicatorAnalytics getStornoPair(Ac4yIndicatorAnalytics aAc4yIndicatorAnalytics) {

		return
			new Ac4yIndicatorAnalytics(
				 aAc4yIndicatorAnalytics.getIndicator()
				 ,aAc4yIndicatorAnalytics.getDate()
				 ,aAc4yIndicatorAnalytics.getMinus()
				 ,aAc4yIndicatorAnalytics.getPlus()
				 ,aAc4yIndicatorAnalytics.getAmount()*-1
				 ,!aAc4yIndicatorAnalytics.isNegative()
				 ,aAc4yIndicatorAnalytics.getOrigin()
				 ,true
				);

	} // getStornoPair
	
	public Ac4yIndicatorAnalytics get(ResultSet aResultSet) throws SQLException, ParseException {

		return 
			new Ac4yIndicatorAnalytics(
					aResultSet.getInt("id")
					,aResultSet.getString("indicator")
					,new DateHandler().getDateFromString(aResultSet.getString("date"))//aResultSet.getDate("date")
					,new DateHandler().getTimeFromString(aResultSet.getString("timestamp"))//aResultSet.getDate("timestamp")
					,aResultSet.getFloat("plus")
					,aResultSet.getFloat("minus")
					,aResultSet.getFloat("amount")
					,aResultSet.getBoolean("negative")
					,aResultSet.getString("origin")
					,aResultSet.getBoolean("storno")
			);

	} // get	
	
	public Ac4yIndicatorAnalytics get(int aId) throws SQLException, Ac4yException, ParseException{

		Ac4yIndicatorAnalytics result = null;
		
		String 	sqlStatement = 
						"SELECT"
						+ " *" 
						+ " FROM Ac4yIndicatorAnalytics"
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
	
	public void delete(int aId) throws SQLException, Ac4yException{

		String 	sqlStatement = 
						"DELETE"
						+ " FROM Ac4yIndicatorAnalytics"
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
						+ " FROM Ac4yIndicatorAnalytics"
						+ " WHERE"
						+ " indicator = '" + aIndicator + "'"
						;

		Statement statement = getConnection().createStatement();
		
		statement.executeUpdate(sqlStatement);
		
		statement.close();
		
	} // delete	
	
	public void deleteByOrigin(String aOrigin) throws SQLException, Ac4yException{

		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");	
		
		String 	sqlStatement = 
						"DELETE"
						+ " FROM Ac4yIndicatorAnalytics"
						+ " WHERE"
						+ " origin = '" + aOrigin + "'"						
						;

		Statement statement = getConnection().createStatement();
		
		statement.executeUpdate(sqlStatement);
		
		statement.close();
		
	} // deleteByOrigin	
	
	public Ac4yIndicatorAnalytics insert(
					String aIndicator
					,Date aDate
					,float aAmount
					,boolean aNegative
					,String aOrigin	
					,boolean aStorno
				) throws SQLException, Ac4yException {

		Ac4yIndicatorAnalytics result = new Ac4yIndicatorAnalytics();
		
		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");	
		
		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");	

		if (aDate == null)
			throw new Ac4yException("date from is empty!");
		
		String vSQLStatement = 
				"INSERT INTO Ac4yIndicatorAnalytics"
				+ "(indicator, date, plus, minus, amount, negative, origin, storno)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
		
		PreparedStatement preparedStatement = 
			getConnection().prepareStatement(
				vSQLStatement
				,Statement.RETURN_GENERATED_KEYS
			);
		
//		Statement stmt = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		float vPlus = -2;
		float vMinus = -2;
		float vAmount = aAmount;
		boolean vNegative = false;
		
		if (aNegative) {
			vPlus = 0;
			vMinus = aAmount;
			vAmount = aAmount*(-1);
			vNegative = true; 
		}
		else {
			vPlus = aAmount;
			vMinus = 0;
			vNegative = false; 
		}
			
		preparedStatement.setFloat(3, vPlus);
		preparedStatement.setFloat(4, vMinus);
		preparedStatement.setBoolean(6, vNegative);		
		
		preparedStatement.setString(1, aIndicator);
		preparedStatement.setString(2, new DateHandler().getStringFromDate(aDate));
		
		preparedStatement.setFloat(5, vAmount);
		preparedStatement.setString(7, aOrigin);
		
		System.out.println("storno:"+aStorno);
		
		preparedStatement.setBoolean(8, aStorno);
		 
		if (preparedStatement.executeUpdate() == 0)
			throw new Ac4yException("insert failed!");
	
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		
		if (resultSet.next()){
			result.setId(resultSet.getInt(1));
		}
		else
			result.setId(-1);
		
		preparedStatement.close();
		
		result.setIndicator(aIndicator);
		result.setDate(aDate);
		result.setPlus(vPlus);
		result.setMinus(vMinus);
		result.setNegative(vNegative);
		result.setAmount(vAmount);
		result.setOrigin(aOrigin);
		result.setStorno(aStorno);
		
		return result;
		
	} // insert
	
	public Ac4yIndicatorAnalytics insert(Ac4yIndicatorAnalytics aAc4yIndicatorAnalytics) throws SQLException, Ac4yException, JAXBException {

		String vSQLStatement = 
				"INSERT INTO Ac4yIndicatorAnalytics"
				+ "(indicator, date, plus, minus, amount, negative, origin, storno)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
		
		PreparedStatement preparedStatement = 
				getConnection().prepareStatement(
					vSQLStatement
					,Statement.RETURN_GENERATED_KEYS
				);
		
		preparedStatement.setString(1, aAc4yIndicatorAnalytics.getIndicator());
		preparedStatement.setString(2, new DateHandler().getStringFromDate(aAc4yIndicatorAnalytics.getDate()));
		preparedStatement.setFloat(3, aAc4yIndicatorAnalytics.getPlus());
		preparedStatement.setFloat(4, aAc4yIndicatorAnalytics.getMinus());
		preparedStatement.setFloat(5, aAc4yIndicatorAnalytics.getAmount());
		preparedStatement.setBoolean(6, aAc4yIndicatorAnalytics.isNegative());		
		preparedStatement.setString(7, aAc4yIndicatorAnalytics.getOrigin());
		preparedStatement.setBoolean(8, aAc4yIndicatorAnalytics.isStorno());
		 
		System.out.println("insert:"+aAc4yIndicatorAnalytics.getAsXml());
		
		if (preparedStatement.executeUpdate() == 0)
			throw new Ac4yException("insert failed!");
		
		preparedStatement.close();
		
		return aAc4yIndicatorAnalytics;
		
	} // insert
	
	public Ac4yIndicatorAnalytics storno(Ac4yIndicatorAnalytics aAc4yIndicatorAnalytics) throws SQLException, Ac4yException, ParseException, JAXBException {
		
		Ac4yIndicatorAnalytics result = null; 
		
		result = insert(getStornoPair(aAc4yIndicatorAnalytics));
		
		System.out.println("storno pair:" + getStornoPair(aAc4yIndicatorAnalytics).getAsXml());
		
		new Ac4yIndicatorBalanceHistoryDBAdapter(
				getConnection()
			).maintain(
				result.getIndicator()
				,result.getDate()
				,result.getPlus()
				,result.getMinus()
			);
		
		return result;
		
	} // storno
	
	public Ac4yIndicatorAnalytics storno(int aId) throws SQLException, Ac4yException, ParseException, JAXBException {
		
		Ac4yIndicatorAnalytics result = null;
		
		result = insert(getStornoPair(get(aId)));

		new Ac4yIndicatorBalanceHistoryDBAdapter(
				getConnection()
			).maintain(
				result.getIndicator()
				,result.getDate()
				,result.getPlus()
				,result.getMinus()
			);
		
		return result;
		
	} // storno
	
	public void storno(String aIndicator) throws SQLException, Ac4yException, ParseException, JAXBException{
		
		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");

		String sqlStatement = "SELECT * FROM Ac4yIndicatorAnalytics WHERE indicator = '" + aIndicator + "'";
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next())
			storno(get(resultSet));
		
		statement.close();
				
	} // storno

	public void stornoByOrigin(String aOrigin) throws SQLException, Ac4yException, ParseException, JAXBException {

		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");

		String sqlStatement = 
			"SELECT * FROM Ac4yIndicatorAnalytics "
			+ " WHERE origin = '" + aOrigin + "'"
			+ " AND storno = 0"
			;		
		
		System.out.println("storno by origin sqlStatement:" + sqlStatement);
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		while (resultSet.next()) {
			
			System.out.println("storno origin:" + get(resultSet).getOrigin());
			
			System.out.println("base :" + get(resultSet).getAsXml() );
			
			storno(get(resultSet));
			
			System.out.println("size after storno:" + resultSet.getRow());
 
		}
		
		statement.close();
		
		System.out.println("stornoByOrigin end");
		
	} // stornoByOrigin

	
	public ResultSet getListResultSet(Statement aStatement, String aWhere) throws SQLException, Ac4yException{
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	
		
		String where = "";
		
		if ((aWhere==null)||(aWhere==""))
			where = "1=1";
		else
			where = aWhere;
			
		String sqlStatement = "SELECT * FROM Ac4yIndicatorAnalytics WHERE " + where;
		
		return aStatement.executeQuery(sqlStatement);

	} // getListResultSet
	
	public Ac4yIndicatorAnalyticsList getList(String aWhere) throws SQLException, Ac4yException, ParseException{
		
		Ac4yIndicatorAnalyticsList result = new Ac4yIndicatorAnalyticsList();
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getListResultSet(statement, aWhere);

		while (resultSet.next())
			result.getAc4yIndicatorAnalytics().add(
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

	
	public IndicatorAnalytics getIndicatorAnalytics(ResultSet aResultSet) throws SQLException {

		return 
			new IndicatorAnalytics(
					aResultSet.getInt("id")
					,aResultSet.getDate("date")
					,aResultSet.getDate("timestamp")
					,aResultSet.getFloat("plus")
					,aResultSet.getFloat("minus")
					,aResultSet.getFloat("amount")
					,aResultSet.getBoolean("negative")
					,aResultSet.getString("origin")
					,aResultSet.getBoolean("storno")
			);

	} // getIndicatorAnalytics	
	
	public OriginAnalytics getOriginAnalytics(ResultSet aResultSet) throws SQLException {

		return 
			new OriginAnalytics(
					aResultSet.getInt("id")
					,aResultSet.getString("indicator")
					,aResultSet.getDate("date")
					,aResultSet.getDate("timestamp")
					,aResultSet.getFloat("plus")
					,aResultSet.getFloat("minus")
					,aResultSet.getFloat("amount")
					,aResultSet.getBoolean("negative")
			);

	} // getOriginAnalytics	

	
	public ResultSet getIndicatorAnalyticsListResultSet(Statement aStatement, String aIndicator) throws SQLException, Ac4yException{

		if (aStatement == null)
			throw new Ac4yException("statement is null!");	
		
		if (aIndicator == null || aIndicator == "")
			throw new Ac4yException("indicator is empty!");	
		
		String 	sqlStatement = 
				"SELECT * "
				+ " FROM Ac4yIndicatorAnalytics"
				+ " WHERE"
				+ " indicator = '" + aIndicator + "'"
				;
		
		return aStatement.executeQuery(sqlStatement);

	} // getIndicatorAnalyticsList

	public IndicatorAnalyticsList getIndicatorAnalyticsList(String aIndicator) throws SQLException, Ac4yException{

		IndicatorAnalyticsList result = new IndicatorAnalyticsList();
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getIndicatorAnalyticsListResultSet(statement, aIndicator); 

		while (resultSet.next())
			result.getIndicatorAnalytics().add(
				getIndicatorAnalytics(resultSet)
			);
		
		statement.close();

		return result;
				
	} // getIndicatorAnalyticsList

	public int getIndicatorAnalyticsListCount(String aWhere) throws SQLException, Ac4yException{
		
		int result = -2;
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getIndicatorAnalyticsListResultSet(statement, aWhere);

        resultSet.last();
        
        result = resultSet.getRow();

        statement.close();

		return result;
				
	} // getIndicatorAnalyticsListCount

	
	public ResultSet getOriginAnalyticsListResultSet(Statement aStatement, String aOrigin) throws SQLException, Ac4yException{

		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");	
		
		String 	sqlStatement = 
				"SELECT * "
				+ " FROM Ac4yIndicatorAnalytics"
				+ " WHERE"
				+ " origin = '" + aOrigin + "'"
				;
		
		return aStatement.executeQuery(sqlStatement);
				
	} // getOriginAnalyticsListResultSet
	
	public OriginAnalyticsList getOriginAnalyticsList(String aOrigin) throws SQLException, Ac4yException{

		if (aOrigin == null || aOrigin == "")
			throw new Ac4yException("origin is empty!");	
		
		OriginAnalyticsList result = new OriginAnalyticsList();
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getOriginAnalyticsListResultSet(statement, aOrigin); 

		while (resultSet.next())
			result.getOriginAnalytics().add(
					getOriginAnalytics(resultSet)
			);
		
		statement.close();

		return result;
				
	} // getOriginAnalyticsList

	public int getOriginAnalyticsListCount(String aOrigin) throws SQLException, Ac4yException{
		
		int result = -2;
		
		Statement statement = getConnection().createStatement();

		ResultSet resultSet = getOriginAnalyticsListResultSet(statement, aOrigin);

        resultSet.last();
        
        result = resultSet.getRow();

        statement.close();

		return result;
				
	} // getOriginAnalyticsListCount
	
} // Ac4yIndicatorBalanceHistoryDBAdapter