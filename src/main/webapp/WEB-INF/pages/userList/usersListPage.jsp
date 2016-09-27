<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<bean:define id="role" name="sessionUser" property="role" scope="session"/>
<html>
<head>
    <title>UserList</title>
</head>
<body>
<h1>
    Type: <bean:write name="role"/> Success Login: <bean:write name="sessionUser" property="firstName"/>
    <bean:write name="sessionUser" property="lastName"/>
</h1>

<jsp:include page="../general/header.jsp"/>

<logic:equal name="role" value="MANAGER">
    <jsp:include page="managerUserList.jsp"/>
</logic:equal>
<logic:equal name="role" value="DEFAULT">
    <jsp:include page="defaultUserList.jsp"/>
</logic:equal>
</body>
</html>
