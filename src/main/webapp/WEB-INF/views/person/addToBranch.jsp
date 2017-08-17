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
        <spring:message code="page.add.to.branch.title" var="pageAddToBranchTitle" />
        <!--MISC MESSAGES-->
        <spring:message code="label.confirm.button" var = "labelConfirmButton" />
        <!--FIELDS-->
        <spring:message code="label.add.to.branch.branch" var="labelAddToBranchBranch" />
        <spring:message code="label.add.to.branch.position" var="labelAddToBranchPosition" />
        <!--VARIABLES-->
        <c:set var='viewName' value='personAddtoBranch' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <script>//menuBuilder("showAdd", "${messageConfirmChangeLocale}", "", "");</script>
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageAddToBranchTitle}</h1>
                <%@include file = '../complements/includes/showPersonBasicData.jspf' %>
                <form:form
                    id="addToBranchForm"
                    action="/person/addToBranch"
                    commandName="addToBranch"
                    method="post">
                    <fieldset>
                        <legend>Branch and Position</legend>
                        <table class="formTable">
                            <tr>
                                <td>${labelAddToBranchBranch}</td>
                                <td>
                                    <form:select path="branchId">
                                        <c:forEach items="${branches}" var="branch">
                                    <option value="${branch.id}">${branch.company.acronym}/${branch.acronym}</option>
                                </c:forEach>
                            </form:select>
                            </td>
                            </tr>
                            <tr>
                                <td>${labelAddToBranchPosition}</td>
                                <td>
                                    <form:input path="position" />
                                    <form:errors path="position" cssClass="formErrorMessage"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <input type="hidden" name="personId" value="${person.id}" />
                </form:form >
                <button
                    formId="addToBranchForm"
                    class="confirmButton confirmSubmit">
                    ${labelConfirmButton}
                </button>
            </div>
            <%@include file = "../complements/includes/ajaxWait.jspf" %>
        </div>
    </body>
</html>