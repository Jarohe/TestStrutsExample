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

<html:errors/>
    <table border="1">
        <tbody>
        <tr>
            <td>User name</td>
        </tr>
        <logic:iterate id="userId" name="userList">
            <tr>
                <td>
                    <bean:write name="userId" property="firstName"/> <bean:write name="userId" property="lastName"/>
                </td>
            </tr>
        </logic:iterate>

        </tbody>
    </table>
</body>
</html>
