<%--
    Document   : list
    Created on : Jul 21, 2013, 12:48:32 PM
    Author     : almir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        <spring:message code="page.person.list.people.title" var="pagePersonListPeopleTitle" />
        <!--LABELS-->
        <spring:message code="label_operations" var = "labelOperations" />
        <spring:message code="label_user_list_title" var = "labelUserListTitle" />
        <spring:message code="label_operations" var="labelOperations" />
        <spring:message code="label_operation_delete" var="labelOperationDelete" />
        <spring:message code="label_operation_show" var="labelOperationShow" />
        <spring:message code="label_operation_edit" var="labelOperationEdit" />
        <spring:message code="label.operations.show.user" var="labelOperationShowUser" />
        
        <!--FIELDS-->
        <spring:message code="label_user_id" var = "labelUserId" />
        <spring:message code="label_user_first_name" var = "labelUserFirstName" />
        <spring:message code="label_user_last_name" var = "labelUserLastName" />

        <spring:message code="label.person.id" var="labelPersonId" />
        <spring:message code="label.person.first.name" var="labelPersonFirstName" />
        <spring:message code="label.person.last.name" var="labelPersonLastName" />
        <spring:message code="label.person.company" var="labelPersonCompany" />
        <spring:message code="label.person.branch" var="labelPersonBranch" />
        <spring:message code="label.person.status" var="labelPersonStatus" />
        <spring:message code="label.person.access.code.type" var="labelPersonAccessCodeType" />
        <spring:message code="label.person.manager" var="labelPersonManager" />
        <spring:message code="label.person.manage.access.code" var="labelPersonManageAcessCode" />
        <spring:message code="label.person.operation.add.to.branch" var="labelPersonOperationAddToBranch" />
        <!--VARIABLES-->
        <c:set var='viewName' value='peopleList' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page="../complements/includes/genericmenu.jsp" />
                <script>//menuBuilder("peopleList", "", "", "");</script>
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${pagePersonListPeopleTitle}</h1>
                <table class="basicTable">
                    <thead>
                        <tr>
                            <th>${labelUserId}</th>
                            <th>${labelUserFirstName}</th>
                            <th>${labelUserLastName}</th>
                            <th>${labelPersonCompany}</th>
                            <th>${labelPersonBranch}</th>
                            <th>${labelPersonStatus}</th>
                            <th>${labelPersonAccessCodeType}</th>
                            <th>${labelPersonManager}</th>
                            <th>${labelOperations}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${people}" var="person">
                            <tr>
                                <td>${person.id}</td>
                                <td>${person.firstName}</td>
                                <td>${person.lastName}</td>
                                <td>${person.branch.company.acronym}</td>
                                <td>${person.branch.acronym}</td>
                                <td>${person.status}</td>
                                <td>${person.accessCodeType}</td>
                                <td>${person.manager.firstName}</td>
                                <td>
                                    <a href="${ctx}/person/delete/${person.id}">${labelOperationDelete}</a> |
                                    <a href="${ctx}/person/edit/${person.id}">${labelOperationEdit}</a> |
                                    <a href="${ctx}/person/show/${person.id}">${labelOperationShow}</a>
                                    <c:if test="${not empty loggedUser.person.branch}">
                                        |
                                        <c:if test="${empty person.branch}">
                                            <a href="${ctx}/person/addToBranch/${person.id}">${labelPersonOperationAddToBranch}</a>
                                        </c:if>
                                        <c:if test="${not empty person.branch}">
                                            <a href="${ctx}/person/removeFromBranch/${person.branch.id}/${person.id}">Remove from Branch</a>
                                        </c:if>
                                    </c:if>
                                    | <a href="${ctx}/person//manageAccessCode/${person.id}">${labelPersonManageAcessCode}</a>
                                    <c:if test="${not empty person.user}">
                                        | <a href="${ctx}/mwcUser/show/${person.user.id}">${labelOperationShowUser}</a>
                                    </c:if>
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