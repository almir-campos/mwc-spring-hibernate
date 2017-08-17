<%--
    Document   : list
    Created on : Jul 21, 2013, 12:48:32 PM
    Author     : almir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <spring:message code="label_operations" var="labelOperations" />
        <!--PAGE LABELS-->
        <spring:message code="label_series_list_title" var="labelSeriesListTitle" />
        <spring:message code="label_operations" var="labelOperations" />
        <spring:message code="label_operation_delete" var="labelOperationDelete" />
        <spring:message code="label_operation_show" var="labelOperationShow" />
        <spring:message code="label_operation_edit" var="labelOperationEdit" />
        <spring:message code="label_operation_details" var="labelOperationDetails" />
        <!--SERIES FIELDS-->
        <spring:message code="label_series_id" var="labelSeriesId" />
        <spring:message code="label_series_description" var="labelSeriesDescription" />
        <spring:message code="label_series_initial_weight" var="labelSeriesInitialWeight" />
        <spring:message code="label_series_goal_weight" var="labelSeriesGoalWeight" />
        <spring:message code="label_series_start_date" var="labelSeriesStartDate" />
        <spring:message code="label_series_estimated_end_date" var="labelSeriesEstimatedEndDate" />
        <spring:message code="label_series_real_end_date" var="labelSeriesRealEndDate" />
        <!--VARIABLES-->
        <c:set var='viewName' value='seriesList' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("seriessList", "", "${mwcUser.id}", "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelSeriesListTitle}</h1>
                <table class="basicTable">
                    <thead>
                        <tr>
                            <th>${labelSeriesId}</th>
                            <th>${labelSeriesDescription}</th>
                            <!--<th>${labelSeriesInitialWeight}</th>-->
                            <th>${labelSeriesGoalWeight}</th>
                            <th>${labelSeriesStartDate}</th>
                            <th>${labelSeriesEstimatedEndDate}</th>
                            <!--<th>${labelSeriesRealEndDate}</th>-->
                            <th>${labelOperations}</th>
                        </tr>
                    </thead>
                    <tbody>                     
                        <c:forEach items="${seriess}" var="series">
                            <tr>
                                <td>${series.id}</td>
                                <td>${series.description}</td>
                                <!--<td>${series.initialWeight}</td>-->
                                <td>${series.goalWeight}</td>
                                <td>${series.startDateString}</td>
                                <td>${series.estimatedEndDateString}</td>
                                <!--<td>${series.realEndDateString}</td>-->
                                <td>
                                    <a href="${ctx}/series/delete/${series.id}">${labelOperationDelete}</a> |
                                    <a href="${ctx}/series/show/${series.id}">${labelOperationShow}</a> |
                                    <a href="${ctx}/series/edit/${series.id}">${labelOperationEdit}</a> |
                                    <a href="${ctx}/series/details/${series.id}">${labelOperationDetails}</a>
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