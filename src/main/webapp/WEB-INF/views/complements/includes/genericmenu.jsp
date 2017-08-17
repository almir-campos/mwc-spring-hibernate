<%-- 
    Document   : genericmenu1
    Created on : Aug 16, 2013, 1:19:45 PM
    Author     : almir
    Comment    : See the menu map at https://docs.google.com/spreadsheet/ccc?key=0Ar2zAMH5Bv0RdHk2TV8tZW1GV1lRTFdGYmF4OGVudXc#gid=0
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file = "noscript.jspf" %>
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/browser-detect.js' />" type="text/javascript" ></script>
        <!--MENU OPTIONS-->

        <spring:message code="menu.option.greetings" var="menuOptionGreetings" />
        <spring:message code="menu.option.general.options" var="menuOptionGeneralOptions" />
        <spring:message code="menu.option.general.options.company" var="menuOptionGeneralOptionsCompany" />
        <spring:message code="menu.option.general.options.company.list.companies" var="menuOptionGeneralOptionsCompanyListcompanies" />
        <spring:message code="menu.option.general.options.company.add.company" var="menuOptionGeneralOptionsCompanyAddCompany" />
        <spring:message code="menu.option.general.options.person" var="menuOptionGeneralOptionsPerson" />
        <spring:message code="menu.option.general.options.person.list.all.people" var="menuOptionGeneralOptionsPersonListAllPeople" />
        <spring:message code="menu.option.general.options.user" var="menuOptionGeneralOptionsUser" />
        <spring:message code="menu.option.general.options.user.list.all.users" var="menuOptionGeneralOptionsUserListAllUsers" />
        <spring:message code="menu.option.my.options" var="menuOptionMyOptions" />
        <spring:message code="menu.option.my.options.my.company" var="menuOptionMyOptionsMyCompany" />
        <spring:message code="menu.option.my.options.my.company.show" var="menuOptionMyOptionsMyCompanyShow" />
        <spring:message code="menu.option.my.options.my.company.edit" var="menuOptionMyOptionsMyCompanyEdit" />
        <spring:message code="menu.option.my.options.my.company.add.branch" var="menuOptionMyOptionsMyCompanyAddBranch" />
        <spring:message code="menu.option.my.options.my.company.branches" var="menuOptionMyOptionsMyCompanyBranches" />
        <spring:message code="menu.option.my.options.my.branch" var="menuOptionsMyOptionsMyBranch" />
        <spring:message code="menu.option.my.options.my.branch.show" var="menuOptionsMyOptionsMyBranchShow" />
        <spring:message code="menu.option.my.options.my.branch.list.people" var="menuOptionsMyOptionsMyBranchListPeople" />
        <spring:message code="menu.option.my.options.my.branch.edit" var="menuOptionsMyOptionsMyBranchEdit" />
        <spring:message code="menu.option.my.options.my.managed.people" var="menuOptionsMyOptionsMyManagedPeople" />
        <spring:message code="menu.option.my.options.my.managed.people.add" var="menuOptionsMyOptionsMyManagedPeopleAdd" />
        <spring:message code="menu.option.my.options.my.managed.people.list.people" var="menuOptionsMyOptionsMyManagedPeopleListPeople" />
        <spring:message code="menu.option.my.options.my.personal.data" var="menuOptionsMyOptionsMyPersonalData" />
        <spring:message code="menu.option.my.options.my.personal.data.show" var="menuOptionsMyOptionsMyPersonalDataShow" />
        <spring:message code="menu.option.my.options.my.personal.data.edit" var="menuOptionsMyOptionsMyPersonalDataEdit" />
        <spring:message code="menu.option.my.options.my.user" var="menuOptionsMyOptionsMyUser" />
        <spring:message code="menu.option.my.options.my.user.show" var="menuOptionsMyOptionsMyUserShow" />
        <spring:message code="menu.option.my.options.my.user.edit" var="menuOptionsMyOptionsMyUserEdit" />
        <spring:message code="menu.option.my.options.my.series" var="menuOptionsMyOptionsMySeries" />
        <spring:message code="menu.option.my.options.my.series.list" var="menuOptionsMyOptionsMySeriesList" />
        <spring:message code="menu.option.my.options.my.series.add" var="menuOptionsMyOptionsMySeriesAdd" />
        <spring:message code="menu.option.my.options.my.series.edit" var="menuOptionsMyOptionsMySeriesEdit" />
        <spring:message code="menu.option.my.options.other" var="menuOptionsMyOptionsOther" />
        <spring:message code="menu.option.my.options.other.my.log" var="menuOptionsMyOptionsOtherMyLog" />
        <spring:message code="menu.option.my.options.other.logout" var="menuOptionsMyOptionsOtherLogout" />
        
        <!--MISC MESSAGES-->
        <spring:message code="warning.click.to.see.details" var="warningClickToSeeDetails" />
        
        <!--VARIABLES-->
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <c:if test="${not empty company}">
        </c:if>
    </script>
