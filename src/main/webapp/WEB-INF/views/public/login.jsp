<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        <noscript><meta http-equiv="refresh" content="0; url=${pageContext.request.servletContext.contextPath}/public/nojavascript"></noscript>
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" ></script> 
    <!--SCRIPTS-->
    <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
    <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
    <!--MESSAGES-->
    <spring:message code="multiline.text.test" var="multilineTextTest" />
    <!--LABELS-->
    <spring:message code="page.login.title" var="pageLoginTitle" />
    <spring:message code="label.login.legend" var="labelLoginLegend" />
    <spring:message code="label.login.username" var="labelLoginUsername" />
    <spring:message code="label.login.password" var="labelLoginPassword" />
    <spring:message code="label.login.sign.up" var="labelLoginSignUp" />
    <spring:message code="label.login.reset.password" var="labelLoginResetPassword" />
    <spring:message code="label.login.button.submit" var="labelLoginButtonSubmit" />
    <spring:message code="label.login.button.clear" var="labelLoginRequestButtonClear" />
    <spring:message code="reset.password.request.title" var="resetPasswordRequestRequestTitle" />
    <spring:message code="reset.password.request.content.text" var="resetPasswordRequestRequestContentText" />
    <spring:message code="reset.password.request.username" var="resetPasswordRequestUsername" />
    <spring:message code="reset.password.request.sent" var="resetPasswordRequestSent" />
    <spring:message code="reset.password.request.confirm.button" var="resetPasswordRequestConfirmButton" />
    <spring:message code="reset.password.request.ok.button" var="resetPasswordRequestOkButton" />
    <spring:message code="reset.password.request.cancel.button" var="resetPasswordRequestCancelButton" />
    <spring:message code="reset.password.request.invalid.username" var="resetPasswordRequestInvalidUsername" />
    <spring:message code="reset.password.request.generic.error" var="resetPasswordRequestGenericError" />
    <!--VARIABLES-->
    <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    <c:set var='viewName' value='login' />

</head>
<body>
    <div class="contentDiv">
        <%@ include file = "../complements/includes/leftPanelWithoutGenericMenu.jspf" %>
        <div class='mainDiv'><%@ include file = "../complements/includes/outterTopBar.jspf" %>
            <h3>${pageLoginTitle}</h3>

            <c:if test="${not empty loginFailed}">
                <div class="errorblock">
                    ${loginFailed}
                </div>
            </c:if>
            <!--<div id='loginBlock'>-->
            <div style="width: 400px;">
                <form

                    name='loginForm'
                    action="<c:url value='/j_spring_security_check' />"
                    method='POST'>
                    <fieldset>
                        <legend>${labelLoginLegend}</legend>
                        <table class="formTable">
                            <tr>
                                <td>
                                    ${labelLoginUsername}
                                </td>
                                <td>
                                    <input type='text' id="username" name='j_username' value='' class="normal"/>
                                    <div id="signupSpan" class="note" onclick="signup();">${labelLoginSignUp}</div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    ${labelLoginPassword}
                                </td>
                                <td>
                                    <input type='password' name='j_password' value="" class="normal" />
                                    <div id="resetPasswordDiv" class="note">${labelLoginResetPassword}</div>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <!--                <div class='block1' style="margin-top: 10px;">-->
                    <!--                    <input 
                                            class="submitButton"
                                            name="reset" 
                                            type="reset"
                                            value="${labelLoginButtonClear}" 
                                            style='float: left; margin-left: 2px;'/>-->
                    <input 
                        class="confirmButton confirmAjaxSubmit" 
                        name="submit" 
                        type="submit" 
                        value="${labelLoginButtonSubmit}"
                        style='float: right; margin-right: 3px;'/>
                    <!--</div>-->
                </form>
            </div>
        </div>
        <div>
            <!--${multilineTextTest}-->
        </div>
        <div id="getUsernameDialogDiv" class="getUsernameDialog" style="display: none;">
            <div id="enterUsername" style="display: none;">
                <br />
                ${resetPasswordRequestRequestContentText}
                <br />
                <br />
                <input id="verifyUsernameInput" value="" />
            </div>
            <div id="usernameOK" style="display: none;">
                <br />
                <br />
                ${resetPasswordRequestSent}
            </div>
        </div>
        <%@include file = "../complements/includes/ajaxWaitForAjaxRequests.jspf" %>
        
    </div>
    <script>
        $("#resetPasswordDiv").click(function()
        {
            var thisDialog;

            $("#getUsernameDialogDiv").dialog(
                    {
                        modal: true,
                        resizable: false,
                        title: "${resetPasswordRequestRequestTitle}",
                        dialogClass: "getUsernameDialog",
                        open: function()
                        {
                            thisDialog = $(this);
                            $("#usernameOK").hide();
                            $("#enterUsername").show();
                            $("#verifyUsernameInput").val($("#username").val()).focus();
                            $(this).siblings('.ui-dialog-buttonpane').find('button:eq(1)').hide();
                        },
                        buttons:
                                {
                                    "confirm":
                                            {
                                                text: "${resetPasswordRequestConfirmButton}",
                                                class: "getUsernameDialogConfirmButton",
                                                click: function()
                                                {
                                                    $.ajax(
                                                            {
                                                                url: "${ctx}/public/verifyUsername",
                                                                type: "post",
                                                                data:
                                                                        {
                                                                            username: $("#verifyUsernameInput").val()
                                                                        },
                                                                beforeSend: function()
                                                                {
                                                                    $("#pleaseWaitDialogDiv").dialog(
                                                                            {
                                                                                dialogClass: 'pleaseWaitDialog',
                                                                                resizable: false,
                                                                                draggable: false,
                                                                                modal: true
                                                                            });
                                                                },
                                                                complete: function()
                                                                {

                                                                },
                                                                success: function(result)
                                                                {
                                                                    $("#pleaseWaitDialogDiv").dialog('close');
                                                                    if (result === "OK")
                                                                    {
                                                                        thisDialog.siblings('.ui-dialog-buttonpane').find('button:eq(1)').show();
                                                                        thisDialog.siblings('.ui-dialog-buttonpane').find('button:eq(1)').focus();
                                                                        thisDialog.siblings('.ui-dialog-buttonpane').find('button:eq(0)').hide();
                                                                        thisDialog.siblings('.ui-dialog-buttonpane').find('button:eq(2)').hide();
                                                                        $("#enterUsername").hide();
                                                                        $("#usernameOK").show();
                                                                    }
                                                                    else
                                                                    {
                                                                        alert("${resetPasswordRequestInvalidUsername}");
                                                                    }
                                                                },
                                                                error: function()
                                                                {
                                                                    $("#pleaseWaitDialogDiv").dialog('close');
                                                                    alert("${resetPasswordRequestGenericError}");
                                                                }
                                                            });
                                                }
                                            },
                                    "ok":
                                            {
                                                text: "${resetPasswordRequestOkButton}",
                                                class: "getUsernameDialogOKButton",
                                                click: function()
                                                {
                                                    $(this).dialog('close');
                                                }
                                            },
                                    "cancel":
                                            {
                                                text: "${resetPasswordRequestCancelButton}",
                                                class: "getUsernameDialogCancelButton",
                                                click: function()
                                                {
                                                    $(this).dialog('close');
                                                }
                                            },
                                }
                    });
        });
        function signup()
        {
            window.location.href = "<c:url value='/public/signup'/>";
        }


    </script>
</body>
</html>
