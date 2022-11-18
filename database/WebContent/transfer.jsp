<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Transfer an NFT</title>
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
			<h1>Transfer NFT to Another User</h1>
				<form method="post" action="endTransfer">
				<div class="wrap">
					<label>Select the NFT that you wish to transfer:</label>
					<select name="nftid">
				  		<c:forEach items="${listNft}" var="nfts" varStatus="loop">
							<option value="${nfts.nftid}">
						        <c:out value="${nfts.nftName}" />
						  	</option>
						</c:forEach>
					</select>
				</div>
				<div class="wrap">
					<label>Select the user that you would like to transfer the NFT to:</label>
					<select name="listUser">
				  		<c:forEach items="${allUsers}" var="users" varStatus="loop">
							<option value="${users.email}">
						        <c:out value="${users.email}" />
						  	</option>
						</c:forEach>
					</select>
				</div>
				<button type="submit">Transfer</button>
			</form>

			<form action = "goBack">
			<br><br>
			<input type="submit" value="Activity Page"/>
			</form>

		</div>
	</body>
</html>