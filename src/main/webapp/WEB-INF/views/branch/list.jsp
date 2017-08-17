<%--
    Document   : list
    Created on : Jul 21, 2013, 12:48:32 PM
    Author     : almir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <spring:message code="page.list.branch.title" var="pageListBranchTitle" />
        <spring:message code="page.list.branch.legend" var="pageListBranchLegend" />
        <!--AVAILABLE OPTIONS-->
        <spring:message code="available.options.branch.list.people" var="availableOptionsBranchListPeople" />
        <!--LABELS-->
        <spring:message code="label_operations" var = "labelOperations" />
        <spring:message code="label_user_list_title" var = "labelUserListTitle" />
        <spring:message code="label_operations" var="labelOperations" />
        <spring:message code="label_operation_delete" var="labelOperationDelete" />
        <spring:message code="label_operation_show" var="labelOperationShow" />
        <spring:message code="label_operation_edit" var="labelOperationEdit" />
        <spring:message code="label_operation_series" var="labelOperationSeries" />
        <spring:message code="label.branch.id" var="labelBranchId" />
        <spring:message code="label.branch.acronym" var="labelBranchAcronym" />
        <spring:message code="label.branch.name" var="labelBranchName" />
        <spring:message code="label.branch.zip.code" var="labelBranchZipCode" />
        <!--FIELDS-->

        <!--MISC-->
        <c:set var='viewName' value='branchList' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page="../complements/includes/genericmenu.jsp" />
                <script>//menuBuilder("branchList", "", "", "");</script>
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageListBranchTitle}</h1>
                <%@include file = '../complements/includes/showCompanyBasicData.jspf' %>
                <fieldset>
                    <legend>${pageListBranchLegend}</legend>
                    <table class="basicTable">
                        <thead>
                            <tr>
                                <th>${labelBranchId}</th>
                                <th>${labelBranchAcronym}</th>
                                <th>${labelBranchZipCode}</th>
                                <th>${labelOperations}</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${branches}" var="branch">
                                <tr>
                                    <td>${branch.id}</td>
                                    <td>${branch.acronym}</td>
                                    <td>${branch.zipCode}</td>
                                    <td>
                                        <a href="${ctx}/branch/delete/${branch.id}">${labelOperationDelete}</a> |
                                        <a href="${ctx}/branch/edit/${branch.id}">${labelOperationEdit}</a> |
                                        <a href="${ctx}/branch/show/${branch.id}">${labelOperationShow}</a> |
                                        <a href="${ctx}/branch/listpeople/${branch.id}">${availableOptionsBranchListPeople}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
        </div>
    </body>
</html>