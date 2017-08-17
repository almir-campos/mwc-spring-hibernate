<%-- 
    Document   : userLog
    Created on : Aug 24, 2013, 3:48:45 PM
    Author     : Almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
    </head>
    <body>
        <div class="contentDiv">
                <div id="rootMenuDiv">
                    <jsp:include page='../complements/includes/genericmenu.jsp' />
                    <!--<script>menuBuilder("userLog", "", ${loggedUser.id}, "");</script>-->
                </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>Log</h1>
                <div id="logDiv"></div>
            </div>
        </div>
        <script>
              $("#logDiv").load("<c:url value='/mwcUser/loadUserLog/${loggedUser.id}' />")
        </script>
    </body>
</html>
