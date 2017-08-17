<%-- 
    Document   : activationRequestConfirmation
    Created on : Oct 4, 2013, 4:01:47 PM
    Author     : Almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <!--PAGE MESSAGES-->
        <spring:message code="activation.request.confirmation.title" var="activationRequestConfirmationTitle" />
        <spring:message code="activation.request.confirmation.text" var="activationRequestConfirmationText" />
        <spring:message code="activation.request.confirmation.link" var="activationRequestConfirmationLink" />
        <!--VARIABLES-->
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div class='leftPanelDiv'></div>
            <div class='mainDiv'>
                <%@ include file = "../complements/includes/outterTopBar.jspf" %>
                <h1>${activationRequestConfirmationTitle}</h1>
                ${activationRequestConfirmationText}
                <br />
                <br />
                <a href="<c:url value='/public/login'/>">${activationRequestConfirmationLink}</a>
            </div>
        </div>
    </body>
</html>
