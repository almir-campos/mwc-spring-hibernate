<%-- 
    Document   : about
    Created on : Oct 7, 2013, 10:06:33 AM
    Author     : Almir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        <!--CSS-->
        <style type="text/css">
            li{ margin-top: 0.5em; }
            .leftColumn{ float: left; width: 48%;}
            .rightColumn{ float: right; width: 48% }
        </style>
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.tabs.css' />" />
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" ></script> 
    <!--SCRIPTS-->
    <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
    <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>

    <!--PAGE MESSAGES-->
    <spring:message code="page.about.title" var="pageAboutTitle" />

    <spring:message code="page.about.disclaimer.title" var="pageAboutDisclaimerTitle" />
    <spring:message code="page.about.disclaimer.left.column" var="pageAboutDisclaimerLeftColumn" />
    <spring:message code="page.about.disclaimer.right.column" var="pageAboutDisclaimerRightColumn" />

    <spring:message code="page.about.purpose.title" var="pageAboutPurposeTitle" />
    <spring:message code="page.about.purpose.left.column" var="pageAboutPurposeLeftColumn" />
    <spring:message code="page.about.purpose.right.column" var="pageAboutPurposeRightColumn" />

    <spring:message code="page.about.functionalities.title" var="pageAboutFunctionalitiesTitle" />
    <spring:message code="page.about.functionalities.left.column" var="pageAboutFunctionalitiesLeftColumn" />
    <spring:message code="page.about.functionalities.right.column" var="pageAboutFunctionalitiesRightColumn" />

    <spring:message code="page.about.technologies.title" var="pageAboutTechnologiesTitle" />
    <spring:message code="page.about.technologies.left.column" var="pageAboutTechnologiesLeftColumn" />
    <spring:message code="page.about.technologies.right.column" var="pageAboutTechnologiesRightColumn" />

    <spring:message code="page.about.techniques.title" var="pageAboutTechniquesTitle" />
    <spring:message code="page.about.techniques.left.column" var="pageAboutTechniquesLeftColumn" />
    <spring:message code="page.about.techniques.right.column" var="pageAboutTechniquesRightColumn" />

    <spring:message code="page.about.further.development.title" var="pageAboutFurtherDevelopmentTitle" />
    <spring:message code="page.about.further.development.left.column" var="pageAboutFurtherDevelopmentLeftColumn" />
    <spring:message code="page.about.further.development.right.column" var="pageAboutFurtherDevelpmentRightColumn" />

    <spring:message code="page.about.developer.title" var="pageAboutDeveloperTitle" />
    <spring:message code="page.about.developer.left.column" var="pageAboutDeveloperLeftColumn" />
    <spring:message code="page.about.developer.right.column" var="pageAboutDeveloperRightColumn" />
    <!--VARIABLES-->
    <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    <sec:authorize var="isLogged" access="isAuthenticated()" />
    <c:set var='viewName' value='about' />

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
        <div class='mainDiv'><%@ include file = "../complements/includes/outterTopBar.jspf" %>
            <h1>${pageAboutTitle}</h1>

            <div id ="tabs">
                <ul>
                    <li><a href="#tab-disclaimer">${pageAboutDisclaimerTitle}</a></li>
                    <li><a href="#tab-purpose">${pageAboutPurposeTitle}</a></li>
                    <li><a href="#tab-functionalities">${pageAboutFunctionalitiesTitle}</a></li>
                    <li><a href="#tab-technologies">${pageAboutTechnologiesTitle}</a></li>
                    <li><a href="#tab-techniques">${pageAboutTechniquesTitle}</a></li>
                    <!--<li><a href="#tab-further-development">${pageAboutFurtherDevelopmentTitle}</a></li>-->
                    <li><a href="#tab-developer">${pageAboutDeveloperTitle}</a></li>
                </ul>
                <div id="tab-disclaimer">
                    <div class="leftColumn">
                        ${pageAboutDisclaimerLeftColumn}
                        
                    </div>
                    <div class ="rightColumn">
                        ${pageAboutDisclaimerRightColumn}
                       
                    </div>
                </div>

                <div id="tab-purpose">
                    <div class="leftColumn">
                        ${pageAboutPurposeLeftColumn}
                    </div>
                    <div class="rightColumn">
                        ${pageAboutPurposeRightColumn}
                         
                    </div>
                </div>

                <div id="tab-functionalities">
                    <div class="leftColumn">
                        ${pageAboutFunctionalitiesLeftColumn}
                        
                    </div>
                    <div class="rightColumn">
                        ${pageAboutFunctionalitiesRightColumn}
                    </div>
                </div>

                <div id="tab-technologies">
                    <div class="leftColumn">
                        ${pageAboutTechnologiesLeftColumn} 
                    </div>

                    <div class="rightColumn">
                        ${pageAboutTechnologiesRightColumn} 
                    </div>
                </div>

                <div id="tab-techniques">
                    <div class="leftColumn">
                        ${pageAboutTechniquesLeftColumn}
                        
                    </div>

                    <div class="rightColumn">
                        ${pageAboutTechniquesRightColumn} 
                    </div>
                </div>

<!--                <div id="tab-further-development">
                    <div class="leftColumn">
                        ${pageAboutFurtherDevelpmentLeftColumn} 
                    </div>

                    <div class="rightColumn">
                        ${pageAboutFurtherDevelpmentRightColumn} 
                    </div>
                </div>-->

                <div id="tab-developer">
                    <div class="leftColumn">
                        ${pageAboutDeveloperLeftColumn} 
                    </div>

                    <div class="rightColumn">
                        ${pageAboutDeveloperRightColumn} 
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        $("#tabs").tabs();
        function activateTab( tab )
        {
            $("#tabs").tabs("option", "active", tab );
        }
    </script>
</body>
</html>
