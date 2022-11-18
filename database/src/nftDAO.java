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
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**S
 * Servlet implementation class Connect
 */
@WebServlet("/nftDAO")
public class nftDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public nftDAO(){}
	
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
    

    public List<nft> listAllNfts() throws SQLException {
        List<nft> listNft = new ArrayList<nft>();        
        String sql = "SELECT * FROM NFT";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        try {
        	while (resultSet.next()) {
        		int nftid = resultSet.getInt("nftid");
        		String nftName = resultSet.getString("nftName");
        		String description = resultSet.getString("description");
        		String nftURL = resultSet.getString("nftURL");
        		String owner = resultSet.getString("owner");
        		String creator = resultSet.getString("creator");
        		java.sql.Timestamp nftMintTime = resultSet.getTimestamp("nftMintTime");
        		
        		
        		nft nfts = new nft(nftid, nftName, description, nftURL, owner, creator, nftMintTime);
        		listNft.add(nfts);
        	}        
        } catch(Exception e) {
        	throw new SQLException(e);
        }

        resultSet.close();
        disconnect();        
        return listNft;
    }
    
    public List<nft> listOwnedNfts(String email) throws SQLException {
        List<nft> listOwnedNfts = new ArrayList<nft>();  
        String sql = "SELECT * FROM NFT WHERE owner = " + "'" + email + "'";
        
        connect_func();      
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        System.out.println(preparedStatement);
    	ResultSet resultSet = preparedStatement.executeQuery(sql);
    	
           try {
        	
        	while (resultSet.next()) {
        		int nftid = resultSet.getInt("nftid");
        		String nftName = resultSet.getString("nftName");
        		String description = resultSet.getString("description");
        		String nftURL = resultSet.getString("nftURL");
        		String owner = resultSet.getString("owner");
        		String creator = resultSet.getString("creator");
        		java.sql.Timestamp nftMintTime = resultSet.getTimestamp("nftMintTime");
        		
        		
        		nft nfts = new nft(nftid, nftName, description, nftURL, owner, creator, nftMintTime);
        		listOwnedNfts.add(nfts);
        	}        
        } catch(Exception e) {
        	throw new SQLException(e);
        }
        resultSet.close();
        disconnect();       
        return listOwnedNfts;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(nft nfts) throws SQLException {
    	connect_func();         
		String sql = "insert into NFT(nftName, description, nftURL, owner, creator, nftMintTime) values (?, ?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, nfts.getnftName());
		preparedStatement.setString(2, nfts.getDescription());
		preparedStatement.setString(3, nfts.getnftURL());	
		preparedStatement.setString(4, nfts.getOwner());	
		preparedStatement.setString(5, nfts.getCreator());	
		preparedStatement.setObject(6, nfts.getnftMintTime());	
		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String nftid) throws SQLException {
        String sql = "DELETE FROM NFT WHERE nftid = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, nftid);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(nft nfts) throws SQLException {
        String sql = "update NFT set nftName = ?, description = ?, nftURL= ?, owner = ?, creator = ?, nftMintTime = ? where nftid = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, nfts.getnftName());
		preparedStatement.setString(2, nfts.getDescription());
		preparedStatement.setString(3, nfts.getnftURL());	
		preparedStatement.setString(4, nfts.getOwner());	
		preparedStatement.setString(5, nfts.getCreator());	
		preparedStatement.setObject(6, nfts.getnftMintTime());	
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public boolean updateOwner(int nftid, String newOwner) throws SQLException {
        //String sql = "update NFT set owner = ? where nftID = ?";
        String sql = "update NFT set owner = " + "'" + newOwner + "'" + " where nftID = " + "'" + nftid + "'";
       	connect_func();
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        
        //System.out.println("nftDAO : updateOwner : line 172");
        //preparedStatement.setString(1, newOwner);
        //System.out.println("nftDAO : updateOwner : line 174");
        //preparedStatement.setInt(2, nftid);
        
        System.out.println(sql);
        System.out.println("updateOwner : line182");
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    
    public nft getNft(String nftid) throws SQLException {
    	nft nft = null;
        String sql = "SELECT * FROM NFT WHERE nftid = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, nftid);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String nftName = resultSet.getString("nftName");
            String description = resultSet.getString("description");
            String nftURL = resultSet.getString("nftURL");
            String owner = resultSet.getString("owner");
            String creator = resultSet.getString("creator");
            java.sql.Timestamp nftMintTime = resultSet.getTimestamp(nftid);
            nft = new nft(nftName, description, nftURL, owner, creator, nftMintTime);
        }
         
        resultSet.close();
        statement.close();
         
        return nft;
    }
    
    public boolean checknftid(String nftid) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM NFT WHERE nftid = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, nftid);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        // Explicit cast object to string
        Date currentTime = new Date();
        java.sql.Timestamp obj = new java.sql.Timestamp(currentTime.getTime());
        String nftMintTime = obj.toString();
        
        String[] INITIAL = {"use testdb; ",
        		"drop table if exists NFT; ",
		        ("CREATE TABLE if not exists NFT( " +
                    "nftid INTEGER NOT NULL AUTO_INCREMENT, " +
		            "nftName VARCHAR(100) NOT NULL, " +
		            "description VARCHAR(100) NOT NULL, " +
		            "nftURL VARCHAR(300) NOT NULL, " +
		            "owner VARCHAR(100), " +
		            "creator VARCHAR(100), " +
		            "nftMintTime DATETIME, " +
		            "PRIMARY KEY (nftid) "+
		            //"FOREIGN KEY (owner) REFERENCES User(email), " +
		            //"FOREIGN KEY (creator) REFERENCES User(email) " +
		           "); ")
				};
        
        String[] TUPLES = {("insert into NFT(nftid, nftName, description, nftURL, owner, creator, nftMintTime)"+
    			"values ('1', 'Bowl Cat', 'A cat curled up in a bowl', 'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/cute-photos-of-cats-curled-up-sleeping-1593184773.jpg', 'brent@gmail.com', 'brent@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('2', 'Bubble Tea Corgi', 'A corgi wearing sunglasses and drinking bubble tea', 'https://media.istockphoto.com/id/1285999285/vector/cute-welsh-corgi-dog-with-sunglasses-drinking-bubble-tea-cartoon-vector-illustration.jpg?s=612x612&w=0&k=20&c=Eg4oOLL28FRroUGUsYKTarGOfk7RD_DBqqTspt69Ld8=', 'gon@gmail.com', 'gon@gmail.com','" + nftMintTime + "')," +
    	    		 	"('3', 'Bunny Burger', 'A burger that looks like a bunny', 'https://images.coplusk.net/project_images/208627/image/2019-11-27-210132-burger2.jpg', 'denji@gmail.com', 'denji@gmail.com','" + nftMintTime + "')," +
    	    		 	"('4', 'Vitruvian Man', 'An image of Leonardo da Vincis Vitruvian man', 'https://mymodernmet.com/wp/wp-content/uploads/2018/08/leonardo-da-vinci-vitruvian-man-2.jpg', 'megan@gmail.com', 'jo@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('5', 'Space Ship', 'An image depicting a sci fi space ship', 'https://www.scififantasyhorror.co.uk/wp-content/uploads/2021/09/mohamed-reda-sci-fi-workshop-feature.jpeg', 'jo@gmail.com', 'wallace@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('6', 'Sakura Lake', 'An image depicting a sakura tree above a lake', 'http://cdn.shopify.com/s/files/1/1744/3435/articles/shutterstock_410271079_1024x1024.jpg?v=1624398205', 'megan@gmail.com', 'megan@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('7', 'Skeleton Yokai', 'An image of the great skeleton yokai of Japanese folklore', 'https://i.pinimg.com/originals/f5/c5/09/f5c50957269aa651aff014ed8da99d56.jpg', 'denji@gmail.com', 'nikolas@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('8', 'Japanese Beetle ', 'A depiction of a Japanese beetle', 'https://images.fineartamerica.com/images-medium-large-5/japanese-beetle-shana-rowe.jpg', 'gon@gmail.com', 'franky@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('9','Cyberpunk Swordsman', 'A cyberpunk character with two swords', 'https://img.freepik.com/premium-vector/cyberpunk-character-with-two-sword_284825-368.jpg?w=2000', 'jeannette@gmail.com', 'jeannette@gmail.com', '" + nftMintTime + "')," +
    	    		 	"('10', 'Black Cat Witch', 'A black cat wearing a witches hat', 'https://as1.ftcdn.net/v2/jpg/02/45/88/20/1000_F_245882059_9jVsBAYmkCpZmpfOXu0VfaYLQh2e2FW2.jpg', 'denji@gmail.com', 'funny@gmail.com', '" + nftMintTime + "')," +
    					"('0', 'root', 'default', 'default', 'default', 'default', '" + nftMintTime + "');")
    	    			};
        
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        disconnect();
    }
    
}