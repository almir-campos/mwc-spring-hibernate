<%-- 
    Document   : avatarimg
    Created on : Aug 11, 2013, 4:19:41 PM
    Author     : almir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <%double rand = Math.random();%>
        <c:set var="rand" value = "<%= rand%>" />
        <img class="picture" src = "<c:url value='/mwcUser/avatarImg/${mwcUser.id}?rand=${rand}' />" />
    </body>
</html>
