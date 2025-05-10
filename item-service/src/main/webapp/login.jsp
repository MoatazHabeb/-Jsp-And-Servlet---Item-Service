<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login & Sign Up</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%
    // Redirect if already authenticated
    if (session != null && session.getAttribute("isAuthenticated") != null && 
        (Boolean)session.getAttribute("isAuthenticated")) {
        response.sendRedirect("ItemController?action=LOAD_ITEMS");
        return;
    }
%>
<%
    // Check if there is a cookie for "username"
    Cookie[] cookies = request.getCookies();
    boolean isAuthenticated = false;
    String username = null;

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("usernamee".equals(cookie.getName())) {
                username = cookie.getValue();
                // If username cookie exists, authenticate the user
                isAuthenticated = true;
                break;
            }
        }
    }

    // If authenticated, redirect to the items page
    if (isAuthenticated) {
        response.sendRedirect("ItemController?action=LOAD_ITEMS");
        return;
    }
%>
<div class="login-wrap">
    <div class="login-html">
        <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
        <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
        <div class="login-form">
            <form class="sign-in-htm" action="ItemController" >
                <div class="group">
                    <label for="user1" class="label">Username</label>
                    <input id="user1" type="text" name="usernamein" class="input" required>
                </div>
                <div class="group">
                    <label for="pass2" class="label">Password</label>
                    <input id="pass2" type="password" name="passwordin" class="input" data-type="password" required>
                </div>

                <input type="hidden" name="action" value="Sign_In">
                <div class="group">
                    <input type="submit" class="button" value="Sign In">
                </div>
                <div class="hr"></div>
                <c:if test="${param.error == 'invalid'}">
                    <div class="error-message">Invalid username or password</div>
                </c:if>
            </form>
            
            <form class="sign-up-htm" action="ItemController" >
                <div class="group">
                    <label for="user" class="label">Username</label>
                    <input id="user" type="text" name="usernameup" class="input" required>
                </div>
                <div class="group">
                    <label for="pass" class="label">Password</label>
                    <input id="pass" type="password" name="passwordup" class="input" data-type="password" required>
                </div>
                <input type="hidden" name="action" value="Sign Up">
                <div class="group">
                    <input type="submit" class="button" value="Sign Up">
                </div>
                <div class="hr"></div>
            </form>
        </div>
    </div>
</div>
</body>
</html>