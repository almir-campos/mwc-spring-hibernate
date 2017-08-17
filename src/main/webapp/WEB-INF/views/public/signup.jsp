<%-- 
    Document   : signup
    Created on : Aug 24, 2013, 11:37:31 PM
    Author     : Almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <!--PAGE MESSAGES-->
        <spring:message code="page.signup.title" var="pageSignupTitle" />
        <spring:message code="page.signup.register.form.legend" var="pageSignupRegisterFormLegend" />
        <spring:message code="page.signup.email" var="pageSignupEmail" />
        <spring:message code="page.signup.change.email" var="pageSignupChangeEmail" />
        <spring:message code="page.signup.confirm.email" var="pageSignupConfirmEmail" />
        <spring:message code="page.signup.verify.access.code" var="pageSignupVerifyAccessCode" />
        <spring:message code="page.signup.request.activation" var="pageSignupRequestActivation" />
        <spring:message code="page.signup.back.to.login" var="pageSignupBackToLogin" />
        <!--LABELS-->
        <spring:message code="label.login.sign.up" var="labelLoginSignUp" />
        <spring:message code="label.person.access.code" var="labelPersonAccessCode" />
        <spring:message code="label.person.company" var="labelPersonCompany" />
        <spring:message code="label.person.branch" var="labelPersonBranch" />
        <spring:message code="label.person.name" var="labelPersonName" />
        <spring:message code="label.person.email" var="labelPersonEmail" />
        <!--VARIABLES-->
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <c:set var='viewName' value='signup' />
    </head>
    <body>
        <div class="contentDiv">
            <c:if test="${not isLogged}">
                <%@ include file = "../complements/includes/leftPanelWithoutGenericMenu.jspf" %>
            </c:if>
            <c:if test="${isLogged}">
                <div id="rootMenuDiv">
                    <%@ include file = "../complements/includes/genericmenu.jsp" %>
                </div>
            </c:if>
            <div class='mainDiv'>
                <%@ include file = "../complements/includes/outterTopBar.jspf" %>
                <h1>${pageSignupTitle}</h1>
                <fieldset id="accessCodeFieldset">
                    <legend>${labelPersonAccessCode}</legend>
                    <table class="showTable">
                        <tr>
                            <td>${labelPersonAccessCode}</td>
                            <td>
                                <input type="text" id="accessCode" />
                                <button
                                    id="verifyAccessCodeBt"
                                    class="confirmButton confirmAjaxSubmit">
                                    ${pageSignupVerifyAccessCode}
                                </button>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <div id="registerFormDiv" style="display: none;">
                    <fieldset>
                        <legend>${pageSignupRegisterFormLegend}</legend>
                        <b>${labelPersonCompany}</b><br />
                        <span id="company" class="genericInfo"></span><br /><br />
                        <b>${labelPersonBranch}</b><br />
                        <span id="branch" class="genericInfo"></span><br /><br />
                        <b>${labelPersonName}</b><br />
                        <span id="name" class="genericInfo"></span><br /><br />
                        <b>${labelPersonEmail}</b><br />
                        <span id="loadedEmail" class="genericInfo"></span><br />
                        <input type="checkbox" id="changeEmailInput">${pageSignupChangeEmail}</input><br />
                        <input type="hidden" id="personId" attr-id="" />
                    </fieldset>
                    <button
                        id="requestActivationBt"
                        class="confirmButton confirmAjaxSubmit">
                        ${pageSignupRequestActivation}
                    </button>
                </div>
                <div id="changeEmailDiv" style="display:none;">
                    <fieldset>
                        <legend>${pageSignupChangeEmail}</legend>
                        ${pageSignupEmail}<br />
                        <input type="text" id="email" value="" />
                        <br />
                        <br />
                        ${pageSignupConfirmEmail}<br />
                        <input type="text" id="confirmEmail" value="" />
                        <br />
                        <br />
                        <button id="requestActivationChangeEmailBt">${pageSignupRequestActivation}</button>
                    </fieldset>
                </div>
                <!--<button id="automaticLoginBt">Automatic Login</button>-->
                <br />
                <br />
                <!--                <div style="text-align: right;">
                                    <a href="${ctx}/public/login">${pageSignupBackToLogin}</a>
                                </div>-->
            </div>
            <%@include file = "../complements/includes/ajaxWaitForAjaxRequests.jspf" %>
        </div>
        <script type="text/javascript" language="javascript">
//            alert( $("#accessCode").val() );
            $("#changeEmailInput").removeAttr("checked");
            $("#verifyAccessCodeBt").click(function()
            {
                var accessCode = $("#accessCode").val();
                
                $.ajax(
                        {
                            url: "${ctx}/public/verifyAccessCode/" + accessCode,
                            type: "get",
                            dataType: "xml",
                            success: function(personXML)
                            {
//                                alert( "*" + personXML + "*" );
                                if (personXML)
                                {
                                    $("#company").text($(personXML).find('company-name').text());
                                    $("#branch").text($(personXML).find('branch-name').text());
                                    $("#name").text($(personXML).find('first-name').text() + " " + $(personXML).find('last-name').text());
                                    $("#loadedEmail").text($(personXML).find('email').text());
                                    $("#registerFormDiv").show();
                                    $("#accessCodeFieldset").hide();
                                    $("#personId").attr("attr-id", $(personXML).find('id').text());
                                }
                                else
                                {
                                    alert("Invalid Access Code");
                                }
                            },
                            complete: function()
                            {
                                $("#pleaseWaitDialogDiv").dialog('close');
                            }
                        });
            });

            $("#changeEmailInput").click(function()
            {
                if ($(this).is(':checked'))
                {
                    $("#changeEmailDiv").show();
                    $("#requestActivationBt").hide();
                }
                else
                {
                    $("#requestActivationBt").show();
                    $("#changeEmailDiv").hide();
                }

            });

            $("#requestActivationBt").click(function()
            {
                $.ajax(
                        {
                            url: "${ctx}/public/requestActivation/" + $("#personId").attr("attr-id"),
                            type: "post",
                            beforeSend: function()
                            {
                                //alert('antes')
                            },
                            success: function(link)
                            {
                                if (link == "ok")
                                {
                                    window.location.href = "${ctx}/public/activationRequestConfirmation";
                                }
                                else
                                {
                                    $("#pleaseWaitDialogDiv").dialog('close');
                                    alert(link);
                                }
                            },
                            error: function()
                            {
                                $("#pleaseWaitDialogDiv").dialog('close');
                                alert("error");
                            },
                            complete: function()
                            {
                                $("#pleaseWaitDialogDiv").dialog('close');
                            }

                        });
            });


            $("#requestActivationChangeEmailBt").click(function()
            {
                alert('activation change email');
            });

            //            $("#automaticLoginBt").click(function()
            //            {
            //                $.ajax(
            //                        {
            //                            url: "<c:url value='/person/almir' />" ,
            //                            type: "post",
            //                            dataType: "text",
            ////                            data:{
            ////                                j_username: "almir",
            ////                                j_password: "almir"
            ////                            },
            //                            success: function( data )
            //                            {
            //                                window.location.href = "<c:url value='/mwcUser/show/'/>" + data;
            //                            },
            //                            error: function()
            //                            {
            //                                alert("error");
            //                            }
            //
            //                        });
            //            });
        </script>
    </body>
</html>
