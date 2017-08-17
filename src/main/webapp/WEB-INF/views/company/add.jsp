<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@page import="java.util.Random"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <!--MISC MESSAGES-->
        <spring:message code="label.confirm.button" var = "labelConfirmButton" />
        <!--PAGE MESSAGES-->
        <spring:message code="page.add.company.title" var = "pageAddCompanyTitle" />
        <!--LABELS-->
        <spring:message code="label.company.legend.basic.data" var = "labelCompanyLegendBasicData" />
        <spring:message code="label.company.acronym" var = "labelCompanyAcronym" />
        <spring:message code="label.company.name" var = "labelCompanyName" />
        <!--VARIABLES-->
        <c:set var='viewName' value='companyAdd' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("companyAdd", "${messageConfirmChangeLocale}", "${company.id}", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageAddCompanyTitle}</h1>
                <form:form
                    id="addCompanyForm"
                    action="/company/add"
                    commandName="company"
                    method="post">
                    <fieldset>
                        <legend>${labelCompanyLegendBasicData}</legend>
                        <table class="formTable">
                            <tr>
                                <td>${labelCompanyAcronym}</td>
                                <td>
                                    <form:input id="acronym" path="acronym" value=''/>
                                </td>
                                <td>
                                    <form:errors path="acronym" cssClass="formErrorMessage" />
                                </td>
                            </tr>
                            <tr>
                                <td>${labelCompanyName}</td>
                                <td>
                                    <form:input id="name" path="name" value=''/>
                                </td>
                                <td>
                                    <form:errors path="name" cssClass="formErrorMessage" />
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </form:form>
                <input
                    formId="addCompanyForm"
                    class="confirmButton confirmSubmit"
                    type="submit"
                    value="${labelConfirmButton}" />
                <!--<button id="generateRandomBt">Generate random data</button>-->
            </div>
            <%@include file = "../complements/includes/ajaxWait.jspf" %>
        </div>
        <script>
            $("#generateRandomBt").click(function()
            {
                $("#acronym").val("ac" + randomInteger(1, 1000));
                $("#name").val("name" + randomInteger(1, 1000));
            });
           
        </script>
    </body>
</html>