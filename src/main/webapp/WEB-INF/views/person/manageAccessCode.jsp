<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        <!--VARIABLES-->
        <c:set var='viewName' value='manageAccessCode' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <!--MISC MESSAGES-->
        <spring:message code="label.confirm.button" var="labelConfirmButton" />
        <!--PAGE MESSAGES-->
        <spring:message code="page.manage.access.code.title" var="pageManageAccessCodeTitle" />
        <!--FORM LABELS-->
        <spring:message code="label.manage.access.code.legend" var="labelManageAccessCodeLegend" />
        <spring:message code="label.manage.access.code.current.access.code" var="labelManageAccessCodeCurrentAccessCode" />
        <spring:message code="label.manage.access.code.current.access.code.type" var="labelManageAccessCodeCurrentAccessCodeType" />
        <spring:message code="label.manage.access.code.new.access.code" var="labelManageAccessCodeNewAccessCode" />
        <spring:message code="label.manage.access.code.new.access.code.type" var="labelManageAccessCodeNewAccessCodeType" />
        <spring:message code="label.manage.access.code.change.email" var="labelManageAccessCodeChangeEmail" />
        <spring:message code="label.manage.access.code.change.email" var="labelManageAccessCodeChangeEmail" />
        <spring:message code="label.manage.access.code.invitation.language" var="labelManageAccessCodeInvitationLanguage" />
        <spring:message code="label.manage.access.code.send.invitation" var="labelManageAccessCodeSendInvitation" />
        <!--ABOUT MESSAGES-->
        <c:set var="aboutThePage">
        <span class="aboutTitle"> 
            <spring:message code="about.page.title" />
        </span>
        <div class="aboutThePageMessagesDiv">
            <ul>
                <li> 
                    <spring:message code="person.accessCode.about.page.01" /></li>
                <li> 
                    <spring:message code="person.accessCode.about.page.02" /></li>
            </ul>
        </div>
    </c:set>
</head>
<body onload="init();">
    <div class="contentDiv">
        <div id="rootMenuDiv">
            <jsp:include page='../complements/includes/genericmenu.jsp' />
            <!--<script>menuBuilder("accessCodePerson", "${messageConfirmChangeLocale}", "", "");</script>-->
        </div>
        <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
            <h1>${pageManageAccessCodeTitle}</h1>
            <%@include file = '../complements/includes/showPersonBasicData.jspf' %>
            <form id="confirmationForm"
                  action="<c:url value='/person/accessCode'/>" 
                  method="post">
                <fieldset>
                    <legend>${labelManageAccessCodeLegend}</legend>
                    <table class="formTable">
                        <tr>
                            <td>${labelManageAccessCodeCurrentAccessCode}</td>
                            <td>${currentAccessCode}</td>
                        </tr>
                        <tr>
                            <td>${labelManageAccessCodeCurrentAccessCodeType}</td>
                            <td>${currentAccessCodeType}</td>
                        </tr>
                        <tr>
                            <td>${labelManageAccessCodeNewAccessCode}</td>
                            <td>${newAccessCode}</td>
                        </tr>
                        <tr>
                            <td>${labelManageAccessCodeNewAccessCodeType}</td>
                            <td>
                                <select name="accessCodeType">
                                    <c:forEach items="${accessCodeTypes}" var="accessCodeType">
                                        <c:if test="${accessCodeType.code eq 'USER'}">
                                            <option selected value="${accessCodeType.code}">${accessCodeType.description}</option>
                                        </c:if>
                                        <c:if test="${accessCodeType.code ne 'USER'}">
                                            <option value="${accessCodeType.code}">${accessCodeType.description}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>${labelManageAccessCodeChangeEmail}</td>
                            <td>
                                <input
                                    type="checkbox"
                                    id="changeEmail"
                                    name="changeEmail"
                                    onclick="showHideChangeEmailDiv();" 
                                    style ="float:left;"/>
                                <div id='changeEmailDiv' style='display:none; float: left;' >
                                    <input type='text' id='email' name='email' value='${person.email}' />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>${labelManageAccessCodeInvitationLanguage}</td>
                            <td>
                                <input type="radio" name="invitationLanguage" value="en_US">English</input>
                                <input type="radio" name="invitationLanguage" value="pt_BR">Português</input>
                            </td>
                        </tr>
                        <tr>
                            <td>${labelManageAccessCodeSendInvitation}</td>
                            <td>
                                <input type="checkbox" name="sendInvitation" />
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <input type="hidden" name="personId" value="${person.id}" />
                <input type="hidden" name="accessCode" value="${newAccessCode}" />
                <!--<input type="hidden" name="accessCodeType" value="USER" />-->
                <!--<input type="submit" class="confirmButton" value="Confirm" />-->
            </form>
                <button
                    formId="confirmationForm"
                    class="confirmButton confirmSubmit">
                    ${labelConfirmButton}
                </button>
            <%--<%@ include file = "../complements/includes/about.jspf" %>--%>
        </div>
        <%@include file = "../complements/includes/ajaxWait.jspf" %>
    </div>
    <script>
        function init()
        {
//        document.getElementById('accessCodeType').value = '${person.accessCodeType}';
//        document.getElementById('changeAccessCodeType').checked = false;
//        document.getElementById('accessCodeTypeDiv').style.display = 'none';

//            if (document.getElementById('changeAccessCodeType').checked)
//            {
//                document.getElementById('newAccessCodeType').value = '${person.accessCodeType}';
//                document.getElementById('accessCodeTypeDiv').style.display = 'block';
//            }
//            else
//            {
//                document.getElementById('accessCodeTypeDiv').style.display = 'none';
//            }
        }
        function showHideAccessCodeTypeDiv()
        {
            if (document.getElementById('changeAccessCodeType').checked === false)
            {
                document.getElementById('newAccessCodeType').value = '${person.accessCodeType}';
            }
            showHide('accessCodeTypeDiv', null, null, null);
            return false;
        }

        function showHideChangeEmailDiv()
        {
            if (document.getElementById('changeEmail').checked === false)
            {
                document.getElementById('email').value = '${person.email}';
            }
            showHide('changeEmailDiv', null, null, null);
            return false;
        }

    </script> 
</body>
</html>