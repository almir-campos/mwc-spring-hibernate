<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <!--PAGE MESSAGES-->
        <!--LABELS-->
        <spring:message code="label_user_show_title" var = "labelUserShowTitle" />
        <spring:message code="label_user_show_legend_identification" var = "labelUserShowLegendIdentification" />
        <spring:message code="label_user_show_legend_avatar" var = "labelUserShowLegendAvatar" />
        <spring:message code="label_user_show_legend_options" var = "labelUserShowLegendOptions" />
        <!--FIELDS-->
        <spring:message code="label_user_id" var = "labelUserId" />
        <spring:message code="label_user_first_name" var = "labelUserFirstName" />
        <spring:message code="label_user_last_name" var = "labelUserLastName" />
        <spring:message code="label_user_display_name" var = "labelUserDisplayName" />
        <spring:message code="label_user_username" var = "labelUserUsername" />
        <spring:message code="label_user_password" var = "labelUserPassword" />
        <spring:message code="label_user_birth_date" var = "labelUserBirthDate" />
        <spring:message code="label_user_preferred_language" var = "labelUserPreferredLanguage" />
        <!--VARIABLES-->
        <c:set var='viewName' value='userShow' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <c:set var="person" value="${mwcUser.person}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("userShow", null, "", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelUserShowTitle}</h1>
                <%@include file = '../complements/includes/showPersonBasicData.jspf' %>
                <fieldset>
                    <legend>${labelUserShowLegendAvatar}</legend>
                    <% Double rand = Math.random();%>
                    <img class="picture" src ="<c:url value='/mwcUser/avatarImg/${mwcUser.id}' />/?rand=<%=rand %>"/>
                </fieldset>
                <fieldset>
                    <legend>${labelUserShowLegendOptions}</legend>
                    <table class="showTable">
                        <tr>
                            <td>${labelUserPreferredLanguage}</td>
                            <td>${mwcUser.preferredLanguageDescription}</td>
                        <tr>
                    </table>
                </fieldset>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
        </div>
    </body>
</html>
