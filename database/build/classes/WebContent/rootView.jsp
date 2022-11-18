<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Root page</title>
		<style>
		body {
				font-family: 'Roboto', sans-serif;
				background-color: rgb(253, 253, 150);
				padding: 5rem;
			}
			table {
				margin-bottom: 3rem;
			}
		</style>
	</head>
	<body>
		<div align = "center">
			
			<form action = "initialize">
				<input type = "submit" value = "Initialize the Database"/>
			</form>
			<a href="login.jsp"target ="_self" >Logout</a><br><br> 
		
			<h1>List all users</h1>
		    <div align="center">
		        <table border="1" cellpadding="6">
		            <caption><h2>List of Users</h2></caption>
		            <tr>
		                <th>Email</th>
		                <th>First name</th>
		                <th>Last name</th>
		                <th>Address</th>
		                <th>Password</th>
		                <th>Birthday</th>
		                <th>Balance(ETH)</th>
		            </tr>
		            <c:forEach var="users" items="${listUser}">
		                <tr style="text-align:center">
		                    <td><c:out value="${users.email}" /></td>
		                    <td><c:out value="${users.firstName}" /></td>
		                    <td><c:out value="${users.lastName}" /></td>
		                    <td><c:out value="${users.address_street_num} ${users.address_street} ${users.address_city} ${users.address_state} ${users.address_zip_code}"/></td>
		                    <td><c:out value="${users.password}" /></td>
		                    <td><c:out value="${users.birthday}" /></td>
		                    <td><c:out value="${users.balance}"/></td>
		            </c:forEach>
		        </table>
			</div>
			
			<h1>List all NFTs</h1>
		    <div align="center">
		        <table border="1" cellpadding="6">
		            <caption><h2>List of NFTs</h2></caption>
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
		</div>
	</body>
</html>