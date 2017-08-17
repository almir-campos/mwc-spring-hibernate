<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@page import="java.util.Random"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        <meta http-equiv='cache-control' content='no-cache'>
                <meta http-equiv='expires' content='0'>
                <meta http-equiv='pragma' content='no-cache'>-->
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.datepicker.css' />" />
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/jquery-complementary.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/input18n-0.0.0.js' />" type="text/javascript" ></script>
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <!--PAGE LABELS-->
        <spring:message code="label_series_add_title" var="labelSeriesAddTitle" />
        <spring:message code="label_series_add_form" var="labelSeriesAddForm" />
        <spring:message code="label_series_add_data_legend" var="labelSeriesAddDataLegend" />
        <spring:message code="label_series_add_initial_photo_legend" var="labelSeriesAddInitialPhotoLegend" />
        <spring:message code="label_series_add_submit_button" var="labelSeriesAddSubmitButton" />
        <!--VARIABLES-->
        <c:set var='viewName' value='seriesAdd' />
        <c:set var='person' value="${mwcUser.person}" />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("seriesAdd", "${messageConfirmChangeLocale}", "${mwcUser.id}", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelSeriesAddTitle}</h1>
                <%@include file = '../complements/includes/showPersonBasicData.jspf' %>
                <form:form 
                    id="addSeriesForm" 
                    action="/series/add/${userId}"
                    commandName="series"
                    enctype="multipart/form-data"
                    method="post">
                    <%@include file = '../complements/includes/inputSeriesBasicData.jspf' %>
                </form:form>
                <button 
                    id="addSeriesBt"
                    class="confirmButton confirmSubmit"
                    formId="addSeriesForm">${labelConfirmButton}</button>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
            <%@include file = '../complements/includes/ajaxWait.jspf' %>
        </div>
    </body>
</html>