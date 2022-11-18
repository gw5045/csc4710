import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private nftDAO nftDAO = new nftDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    private ListingDAO ListingDAO = new ListingDAO();
	    private TransactionDAO TransactionDAO = new TransactionDAO();
	    
	    private Connection connect = null;
	    
	    
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
	    
	    
	    
	    
	    
	    
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	nftDAO = new nftDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        RequestDispatcher dispatcher = null;
	        System.out.println(action);
	    
	    try {
	    	// Routes
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		System.out.println("We are inside initialize on the switch case section");
        		userDAO.init();
        		nftDAO.init();
        		ListingDAO.init();
        		TransactionDAO.init();
        		System.out.println("Database successfully initialized!");
        		
        		rootPage(request,response,"");
        		break;
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);  
                 break;
        	 case "/listNft": 
                 System.out.println("The action is: list");
                 listNft(request, response);           	
                 break;
         	case "/mint":
        		mint(request,response);
        		request.setAttribute("userNFT", nftDAO.listOwnedNfts(currentUser));
         		dispatcher = request.getRequestDispatcher("activitypage.jsp");       
         		dispatcher.forward(request, response);
        		break;
         	case "/search":
         		listNft(request,response);
         		break;
         	case "/createListing":
         		System.out.println("The action is: Create Listing");
         		createListing(request,response);
         		break;
         	case "/listing":
         		System.out.println("Enter listing page");
         		listing(request, response);
         		break;
         		
         	case "/buy":
         		System.out.println("Preparing to buy current NFT");
         		buy(request, response);
         		break;
         	case "/beginTransfer":
         		System.out.println("Beginning to transfer NFT");
         		beginTransfer(request, response);
         		break;
         	case "/endTransfer":
         		endTransfer(request, response);
         		System.out.println("Completed transferring NFT");
         		break;
         	case "/listCurrentUserNft":
         		request.setAttribute("listNft", nftDAO.listOwnedNfts(currentUser));
         		dispatcher = request.getRequestDispatcher("activitypage.jsp");       
         		dispatcher.forward(request, response);
         		break;
         	case "/goBack":
         		goBack(request, response);
         		break;
         		
	    	}   
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
	    
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");

	     
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("userList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    
	    private void listNft(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	ListingDAO listingDAO = new ListingDAO();
	        System.out.println("listNFT started: 00000000000000000000000000000000000");

	     
	        List<nft> listNft = nftDAO.listAllNfts();
	        List<Listing> allListings = listingDAO.allListedNfts();
	        
	        request.setAttribute("listNft", listNft);       
	        request.setAttribute("allListings", allListings);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("nftList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listNFTs finished: 111111111111111111111111111111111111");
	    }
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String email = request.getParameter("email");
	    	 String password = request.getParameter("password");
	    	 
	    	 if (email.equals("root") && password.equals("pass1234")) {
				 System.out.println("Login Successful! Redirecting to root");
				 session = request.getSession();
				 session.setAttribute("email", email);
				 rootPage(request, response, "");
	    	 }
	    	 else if(userDAO.isValid(email, password)) 
	    	 {
			 	 user user = userDAO.getUser(email);
			 	connect_func();
			 	System.out.println("ControlServlet : Line 195: after connect_func()");
			 	 currentUser = email;
			 	 session = request.getSession();
			 	 session.setAttribute("currentUser", user.getEmail());
			 	 session.setAttribute("firstName", userDAO.getUser(email).getFirstName());
			 	 session.setAttribute("lastName", userDAO.getUser(email).getLastName());
			 	 //request.setAttribute("listNFT", nftDAO.listOwnedNfts(email));
			 	 System.out.println("Login Successful! Redirecting");
				 //request.setAttribute("listUser", userDAO.listAllUsers());
				 //request.getRequestDispatcher("activitypage.jsp").forward(request, response);
					 
			 	 
			 	 
			 	 //allows all nfts to be seen on the activity page after logging in
				System.out.println("act page view");
				request.setAttribute("listNft", nftDAO.listOwnedNfts(user.getEmail())); 
			    request.getRequestDispatcher("activitypage.jsp").forward(request, response);
			    System.out.println("act page view");
	    	 }
	    	 else {
	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
	    	 }
	    }
	    
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String email = request.getParameter("email");
	   	 	String firstName = request.getParameter("firstName");
	   	 	String lastName = request.getParameter("lastName");
	   	 	String password = request.getParameter("password");
	   	 	String birthday = request.getParameter("birthday");
	   	 	String address_street_num = request.getParameter("address_street_num"); 
	   	 	String address_street = request.getParameter("address_street"); 
	   	 	String address_city = request.getParameter("address_city"); 
	   	 	String address_state = request.getParameter("address_state"); 
	   	 	String address_zip_code = request.getParameter("address_zip_code"); 
	   	 	
	   	 	String confirm = request.getParameter("confirmation");
	   	 	
	   	 	if (password.equals(confirm)) {
	   	 		if (!userDAO.checkemail(email)) {
		   	 		System.out.println("Registration Successful! Added to database");
		            user users = new user(email,firstName, lastName, password, birthday, address_street_num,  address_street,  address_city,  address_state,  address_zip_code, 100);
		   	 		userDAO.insert(users);
		   	 		response.sendRedirect("login.jsp");
	   	 		}
		   	 	else {
		   	 		System.out.println("Username taken, please enter new username");
		    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
		    		 request.getRequestDispatcher("register.jsp").forward(request, response);
		   	 	}
	   	 	}
	   	 	else {
	   	 		System.out.println("Password and Password Confirmation do not match");
	   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	   		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
	    } 
	    
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view Function at like 230 has been called");
			 request.setAttribute("listUser", userDAO.listAllUsers());
			 request.setAttribute("listNft", nftDAO.listAllNfts());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    } 
	    
	    
	    //lines of code that let the target website access the list of nfts to display. this is commented out because we integrated the code straight into functions.
	    
	    /*
	    private void activityPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("act page view");
			request.setAttribute("listNft", nftDAO.listAllNfts());
	    	request.getRequestDispatcher("activitypage.jsp").forward(request, response); //Root view to see how c:forEach is connected
	    } 
	    */
	    
	    
	    
	    
	    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        }	
	    
	    
	    
	    
	    private void listing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	        System.out.println("open listing page started: 00000000000000000000000000000000000");
	        String owner = (String)session.getAttribute("currentUser");
	        request.setAttribute("listNft", nftDAO.listOwnedNfts(owner)); 	        
	        request.getRequestDispatcher("Listings.jsp").forward(request, response);
	     
	        System.out.println("listOwnedNfts finished: 111111111111111111111111111111111111");
	    }
	    
	    
	    
	    
	    
	    private void createListing(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("Listings.jsp");
	    	int nftid= Integer.parseInt(request.getParameter("nftid"));
	    	int lengthoftime= Integer.parseInt(request.getParameter("lengthoftime"));
	    	double price= Double.parseDouble(request.getParameter("price"));
	    	ListingDAO listingDAO = new ListingDAO();
	    	
	    	if (price<=0) {
	    		request.setAttribute("userNFT", nftDAO.listOwnedNfts(currentUser));
	    		System.out.println("we are at 324");
	    		request.setAttribute("error!", "Price of lissting myst be greater than 0! ");
	    		System.out.println("we are at 326");
	    		dispatcher.forward(request, response);
	    		System.out.println("we are at 328");
	    	}
	    	else if(listingDAO.getListedNft(nftid)!=null) {
	    		request.setAttribute("userNFT", nftDAO.listOwnedNfts(currentUser));
	    		System.out.println("we are at 333");
	    		request.setAttribute("error!", "A listing for this NFT already exists! ");
	    		System.out.println("we are at 335");
	    		dispatcher.forward(request, response);
	    		System.out.println("we are at 337");
	    	}
	    	else {
	    		Calendar cvar= Calendar.getInstance();
	    		System.out.println("we are at 340");
	    		cvar.add(Calendar.MONTH, lengthoftime);
	    		System.out.println("we are at 342");
	    		Date start= new Date();
	    		System.out.println("we are at 344");
	    		Date end=cvar.getTime();
	    		System.out.println("we are at 346");
	    		Timestamp startTime=new Timestamp(start.getTime());
	    		System.out.println("we are at 348");
	    		Timestamp endTime= new Timestamp(end.getTime());
	    		System.out.println("we are at 350");
	    		
	    		listingDAO.insert(new Listing(currentUser,nftid,startTime, endTime, price));
	    		System.out.println("we are at 353");
	    		request.setAttribute("userNFT", nftDAO.listOwnedNfts(currentUser));
	    		System.out.println("we are at 355");
	    		request.setAttribute("success!", "Listing has been Created!");
	    		System.out.println("we are at 357");
	    		dispatcher = request.getRequestDispatcher("Listings.jsp"); 
	    		System.out.println("we are at 359");
	    		dispatcher.forward(request, response);
	    		System.out.println("we are at 361");
	    		
	    	}
	    }
	    
	    private void mint(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {	   	 	
	    	String nftName = request.getParameter("nftName");
	   	 	String description = request.getParameter("description");
	   	 	String nftURL = request.getParameter("nftURL");
	   	 	String owner = (String)session.getAttribute("currentUser");
	   	 	String creator = (String)session.getAttribute("currentUser");
	    	java.util.Date currentDate = new java.util.Date();
	    	Timestamp nftMintTime = new Timestamp(currentDate.getTime());
	    	
	   	 	nft nfts = new nft(nftName, description, nftURL, owner, creator, nftMintTime);
	   	 	nftDAO.insert(nfts);
	   	 	
	   	 System.out.println("Saved to NFT database");
		    System.out.println(creator);
		    System.out.println(owner);
		   
		    //shows all nfts on activity page after pressing mint --- is there and easier way to display all nfts without placing this in every function?
		    System.out.println("act page view");
		    request.setAttribute("listNft", nftDAO.listOwnedNfts(owner)); 
		    request.getRequestDispatcher("activitypage.jsp").forward(request, response);
		    
		    
		   	//response.sendRedirect("activitypage.jsp");
	    } 
	    
	    
	    
	    //buy function, made it but not sure it works tbh
	    
	    private void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	Date currentTime = new Date();
	    	Timestamp timeStamp = new Timestamp(currentTime.getTime());
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp"); 
	    	
	    	int nftid = Integer.parseInt(request.getParameter("nftid"));
	    	user currentBuyer = userDAO.getUser(currentUser);
	    	Listing list = ListingDAO.getListedNft(nftid);
	    	
	    	if (list != null) {
	    		// Error handling
//	    		if (list.getEnd().compareTo(timeStamp) < 0) {
//	    			
//	    		}
	    		
	    		// Buy and Sell NFT
	    		userDAO.increaseBal(list.getOwner(), list.getPrice());
	    		userDAO.decreaseBal(currentUser, list.getPrice());
	    		ListingDAO.delete(nftid);
	    		nftDAO.updateOwner(nftid, currentUser);
	    		
	    		//Transaction
	    		Transaction newTransfer = new Transaction(list.getOwner(), currentUser, timeStamp, list.getPrice(), "sale");
	    		TransactionDAO.insert(newTransfer);
	    		
		    	request.setAttribute("listNFT", nftDAO.listAllNfts());  
		        request.setAttribute("allListings", ListingDAO.allListedNfts());  
		        dispatcher.forward(request, response);
		    	return;
	    	}
	    }
	    
	    
	    
	    //starts transfer from one user to another (supposedly bc transfer doesn't work yet)
	    
	    private void beginTransfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	        System.out.println("open transfer page started: 00000000000000000000000000000000000");
	        String owner = (String)session.getAttribute("currentUser");
	        request.setAttribute("listNft", nftDAO.listOwnedNfts(owner)); 
	        request.setAttribute("allUsers", userDAO.listAllUsers());
	        
	        
	        request.getRequestDispatcher("transfer.jsp").forward(request, response);
	        //RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");       
	        //dispatcher.forward(request, response);
	     
	        System.out.println("listUsersNfts finished: 111111111111111111111111111111111111");
	    }
	    
	    
	    
	    
	    //ends transfer from one user to another (supposedly bc transfer doesn't work yet)
	    
	    private void endTransfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	int nftid = Integer.parseInt(request.getParameter("nftid"));
	    	String reciever = request.getParameter("listUser");
	    	System.out.println(reciever + " line 413 of endTransfer");
	    	Date currentTime = new Date();
	    	Timestamp timeStamp = new Timestamp(currentTime.getTime());
	    	
	    	ListingDAO.delete(nftid);
	    	nftDAO.updateOwner(nftid, reciever);
	    	Transaction newTransfer = new Transaction(currentUser, reciever, timeStamp, (double) 0, "transfer");
	    	TransactionDAO.insert(newTransfer);
	    	
	    	request.setAttribute("userNFTs", nftDAO.listOwnedNfts(currentUser));
	        request.setAttribute("allUsers", userDAO.listAllUsers());
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");  
	        dispatcher.forward(request, response);
	    }
	    
	    
	    
	    private void goBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String owner = (String)session.getAttribute("currentUser");
	    	System.out.println("go back to act page view");
		    request.setAttribute("listNft", nftDAO.listOwnedNfts(owner)); 
		    request.getRequestDispatcher("activitypage.jsp").forward(request, response);
	    
	    }
}