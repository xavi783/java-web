<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Usukuma</title>
	<link href="<c:url value="/resources/css/styleguide.css"/>" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="<c:url value="/resources/js/main.js"/>"></script>
</head>
<body class=".bg-404">

	<%@ include file="../includes/header.jsp"%>
	
	<main class="main-404">		
		<img class="image-404 col-10" src="<c:url value="/resources/images/404.png"/>" alt="404 Not Found">
	</main>

</body>
</html>