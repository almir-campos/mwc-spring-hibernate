<%-- 
    Document   : resetPassword
    Created on : Oct 5, 2013, 8:54:07 PM
    Author     : Almir
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
        <spring:message code="page.reset.password.title" var="pageResetPasswordTitle" />
        <!--FORM LABELS-->
        <spring:message code="reset.password.legend" var="resetPasswordLegend" />
        <spring:message code="reset.password.username" var="resetPasswordUsername" />
        <spring:message code="reset.password.new.password" var="resetPasswordNewPassword" />
        <spring:message code="reset.password.confirm.new.password" var="resetPasswordConfirmNewPassword" />
        <spring:message code="reset.password.confirm.button" var="resetPasswordConfirmButton" />
        <spring:message code="reset.password.generic.error" var="resetPasswordGenericError" />
        <spring:message code="reset.password.ok" var="resetPasswordOk" />
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
                <h1>${pageResetPasswordTitle}</h1>
                <fieldset>
                    <legend>${resetPasswordLegend}</legend>
                    <table class="formTable">
                        <tr>
                            <td>${resetPasswordUsername}</td>
                            <td><input type="text" id="username" value="" /></td>
                        </tr>
                        <tr>
                            <td>${resetPasswordNewPassword}</td>
                            <td><input type="password" id="password" value="" /></td>
                        </tr>
                        <tr>
                            <td>${resetPasswordConfirmNewPassword}</td>
                            <td><input type="password" id="confirmPassword" value="" /></td>
                        </tr>
                    </table>
                </fieldset>
                <button
                    id="resetPasswordBt"
                    class="confirmButton confirmAjaxSubmit">${resetPasswordConfirmButton}</button>
            </div>
            <%@include file = "../complements/includes/ajaxWaitForAjaxRequests.jspf" %>
        </div>
        <script type="text/javascript" language="javascript">
            $("#resetPasswordBt").click(function()
            {
                $.ajax(
                        {
                            url: "<c:url value='/public/resetPassword' />",
                            type: "post",
                            dataType: "text",
                            data: {
                                username: $("#username").val(),
                                password: $("#password").val(),
                                confirmPassword: $("#confirmPassword").val(),
                                linkCode: "${linkCode}"
                            },
                            success: function(data)
                            {
                                if (data == "OK")
                                {
                                    alert( "${resetPasswordOk}" );
                                    window.location.href = "<c:url value='/public/login/'/>";
                                }
                                else
                                {
                                    $("#pleaseWaitDialogDiv").dialog('close');
                                    alert( data );
                                }
                            },
                            error: function()
                            {
                                $("#pleaseWaitDialogDiv").dialog('close');
                                alert("${resetPasswordGenericError}");
                            }

                        });
            });
        </script>
    </body>
</html>