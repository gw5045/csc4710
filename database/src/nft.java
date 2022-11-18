public class nft {
		protected int nftid;
	    protected String nftName;
	    protected String description;
	    protected String nftURL; 
	    protected String owner;
	    protected String creator;
	    protected java.sql.Timestamp nftMintTime;
	    
	    //constructors
	    public nft() {
	    }
	 
	    public nft(int nftid) {
	        this.nftid = nftid;
	    }
	    
	    public nft(int nftid, String nftName, String description, String nftURL, 
	    		String owner, String creator, java.sql.Timestamp nftMintTime) {
	    	this(nftName, description, nftURL, owner, creator, nftMintTime);
	    	this.nftid = nftid;
	    }
	 
	    public nft(String nftName, String description, String nftURL, 
	    		String owner, String creator, java.sql.Timestamp nftMintTime) {
	    	this.nftName = nftName;
	    	this.description = description;
	        this.nftURL = nftURL;
	        this.owner = owner;
	        this.creator = creator;
	        this.nftMintTime = nftMintTime;
	    }
	    
	   //getter and setter methods
        public int getNftid() {
	        return nftid;
	    }
	    public void setNftid(int nftid) {
	        this.nftid = nftid;
	    }

	    public String getnftName() {
	        return nftName;
	    }
	    public void setnftName(String nftName) {
	        this.nftName = nftName;
	    }
	    
	    public String getDescription() {
	        return description;
	    }
	    public void setDescription(String description) {
	        this.description = description;
	    }
	    public String getnftURL() {
	        return nftURL;
	    }
	    public void setnftURL(String nftURL) {
	        this.nftURL = nftURL;
	    }
	    public String getOwner() {
	        return owner;
	    }
	    public void setOwner(String owner) {
	        this.owner = owner;
	    }
	    public String getCreator() {
	        return creator;
	    }
	    public void setCreator(String creator) {
	        this.creator = creator;
	    }
	    public java.sql.Timestamp getnftMintTime() {
	    	return nftMintTime;
	    }
	    public void setnftMintTime(java.sql.Timestamp nftMintTime) {
	    	this.nftMintTime = nftMintTime;
	    }
	}