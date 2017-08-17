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
        <meta http-equiv='cache-control' content='no-cache'>
        <meta http-equiv='expires' content='0'>
        <meta http-equiv='pragma' content='no-cache'>
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <spring:message code="page.edit.branch.title" var = "pageEditBranchTitle" />

        <!--MISC MESSAGES-->
        <spring:message code="label.confirm.button" var = "labelConfirmButton" />
        <!--LABELS-->
        <spring:message code="label.branch.legend.basic.data" var="labelBranchLegendBasicData" />
        <spring:message code="label.branch.id" var="labelBranchId" />
        <spring:message code="label.branch.acronym" var="labelBranchAcronym" />
        <spring:message code="label.branch.name" var="labelBranchName" />
        <spring:message code="label.branch.zip.code" var="labelBranchZipCode" />
        <!--VARIABLES-->
        <c:if test="${empty company}">
            <c:set var="company" value="${branch.company}" />
        </c:if>
        <c:set var='viewName' value='branchEdit' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("editBranch", "${messageConfirmChangeLocale}", "", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageEditBranchTitle}</h1>
                <%@include file = '../complements/includes/showCompanyBasicData.jspf' %>
                <form:form
                    id="editBranchForm"
                    action="/branch/edit"
                    commandName="branch"
                    method="post">
                    <fieldset>
                        <legend>${labelBranchLegendBasicData}</legend>
                        <table class="formTable">
                            <tr>
                                <td>${labelBranchId}</td>
                                <td>
                                    ${branch.id}
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>${labelBranchAcronym}</td>
                                <td>
                                    <form:input path="acronym" id="acronym" value='${branch.acronym}' />
                                </td>
                                <td>
                                    <form:errors path="acronym" cssClass="formErrorMessage" />
                                </td>
                            </tr>
                            <tr>
                                <td>${labelBranchName}</td>
                                <td>
                                    <form:input path="name" id="name" value='${branch.name}' />
                                </td>
                                <td>
                                    <form:errors path="name" cssClass="formErrorMessage"/>
                                </td>
                            </tr>
                            <tr>
                                <td>${labelBranchZipCode}</td>
                                <td>
                                    <form:input path="zipCode" id="zipCode" value='${branch.zipCode}' />
                                </td>
                                <td>
                                    <form:errors path="zipCode" cssClass="formErrorMessage" />
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <input type ="hidden" name="id" value="${branch.id}" />
                </form:form>
                    <button formId="editBranchForm" class="confirmButton confirmSubmit">${labelConfirmButton}</button>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
            <%@ include file = "../complements/includes/ajaxWait.jspf" %>
        </div>
    </body>
</html>