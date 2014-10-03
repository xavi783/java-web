<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/login" var="loginUrl"/>
<c:url value="/signgin" var="signinUrl"/>

<div id="login-form" class="col-4 user-form">
    <form action="${loginUrl}" id="login" method="post">
        <div class="field-container">
            <input required name="username" type="text" class="username">
            <label for="text">username</label>
        </div>
        <div class="field-container">
            <input required name="password" type="password">
            <label for="password">password</label>
        </div>
        <div class="error-hook"></div>
        <div>
            <div id="login-btn" class="row last button-pill-glass user-btn">Log in</div>
            <span class="twiterlog user-btns" data-icon="&#xe00f;"></span>
            <span class="facebooklog user-btns" data-icon="&#xe00e;"></span>
            <span class="googlelog user-btns" data-icon="&#xe010;"></span>
        </div>
    </form>
</div>

<div id="signin-form" class="col-4 user-form">
    <form action="${signinUrl}" id="signin" method="post">
        <div class="field-container">
            <input required name="username" type="text" class="username">
            <label for="text">username</label>
        </div>
        <div class="field-container">
            <input required name="email" type="email">
            <label for="email">email</label>
        </div>
        <div class="field-container">
            <input required name="password" type="password">
            <label for="password">password</label>
        </div>
        <div class="error-hook"></div>
        <div>
            <div id="signin-btn" class="row last button-pill-glass user-btn">Sign in</div>
            <span class="twiterlog user-btns" data-icon="&#xe00f;"></span>
            <span class="facebooklog user-btns" data-icon="&#xe00e;"></span>
            <span class="googlelog user-btns" data-icon="&#xe010;"></span>
        </div>
    </form>
</div>