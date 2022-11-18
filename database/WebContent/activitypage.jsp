<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Activity Page</title>
<style>
	body {
		background-color: rgb(253, 253, 150);
		text-align: center;
		margin: auto;
  		width: 50%;
		padding: 10px;
	}
	.Button {
		padding: .5rem;
		background-color: rgb(255, 105, 97);
		border-radius: 0.25rem;
		margin: 1rem;
		color: black;
	}
	.Button2 {
		padding: .5rem;
		background-color: rgb(119, 158, 215);
		border-radius: 0.25rem;
		margin: 1rem;
		color: black;
	}
	.container {
		display: flex;
		
	}
	.table-container {
		margin-left:auto;
		margin-right:auto;
	}
</style>
</head>

		
		<%
			/* if (session != null){
				if(session.getAttribute("email") != null){
					String userEmail = (String) session.getAttribute("email");
					String firstName = (String) session.getAttribute("firstName");
					String lastName = (String) session.getAttribute("lastName");
				}
				else{
					response.sendRedirect("login.jsp");
				}
			}*/
			%>

	<body>
			<h1>Welcome ${firstName} ${lastName}!</h1>

		 <a class="Button" href="login.jsp"target ="_self" >Logout</a><br><br> 
		 <div class=container>
			 <a class="Button2" href="mint.jsp"target ="_self" > Mint NFT</a>
			 <a class="Button2" href="nftList.jsp"target ="_self" >Listed NFTs</a>
			 <a class="Button2" href="search.jsp"target ="_self" > Search for an NFT</a>
			 <a class="Button2" href="listing"target ="_self" >List an NFT</a>
			 <a class="Button2" href="beginTransfer"target ="_self" >Transfer an NFT</a>
		 </div>
		
		        <h1>Your NFTs</h1>
		    <div align="center">
		        <table border="1" cellpadding="6">
		            <caption>List of Owned NFTs</caption>
		            <tr>
		                <th>NFT ID</th>
		                <th>Name</th>
		                <th>Description</th>
		                <th>Image</th>
		                <th>Owner</th>
		                <th>Creator</th>
		                <th>Date created</th>
	
		            </tr>
		            <c:forEach var="nfts" items="${listNft}">
		                <tr style="text-align:center">
		                    <td><c:out value="${nfts.nftid}" /></td>
		                    <td><c:out value="${nfts.nftName}" /></td>
		                    <td><c:out value="${nfts.description}" /></td>
		                    <td><img style="width:100%; max-width:500px;" src="${nfts.nftURL}" alt="${nfts.nftURL}"><a href=<c:out value="${nfts.nftURL}"/> target=_blank><br>Image Link</a></td>
		                    <td><c:out value="${nfts.owner}" /></td>
		                    <td><c:out value="${nfts.creator}" /></td>
		                    <td><c:out value="${nfts.nftMintTime}" /></td>
		            </c:forEach>
		        </table>
			</div>
	</body>
</html>