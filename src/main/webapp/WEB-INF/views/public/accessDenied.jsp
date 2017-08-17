<%-- 
    Document   : accessDenied
    Created on : Aug 19, 2013, 1:40:06 PM
    Author     : Almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title><%@ include file = "../complements/includes/noscript.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
    </head>
    <body>
        <div class="contentDiv">
            <div class='leftPanelDiv'></div>
            <div class='mainDiv'><%@ include file = "../complements/includes/outterTopBar.jspf" %>
                <h1>Access Denied</h1>
                ${errorCode}
                <br />
                <br />
                <a href="<c:url value='/mwcUser/show'/>">Ok. I got it.</a>
            </div>
        </div>
    </body>
</html>
