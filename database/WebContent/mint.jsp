<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Mint an NFT</title>
			<style>
				body {
					font-family: 'Roboto', sans-serif;
					background-color: rgb(253, 253, 150);
					padding: 5rem;
				}
				
				.box {
					background-color: rgb(253, 253, 150);
					/*filter: drop-shadow(0 4px 3px rgb(0 0 0 / 0.1)) drop-shadow(0 2px 2px rgb(0 0 0 / 0.1));*/
					border-radius: 0.25rem;
					display:flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					padding-bottom:1rem;
				}
				.button {
					position: relative;
					padding: .5rem;
					background-color: rgb(180, 211, 178);
					border-radius: 0.25rem;
					margin: 3rem;
					color: black;
				}
			</style>
	</head>
	<body>
		<div class="box">
			<h1>Mint an NFT!</h1>
			<form method="post" action="mint">
				<table border="1" cellpadding="5">
					<tr>
						<th>Name:</th>
						<td align="center" colspan="3">
							<input type="text" name="nftName" size="45" value="Give your NFT a name" onfocus="this.value=''">
						</td>
					</tr>
					<tr>
						<th>Description:</th>
						<td align="center" colspan="3">
							<input type="text" name="description" size="45"  value="Describe what your NFT looks like" onfocus="this.value=''">
						</td>
					</tr>
					<tr>
						<th>Image URL:</th>
						<td align="center" colspan="3">
							<input type="text" name="nftURL" size="45"  value="Paste the URL of the image you are turning into an NFT" onfocus="this.value=''">
						</td>
					</tr>
					<tr>
						<td align="center" colspan="5">
							<input type="submit" value="Mint"/>
						</td>
					</tr>
				</table>
			</form>
			
			<form action = "goBack">
			<br><br>
			<input type="submit" value="Activity Page"/>
			</form>
		
		</div>
	</body>
</html>