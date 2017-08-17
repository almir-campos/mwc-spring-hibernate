<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@page import="java.util.Random"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv='cache-control' content='no-cache'>
        <meta http-equiv='expires' content='0'>
        <meta http-equiv='pragma' content='no-cache'>
        <title>JSP Page</title><%@ include file = "../complements/includes/noscript.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <!--LABELS-->
        <spring:message code="label_user_add_title" var = "labelUserAddTitle" />
        <spring:message code="label_user_add_legend_identification" var = "labelUserAddLegendIdentification" />
        <spring:message code="label_user_add_legend_avatar" var = "labelUserAddLegendAvatar" />
        <spring:message code="label_user_add_legend_options" var = "labelUserAddLegendOptions" />
        <spring:message code="label_user_add_submit_button" var = "labelUserAddSubmitButton" />
        <!--FIELDS-->
        <spring:message code="label_user_id" var = "labelUserId" />
        <spring:message code="label_user_first_name" var = "labelUserFirstName" />
        <spring:message code="label_user_last_name" var = "labelUserLastName" />
        <spring:message code="label_user_display_name" var = "labelUserDisplayName" />
        <spring:message code="label_user_username" var = "labelUserUsername" />
        <spring:message code="label_user_password" var = "labelUserPassword" />
        <spring:message code="label_user_birth_date" var = "labelUserBirthDate" />
        <spring:message code="label_user_preferred_language" var = "labelUserPreferredLanguage" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <script>menuBuilder("userAdd", "${messageConfirmChangeLocale}", "", "");</script>
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelUserAddTitle}</h1>
                <% Random r = new Random();
            int rand = r.nextInt();%>
                <form 
                    id="updateAvatarImgForm" 
                    action="<c:url value='/mwcUser/addUser'/>"
                    enctype="multipart/form-data"
                    method="post">
                    <fieldset>
                        <legend>${labelUserAddLegendIdentification}</legend>
                        <label for="firstName">${labelUserFirstName}:</label>
                        <input type ='text' name="firstName" id="firstName" value='mwcUser<%=rand%>'/>
                        <br />
                        <label for="lastName">${labelUserLastName}</label>
                        <input type ='text' name="lastName" id="lastName" value='mwcUser<%=rand%>'/>
                        <br />
                        <label for="displayName">${labelUserDisplayName}:</label>
                        <input type ='text' name="displayName" id="displayName" value='mwcUser<%=rand%>'/>
                        <br />
                        <label for="username">${labelUserUsername}:</label>
                        <input type ='text' name="username" id="username" value='mwcUser<%=rand%>'/>
                        <br />
                        <label for="password">${labelUserPassword}:</label>
                        <input type ='text' name="password" id="password" value='mwcUser<%=rand%>'/>
                        <br />
                        <label for="birthDate">${labelUserBirthDate}:<br />(yyyy-mm-dd)</label>
                        <input type ='text' name="birthDate" id="birthDate" value='2013-01-01'/>
                        <br />
                    </fieldset>
                    <fieldset>
                        <legend>${labelUserAddLegendAvatar}</legend>
                        <input id="avatarImgInput" type="file" name="file"/>
                    </fieldset>
                    <fieldset>
                        <legend>${labelUserAddLegendOptions}</legend>
                        <label for="preferredLanguage">${labelUserPreferredLanguage}:</label>
                        <select name="preferredLanguage">
                            <c:forEach items="${languages}" var="lang">
                                <option value="${lang.code}">${lang.description}</option>
                            </c:forEach>
                        </select>
                    </fieldset>
                    <input type="submit" value="${labelUserAddSubmitButton}" />
                </form>
            </div>
        </div>
    </body>
</html>