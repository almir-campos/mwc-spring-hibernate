<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title><%@ include file = "../complements/includes/noscript.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
        <!--PAGE MESSAGES-->
        <spring:message code="page.show.company.title" var = "pageShowCompanyTitle" />
        <!--VARIABLES-->
        <c:set var='viewName' value='companyShow' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("companyShow", "", "", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageShowCompanyTitle}</h1>
                <%@include file = '../complements/includes/showCompanyBasicData.jspf' %>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
        </div>
    </body>
</html>
