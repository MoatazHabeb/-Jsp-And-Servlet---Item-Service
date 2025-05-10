<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AMAZON</title>
    <link rel="stylesheet" href="css/showI.css">
</head>
<body>

<% 
 String username = null;
    boolean isAuthenticated = false;

    // 1. Check for existing session
    if (session != null && session.getAttribute("isAuthenticated") != null && (Boolean) session.getAttribute("isAuthenticated")) {
        username = (String) session.getAttribute("username");
        isAuthenticated = true;
    } else {
        // 2. If no session, check for the cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("usernamee")) {
                    username = cookie.getValue();
                    isAuthenticated = true;

                    // 3. IMPORTANT: Recreate the session if using cookie
                    session = request.getSession(true); // Create a new session
                    session.setAttribute("username", username);
                    session.setAttribute("isAuthenticated", true);
                    break;
                }
            }
        }
    }

    // If not authenticated, redirect to login
    if (!isAuthenticated) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // User is authenticated, welcome message
    out.println("Welcome " + username);
%>


<div class="layer">
    <div class="header">
        <h1>Items</h1>
        <div class="user-info">
            Welcome, ${sessionScope.username}! 
            <a href="ItemController?action=SIGN_OUT" class="logout-btn">Sign Out</a>
        </div>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>PRICE</th>
                <th>TOTAL_NUMBER</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${allIt}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.totalNumber}</td>
                    <td>
                        <a href="ItemController?action=LOAD_ITEM&id=${item.id}">Update</a>
                        <a href="ItemController?action=DELETE&id=${item.id}">Delete</a>
                        <a href="ItemController?action=LOAD_ITEMS_DETAILS&id=${item.id}">Update ITEM DETAILS</a>
                        <c:if test="${empty item.description}">
                            <a href="add-item-details.jsp?id=${item.id}">ADD ITEM DETAILS</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="actions">
        <button class="f"><a href="add-item.html">Add Item</a></button>
        <button class="f"><a href="ItemController?action=LOAD_ITEM_DETAILS">Show All Details</a></button>
    </div>
</div>
</body>
</html>