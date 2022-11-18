import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false"
            		+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
            		+ "&user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    

    public List<user> listAllUsers() throws SQLException {
        List<user> listUser = new ArrayList<user>();        
        String sql = "SELECT * FROM User";      
        try {
        	connect_func(); // Call this function in try block in case testdb was dropped in MySQL Workbench      
            statement = (Statement) connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        	while (resultSet.next()) {
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String password = resultSet.getString("password");
                String birthday = resultSet.getString("birthday");
                String address_street_num = resultSet.getString("address_street_num"); 
                String address_street = resultSet.getString("address_street"); 
                String address_city = resultSet.getString("address_city"); 
                String address_state = resultSet.getString("address_state"); 
                String address_zip_code = resultSet.getString("address_zip_code"); 
                int balance = resultSet.getInt("balance");

                 
                user users = new user(email,firstName, lastName, password, birthday, address_street_num,  address_street,  address_city,  address_state,  address_zip_code, balance);
                listUser.add(users);
            }        
            resultSet.close();
        } catch (SQLException e) {
        	System.out.println(e.toString()+ ", that is the error");
        }
        disconnect(); 
        
        return listUser;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        	System.out.println("Connection is getting closed");
        }
    }
    
    public void insert(user users) throws SQLException {
    	connect_func();         
		String sql = "insert into User(email, firstName, lastName, password, birthday,address_street_num, address_street,address_city,address_state,address_zip_code,balance) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, users.getEmail());
			preparedStatement.setString(2, users.getFirstName());
			preparedStatement.setString(3, users.getLastName());
			preparedStatement.setString(4, users.getPassword());
			preparedStatement.setString(5, users.getBirthday());
			preparedStatement.setString(6, users.getAddress_street_num());		
			preparedStatement.setString(7, users.getAddress_street());		
			preparedStatement.setString(8, users.getAddress_city());		
			preparedStatement.setString(9, users.getAddress_state());		
			preparedStatement.setString(10, users.getAddress_zip_code());		
			preparedStatement.setInt(11, users.getbalance());		
		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(user users) throws SQLException {
        String sql = "update User set firstName=?, lastName =?,password = ?,birthday=?,address_street_num =?, address_street=?,address_city=?,address_state=?,address_zip_code=?, balance=? where email = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getFirstName());
		preparedStatement.setString(3, users.getLastName());
		preparedStatement.setString(4, users.getPassword());
		preparedStatement.setString(5, users.getBirthday());
		preparedStatement.setString(6, users.getAddress_street_num());		
		preparedStatement.setString(7, users.getAddress_street());		
		preparedStatement.setString(8, users.getAddress_city());		
		preparedStatement.setString(9, users.getAddress_state());		
		preparedStatement.setString(10, users.getAddress_zip_code());		
		preparedStatement.setInt(11, users.getbalance());		
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public user getUser(String email) throws SQLException {
    	user user = null;
        String sql = "SELECT * FROM User WHERE email = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String birthday = resultSet.getString("birthday");
            String address_street_num = resultSet.getString("address_street_num"); 
            String address_street = resultSet.getString("address_street"); 
            String address_city = resultSet.getString("address_city"); 
            String address_state = resultSet.getString("address_state"); 
            String address_zip_code = resultSet.getString("address_zip_code"); 
            int balance = resultSet.getInt("balance");
            user = new user(email, firstName, lastName, password, birthday, address_street_num,  address_street,  address_city,  address_state,  address_zip_code, balance);
        }
         
        resultSet.close();
        statement.close();
         
        return user;
    }
    
    public boolean checkemail(String email) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE email = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE password = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    
    
    public boolean isValid(String email, String password) throws SQLException
    {
    	String sql = "SELECT * FROM User";
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("email").equals(email) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    public boolean increaseBal(String email, double amount) throws SQLException {
        String sql = "update User set eth_bal=? where email = ?";
        user currentUser = getUser(email);
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setDouble(1, currentUser.getbalance() + amount);
        preparedStatement.setString(2, email);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public boolean decreaseBal(String email, double amount) throws SQLException {
        String sql = "update User set eth_bal=? where email = ?";
        user currentUser = getUser(email);
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setDouble(1, currentUser.getbalance() - amount);
        preparedStatement.setString(2, email);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        System.out.println("This is user table initialization from USer.Dao Line 266");
        
        String[] INITIAL = {"drop database if exists testdb; ",
		        "create database testdb; ",
		        "use testdb; ",
		        "drop table if exists User; ",
		        ("CREATE TABLE if not exists User( " +
                    "email VARCHAR(50) NOT NULL, " +
		            "firstName VARCHAR(10) NOT NULL, " +
		            "lastName VARCHAR(10) NOT NULL, " +
		            "password VARCHAR(20) NOT NULL, " +
		            "birthday DATE NOT NULL, " +
		            "address_street_num VARCHAR(4) , " + 
		            "address_street VARCHAR(26) , " + 
		            "address_city VARCHAR(20)," + 
		            "address_state VARCHAR(2)," + 
		            "address_zip_code VARCHAR(5)," + 
		            "balance DECIMAL(13,2) DEFAULT 100," + 
		            "PRIMARY KEY (email) "+"); ")
				};
        
        String[] TUPLES = {("insert into User(email, firstName, lastName, password, birthday, address_street_num, address_street, address_city, address_state, address_zip_code, balance)"+
        		"values ('brent@gmail.com', 'Brent ', 'Goudie', 'brent1234', '2001-03-07', '5905', 'Garry Street,', 'Detroit', 'MI', '48202','1000'),"+
            		 	"('gon@gmail.com', 'Gon', 'Freeccs', 'gon1234', '1986-04-10', '532', 'Forest Street,', 'Whale Island', 'NC', '12345','1000'),"+
            	 	 	"('denji@gmail.com', 'Denji', 'Pochita','denji1234', '2000-10-26', '729', 'Control Road,', 'Windbloom','NY','12561','1000'),"+
            		 	"('jo@gmail.com', 'Jo', 'Hort','jo1234', '2001-06-23', '2915','University Street,', 'Auburn Hills', 'MI', '54321','1000'),"+
            		 	"('wallace@gmail.com', 'Wallace', 'Russel','wallace1234', '1963-06-15', '4500', 'Cheese Road,', 'Downy', 'WI', '48202','1000'),"+
            		 	"('megan@gmail.com', 'Megan', 'Corellia','megan1234', '2000-04-14', '1482', 'Masonic Street,', 'Royal Oak', 'MI', '48000','1000'),"+
            			"('nikolas@gmail.com', 'Nikolas', 'Crasiuc','nikolas1234', '2001-05-15', '5093', 'Grand Valley Street,', 'Wessin', 'OH', '24680','1000'),"+
            			"('franky@gmail.com', 'Franky', 'Robin','franky1234', '1994-09-12', '4680', 'Sunny Street,', 'Rosa', 'FL', '13579','1000'),"+
            			"('kakyoin@gmail.com', 'Kakyoin', 'Noriaki','kakyoin1234', '1986-06-05', '2058', 'Donut Road,', 'Grave','AR', '09876','1000'),"+
            			"('funny@gmail.com', 'Funny ', 'Valentine','funny1234', '1845-12-18', '4975', 'Flag Street,', 'New York City', 'NY', '87654','1000'),"+
            			"('root', 'default', 'default','pass1234', '0000-00-00', '0000', 'Default', 'Default', '0', '00000','1000');")
            			};
        
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        disconnect();
    }
}