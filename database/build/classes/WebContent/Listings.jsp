<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>List an NFT to Sell</title>
</head>


<style>
	body {
				font-family: 'Roboto', sans-serif;
				background-color: rgb(253, 253, 150);
				padding: 5rem;
			}
	.wrap{
	background-color: rgb(253, 253, 150);
	padding: 1rem;
	align-items: center;
}
</style>


<body>
<div align="center">
	<h1>Create a listing:</h1>
	
	<form method="post" action="createListing">
		<div class="wrap">
			<label>Select an NFT you would like to list:</label>
			<select name="nftid">
		  		<c:forEach items="${listNft}" var="nfts" varStatus="loop">
					<option value="${nfts.nftid}">
				        <c:out value="${nfts.nftName}"/>
				  	</option>
				</c:forEach>
			</select>
		</div>
		<div class="wrap">
			<label>How long would you like to list it? (Months)</label>
			<select name="lengthoftime">
		  		<c:forEach var="i" begin = "1" end = "12">
					<option value="${i}">
				        <c:out value="${i}"/>
				  	</option>
				</c:forEach>
			</select>
		</div>
		<div class="wrap">
			<label>Price (Etherium)</label>
			<input type="number" name="price" step="0.001" value=".01" required>
		</div>
		<button type="submit">Create Listing</button>
	</form>
	
	<form action = "goBack">
			<br><br>
			<input type="submit" value="Activity Page"/>
	</form>
			
</div>
</body>
</html>