</head>
<body>
    <div id="menu1Div">
        ${menuOptionGreetings}, <a href='${ctx}/mwcUser/show/${loggedUser.id}'>${loggedUser.displayName}</a>.
        <br />
        <c:if test="${empty loggedPerson}">
            <c:set var="loggedPerson" value="${loggedUser.person}" /> 
        </c:if>
        <c:if test="${empty loggedBranch}">
            <c:set var="loggedBranch" value="${loggedUser.person.branch}" /> 
        </c:if>
        <c:if test="${empty loggedCompany}">
            <c:set var="loggedCompany" value="${loggedUser.person.branch.company}" />
        </c:if>

        <br/>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <!--GENERAL OPTIONS-->
            <div class="submenuTitle">${menuOptionGeneralOptions}</div>
            <!--GENERAL OPTIONS-->

            <!--COMPANY-->
            <div class='menuTitle'>${menuOptionGeneralOptionsCompany}</div>
            <a href='${ctx}/company/listcompanies' class='menuItem'>${menuOptionGeneralOptionsCompanyListcompanies}</a>
            <br />
            <a href='${ctx}/company/add' class='menuItem'>${menuOptionGeneralOptionsCompanyAddCompany}</a>
            <br />

            <!--PERSON-->
            <div class='menuTitle'>${menuOptionGeneralOptionsPerson}</div>
            <a href='${ctx}/person/listpeople' class='menuItem'>${menuOptionGeneralOptionsPersonListAllPeople}</a>
            <br />

            <!--USER-->
            <div class='menuTitle'>${menuOptionGeneralOptionsUser}</div>
            <a href='${ctx}/mwcUser/listusers' class='menuItem'>${menuOptionGeneralOptionsUserListAllUsers}</a>
            <br />
            <br />
            <div class="submenuTitle">${menuOptionMyOptions}</div>    
        </sec:authorize>

        <c:if test="${not empty loggedCompany}">
            <!--MY OPTIONS-->
            <div class='menuTitle'>${menuOptionMyOptionsMyCompany}</div>
            <!--MY OPTIONS-->
            <!--COMPANY-->
            <a href='${ctx}/company/show/${loggedCompany.id}' class='menuItem'>${menuOptionMyOptionsMyCompanyShow}</a>
            <br />
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href='${ctx}/company/edit/${loggedCompany.id}' class='menuItem'>${menuOptionMyOptionsMyCompanyEdit}</a>
                <br />
            </sec:authorize>
            <a href='${ctx}/branch/add/${loggedCompany.id}' class='menuItem'>${menuOptionMyOptionsMyCompanyAddBranch}</a>
            <br />
            <a href='${ctx}/company/listbranches/${loggedCompany.id}' class='menuItem'>${menuOptionMyOptionsMyCompanyBranches}</a>
            <br />
        </c:if>

        <c:if test="${not empty loggedCompany}">
            <!--MY OPTIONS-->
            <!--BRANCH-->
            <div class='menuTitle'>${menuOptionsMyOptionsMyBranch}</div>
            <c:if test="${not empty loggedBranch}">
                <a href='${ctx}/branch/show/${loggedBranch.id}' class='menuItem'>${menuOptionsMyOptionsMyBranchShow}</a>
                <br />
                <a href='${ctx}/branch/listpeople/${loggedBranch.id}' class='menuItem'>${menuOptionsMyOptionsMyBranchListPeople}</a>
                <br />
            </c:if>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                <c:if test="${not empty loggedCompany}">
                    <c:if test="${not empty loggedBranch}">
                        <a href='${ctx}/branch/edit/${loggedBranch.id}' class='menuItem'>${menuOptionsMyOptionsMyBranchEdit}</a>
                        <br />
                    </c:if>
                </c:if>
            </sec:authorize>
        </c:if>

        <%--<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">--%>
        <!--MY OPTIONS-->
        <!--MANAGED PEOPLE-->
        <div class='menuTitle'>${menuOptionsMyOptionsMyManagedPeople}</div>
        <a href='${ctx}/person/add' class='menuItem'>${menuOptionsMyOptionsMyManagedPeopleAdd}</a>
        <br />
        <a href='${ctx}/person/listmanagedpeople' class='menuItem'>${menuOptionsMyOptionsMyManagedPeopleListPeople}</a>
        <br />
        <%--</sec:authorize>--%>

        <!--MY OPTIONS-->
        <!--PERSONAL DATA-->
        <div class='menuTitle'>${menuOptionsMyOptionsMyPersonalData}</div>
        <a href='${ctx}/person/show/${loggedUser.person.id}' class='menuItem'>${menuOptionsMyOptionsMyPersonalDataShow}</a>
        <br />
        <a href='${ctx}/person/edit/${loggedUser.person.id}' class='menuItem'>${menuOptionsMyOptionsMyPersonalDataEdit}</a>
        <br />

        <!--MY OPTIONS-->
        <!--USER-->
        <div class='menuTitle'>${menuOptionsMyOptionsMyUser}</div>
        <a href='${ctx}/mwcUser/show/${loggedUser.id}' class='menuItem'>${menuOptionsMyOptionsMyUserShow}</a>
        <br />
        <a href='${ctx}/mwcUser/edit/${loggedUser.id}' class='menuItem'>${menuOptionsMyOptionsMyUserEdit}</a>
        <br />

        <!--MY OPTIONS-->
        <!--SERIES-->
        <div class='menuTitle'>${menuOptionsMyOptionsMySeries}</div>
        <a href='${ctx}/mwcUser/listseries/${loggedUser.id}' class='menuItem'>${menuOptionsMyOptionsMySeriesList}</a>
        <br />
        <a href='${ctx}/series/add/${loggedUser.id}' class='menuItem'>${menuOptionsMyOptionsMySeriesAdd}</a>
        <br />

        <!--MY OPTIONS-->
        <!--OTHER-->
        <div class='menuTitle'>${menuOptionsMyOptionsOther}</div>
        <a href='${ctx}/mwcUser/log/${loggedUser.id}' class='menuItem'>${menuOptionsMyOptionsOtherMyLog}</a>
        <br />
        <a href='${ctx}/public/about' class='menuItem'>About</a>
        <br />
        <a href='${ctx}/j_spring_security_logout' class='menuItem'>${menuOptionsMyOptionsOtherLogout}</a>

        <br />
        <br />
    </div>
    <div id="browserDetectDiv" onclick="warningMessage();" class="warning"></div>
    <script>
        if (BrowserDetect.browser !== "Firefox" || BrowserDetect.version < 20)
        {
            document.getElementById("browserDetectDiv").innerHTML = "${warningClickToSeeDetails}";
        }
    </script>
    <!--        <img 
                src='${ctx}/resources/images/flags/br.gif'
                style='cursor: pointer; float: left; margin-right: 5px;'
                class="localeFlag"
                id="pt_BR" />
            <img 
                src='${ctx}/resources/images/flags/us.gif' 
                style='cursor: pointer; float: left; margin-left: 5px;' 
                class="localeFlag"
                id="en_US" />
        </div>
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
                changeLocale(locale, "", "${currentId}");
            });
        </script>-->
</body>
</html>