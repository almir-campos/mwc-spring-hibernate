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
        <!--PAGE LABELS-->
        <spring:message code="label_series_show_title" var="labelSeriesShowTitle" />
        <spring:message code="label_series_show_data_legend" var="labelSeriesShowDataLegend" />
        <spring:message code="label_series_show_initial_photo_legend" var="labelSeriesShowInitialPhotoLegend" />
         <!--VARIABLES-->
        <c:set var='viewName' value='seriesShow' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("seriesShow", "", "${series.mwcUser.id}", "${series.id}");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelSeriesShowTitle}</h1>
                <%@include file = '../complements/includes/showPersonBasicData.jspf' %>
                <%@include file = '../complements/includes/showSeriesBasicData.jspf' %>
                <!--                <fieldset>
                                    <legend>${labelSeriesShowInitialPhotoLegend}</legend>
                                    <img class="picture" src="<c:url value='/series/seriesInitialPhoto/${series.id}' />" />
                                </fieldset>-->
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
        </div>
    </body>
</html>
