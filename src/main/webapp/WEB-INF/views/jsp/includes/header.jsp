<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header class="header-main">
	<div class="main">
		<a href="<c:url value="/"/>">
			<div class="col-3 front-panel-logo">
				<div class="centered" id="logo"></div>
			</div>
		</a>
		<div class="col-9 content-flow">
			<form action="" class="col-8 searchbox field-container">
				<input class="p" type="search" name="searchbox-main">
				<label class="h6" for="searchbox-main">Search</label>
			</form>
			<form action="" class="col-4 panel-login">
				<sec:authorize access="isAnonymous()">
					<input id="show-signin-form"  class="button1" type="button" value="Sign In">
					<input id="show-login-form" class="button1" type="button" value="Login">
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<a href="<c:url value="/profile"/>">
						<div class="off-1 col-11 p profile" data-icon="&#xe008;"><sec:authentication property="principal.username"/></div>
					</a>
					<a href="<c:url value="/logout"/>">
						<div class="off-1 col-11 p logout-btn" data-icon="&#xe00a;"> Logout</div>
					</a>
				</sec:authorize>
			</form>
		</div>
	</div>
	<sec:authorize access="isAnonymous()">
		<%@ include file="loggingForm.jsp"%>
	</sec:authorize>
</header>

