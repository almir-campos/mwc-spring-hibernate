<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
        <!--CSS-->
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.core.css' />" />
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.dialog.css' />" />
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >

        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
        <script src="<c:url value='/resources/js/jquery-ui-1.10.3/minified/jquery-ui.min.js' />" type="text/javascript" ></script> 
        <script src="<c:url value='/resources/js/jQueryForm/jquery.form.js' />" type="text/javascript" ></script> 
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <!--LABELS-->
        <spring:message code="label_user_edit_title" var = "labelUserEditTitle" />
        <spring:message code="label_user_edit_legend_identification" var = "labelUserEditLegendIdentification" />
        <spring:message code="label_user_edit_update_identification_button" var = "labelUserEditUpdateIdentificationButton" />
        <spring:message code="label_user_edit_legend_avatar" var = "labelUserEditLegendAvatar" />
        <spring:message code="label_user_edit_update_avatar_button" var = "labelUserEditUpdateAvatarButton" />
        <spring:message code="label_user_edit_legend_options" var = "labelUserEditLegendOptions" />
        <spring:message code="label_user_edit_update_preferred_language_button" var = "labelUserEditUpdatePreferedLanguageButton" />
        <!--FIELDS-->
        <spring:message code="label_user_id" var = "labelUserId" />
        <spring:message code="label_user_first_name" var = "labelUserFirstName" />
        <spring:message code="label_user_last_name" var = "labelUserLastName" />
        <spring:message code="label_user_display_name" var = "labelUserDisplayName" />
        <spring:message code="label_user_username" var = "labelUserUsername" />
        <spring:message code="label_user_password" var = "labelUserPassword" />
        <spring:message code="label_user_birth_date" var = "labelUserBirthDate" />
        <spring:message code="label_user_preferred_language" var = "labelUserPreferredLanguage" />
        <!--VARIABLES-->
        <c:set var='viewName' value='userEdit' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <c:set var="person" value="${mwcUser.person}"/>
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("userEdit", "${messageConfirmChangeLocale}", ${mwcUser.person.id}, "");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelUserEditTitle}</h1>
                <%@include file = "../complements/includes/showPersonBasicData.jspf" %>
                <%@include file = "../complements/includes/inputMwcUserBasicData.jspf" %>
                <div>
                    <div style="float: left;">
                        <fieldset style="height: 250px;">
                            <legend>${labelUserEditLegendAvatar}</legend>
                            <div id="avatarImgDiv">
                                <% Double rand = Math.random();%>
                                <br />
                                <img class="picture" src ="<c:url value='/mwcUser/avatarImg/${mwcUser.id}' />/?rand=<%= rand %>" />
                            </div>
                            <form 
                                id="updateAvatarImgForm"
                                action="<c:url value='/mwcUser/updateAvatarImg'/>"
                                enctype="multipart/form-data"
                                method="post">
                                <input type="hidden" name="userId" value="${mwcUser.id}" />
                                <input type="hidden" name="fake" value="<% Math.random();%>" />
                                <input id="avatarImgInput" type="file" name="file"/>
                                <br />
                                <input id="updateAvatarImgFormBt" type="submit" value="${labelUserEditUpdateAvatarButton}">
                            </form>
                        </fieldset>
                    </div>
                    <div id="preferredLanguageDiv" style="float:right;">
                        <fieldset style="height: 250px;">
                            <legend>${labelUserEditLegendOptions}</legend>
                            <table class="showTable">
                                <tr>
                                    <td>${labelUserPreferredLanguage}:</td>
                                    <td>
                                        <select id="preferredLanguage" name="preferredLanguage">
                                            <c:forEach items="${languages}" var="lang">
                                                <c:if test="${lang.code eq mwcUser.preferredLanguageDescription}">
                                                    <option value="${lang.code}" selected>${lang.code}, ${lang.description}</option>  
                                                    <script>var currentLanguage = "${lang.code}";</script>
                                                </c:if>
                                                <c:if test="${lang.code ne mwcUser.preferredLanguageDescription}">
                                                    <option value="${lang.code}">${lang.code}, ${lang.description}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <button id="updatePreferredLanguage">${labelUserEditUpdatePreferedLanguageButton}</button>
                                    </td>
                                </tr>
                            </table>

                        </fieldset>
                    </div>
                </div>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
            </div>
            <%@ include file = "../complements/includes/ajaxWaitPlease.jspf" %>
            <script type="text/javascript">
                $.ajaxSetup({cache: false});

                var jqfoptions = {
                    success: function()
                    {
                        var href = "<c:url value='/mwcUser/ajax/avatarimgload' />" + "?userId=${mwcUser.id}";
                        $("#avatarImgDiv").load(href);
                    }
                };

                $("#updateAvatarImgForm").ajaxForm(jqfoptions);

                $("#updatePreferredLanguage").click(function()
                {
                    $.ajax(
                            {
                                url: "<c:url value='/mwcUser/updatePreferredLanguage' />",
                                type: "POST",
                                data:
                                        {
                                            userId: ${mwcUser.id},
                                            prefLanguage: $("#preferredLanguage").val(),
                                        },
                                success: function()
                                {
                                    var changed = changeLocale($("#preferredLanguage").val(), "${messageConfirmChangeLocale}");
                                    if (!changed)
                                    {
                                        $("#preferredLanguage").val(currentLanguage);
                                    }
                                }

                            });
                });

            </script>
    </body>
</div>
</html>