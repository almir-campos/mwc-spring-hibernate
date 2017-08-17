<%-- 
    Document   : activation
    Created on : Aug 30, 2013, 1:26:13 AM
    Author     : almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
        <!--PAGE MESSAGES-->
        <spring:message code="page.activation.title" var="pageActivationTitle" />
        <spring:message code="page.activation.show.disclaimer.button" var="pageActivationShowDisclaimerButton" />
        <spring:message code="page.activation.i.agree.button" var="pageActivationIAgreeButton" />
        <spring:message code="page.activation.i.do.not.agree.button" var="pageActivationIDoNotAgreeButton" />
        <spring:message code="page.activation.i.didnt.read.button" var="pageActivationIDidntReadButton" />
        <spring:message code="page.activation.cannot.continue" var="pageActivationCannotContinue" />
        <spring:message code="page.about.disclaimer.title" var="pageAboutDisclaimerTitle" />
        <spring:message code="page.about.disclaimer.left.column" var="pageAboutDisclaimerLeftColumn" />
        <spring:message code="page.about.disclaimer.right.column" var="pageAboutDisclaimerRightColumn" />
        <!--FORM LABELS-->
        <spring:message code="label.user.username" var="labelUserUsername" />
        <spring:message code="label.user.password" var="labelUserPassword" />
        <spring:message code="label.user.confirm.password" var="labelUserConfirmPassword" />
        <spring:message code="label.user.account.creation.and.login.button" var="labelUserAccountCreationAndLoginButton" />
        <spring:message code="label.user.account.creation.and.login.legend" var="labelUserAccountCreationAndLoginLegend" />
        <!--VARIABLES-->
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div class='leftPanelDiv'>
                <img 
                    src='${ctx}/resources/images/flags/br.gif'
                    style='cursor: pointer; float: left; margin-right: 5px;'
                    class="localeFlag"
                    id="pt_BR" />
                <img 
                    src='${ctx}/resources/images/flags/us.gif' 
                    style='cursor: pointer; float: left; margin-left: 5px;' 
                    class="localeFlag"
                    id="en_US" />
                <script>
                    var locale = $.cookie("MWC_locale");
                    if (!locale)
                    {
                        locale = "en_US";
                    }
                    $("#" + locale).addClass("flagShadow");

                    $(".localeFlag").click(function()
                    {
                        locale = $(this).attr("id");

                        $(".localeFlag").removeClass("flagShadow");
                        changeLocale(locale, "", "");
                    });
                </script>
            </div>
            <div class="mainDiv">
                <h1>${pageActivationTitle} ${person.firstName}</h1>
                <div id="registrationFormDiv" style="display: none;">
                    <fieldset>
                        <legend>${labelUserAccountCreationAndLoginLegend}</legend>
                        <table class="formTable">
                            <tr>
                                <td>${labelUserUsername}</td>
                                <td><input type="text" id="username" value="" /></td>
                            </tr>
                            <tr>
                                <td>${labelUserPassword}</td>
                                <td><input type="password" id="password" value="" /></td>
                            </tr>
                            <tr>
                                <td>${labelUserConfirmPassword}</td>
                                <td><input type="password" id="confirmPassword" value="" /></td>
                            </tr>
                        </table>
                    </fieldset>
                    <button
                        id="loginRegisterBt"
                        class="confirmButton confirmAjaxSubmit">
                        ${labelUserAccountCreationAndLoginButton}
                    </button>
                    <button class="showDisclaimerBt genericButton">${pageActivationShowDisclaimerButton}</button>
                </div>
                <div id="disclaimerDiv">
                    <h4>${pageAboutDisclaimerTitle}</h4>
                    <hr />
                    ${pageAboutDisclaimerLeftColumn}
                    ${pageAboutDisclaimerRightColumn}
                    <hr />
                    <table width='100%'>
                        <tr>
                            <td>
                                <button id="iAgreeBt" class="genericButton">${pageActivationIAgreeButton}</button>
                            </td>
                            <td style='text-align: center;'>
                                <button id="iDoNotAgreeBt" class='cannotContinue genericButton'>${pageActivationIDoNotAgreeButton}</button>
                            </td>
                            <td style='text-align: right;'>
                                <button id="iDidntReadBt" class='cannotContinue genericButton'>${pageActivationIDidntReadButton}</button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="cannotContinueDiv" style="display: none;">
                    ${pageActivationCannotContinue}
                    <br />
                    <button class="showDisclaimerBt genericButton">${pageActivationShowDisclaimerButton}</button>
                </div>
            </div>
            <%@include file = "../complements/includes/ajaxWaitForAjaxRequests.jspf" %>
        </div>
        <script type="text/javascript" language="javascript">
            $("#iAgreeBt").click(function()
            {
                $("#disclaimerDiv").hide();
                $("#cannotContinueDiv").hide()
                $("#registrationFormDiv").show();
            });

            $(".showDisclaimerBt").click(function()
            {
                $("#registrationFormDiv").hide();
                $("#cannotContinueDiv").hide();
                $("#disclaimerDiv").show();
            });

            $(".cannotContinue").click(function()
            {
                $("#registrationFormDiv").hide();
                $("#disclaimerDiv").hide();
                $("#cannotContinueDiv").show();
            });

            $("#loginRegisterBt").click(function()
            {
                $.ajax(
                        {
                            url: "<c:url value='/public/firstLogin' />",
                            type: "post",
                            dataType: "text",
                            data: {
                                username: $("#username").val(),
                                password: $("#password").val(),
                                confirmPassword: $("#confirmPassword").val(),
                                linkCode: "${linkCode}",
                                personId: ${person.id}
                            },
                            success: function(data)
                            {
                                if (data == "OK")
                                {
                                    window.location.href = "<c:url value='/mwcUser/show/'/>";
                                }
                                else
                                {
                                    $("#pleaseWaitDialogDiv").dialog('close');
                                    alert(data);
                                }
                            },
                            error: function()
                            {
                                $("#pleaseWaitDialogDiv").dialog('close');
                                alert("error");
                            }

                        });
            });
        </script>
    </body>
</html>
