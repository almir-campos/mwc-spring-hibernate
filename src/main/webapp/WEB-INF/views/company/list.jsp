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
        <spring:message code="page.list.company.title" var = "pageListCompanyTitle" />
        <!--LABELS-->
        <spring:message code="label_operations" var = "labelOperations" />
        <spring:message code="label_user_list_title" var = "labelUserListTitle" />
        <spring:message code="label_operations" var="labelOperations" />
        <spring:message code="label_operation_delete" var="labelOperationDelete" />
        <spring:message code="label_operation_show" var="labelOperationShow" />
        <spring:message code="label_operation_edit" var="labelOperationEdit" />
        <spring:message code="label_operation_series" var="labelOperationSeries" />
        <spring:message code="available.options.company.add.branch" var="availableOptionsCompanyAddBranch" />
        <spring:message code="available.options.company.list.branches" var="availableOptionsCompanyListBranches" />

        <!--FIELDS-->
        <spring:message code="label.company.id" var = "labelCompanyId" />
        <spring:message code="label.company.acronym" var = "labelCompanyAcronym" />
        <spring:message code="label.company.name" var = "labelCompanyName" />

        <!--MISC-->
        <c:set var='viewName' value='companyList' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page="../complements/includes/genericmenu.jsp" />
                <!--                <script>menuBuilder("companyList", "", "", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pageListCompanyTitle}</h1>
                <table class="basicTable">
                    <thead>
                        <tr>
                            <th>${labelCompanyId}</th>
                            <th>${labelCompanyAcronym}</th>
                            <th>${labelCompanyName}</th>
                            <th>${labelOperations}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${companies}" var="company">
                            <tr>
                                <td>${company.id}</td>
                                <td>${company.acronym}</td>
                                <td>${company.name}</td>
                                <td>
                                    <a href="${ctx}/company/delete/${company.id}">${labelOperationDelete}</a> |
                                    <a href="${ctx}/company/edit/${company.id}">${labelOperationEdit}</a> |
                                    <a href="${ctx}/company/show/${company.id}">${labelOperationShow}</a> |
                                    <a href="${ctx}/company/listbranches/${company.id}">${availableOptionsCompanyListBranches}</a> |
                                    <a href="${ctx}/branch/add/${company.id}">${availableOptionsCompanyAddBranch}</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
        </div>
    </body>
</html>