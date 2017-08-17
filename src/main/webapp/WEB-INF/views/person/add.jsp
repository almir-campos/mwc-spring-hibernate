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
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.datepicker.css' />" />
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/jquery-complementary.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <!--MISC MESSAGES-->
        <spring:message code="label.confirm.button" var = "labelConfirmButton" />
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <spring:message code="page.add.person.title" var="pageAddPersonTitle" />
        <!--VARIABLES-->
        <c:set var='viewName' value='personAdd' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <script>//menuBuilder("personAdd", "${messageConfirmChangeLocale}", "", "");</script>
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageAddPersonTitle}</h1>
                <form:form 
                    id="addPersonForm" 
                    action="/person/add"
                    commandName="person"
                    method="post">
                    <%@ include file = '../complements/includes/inputPersonBasicData.jspf' %>
                    <input type="hidden" name="status" value="NOT_INVITED" />
                    <input type="hidden" name="accessCodeType" value="NOT_USER" />
                </form:form>
                <button
                    formId="addPersonForm"
                    class="confirmButton confirmSubmit">
                    ${labelConfirmButton}
                </button>
            </div>
            <%@include file = "../complements/includes/ajaxWait.jspf" %>
        </div>
    </body>
</html>