<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User Page</title>
    <link rel="stylesheet" type="text/css" href="/css/loginPage.css">
</head>
<body>
<jsp:include page="general/header.jsp"/>
<html:form action="/system/createUser" method="POST">
    <div>
        <html:errors/>
    </div>
    <table border="0">
        <tbody>
        <tr>
            <td>Username:</td>
            <td><html:text property="login"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><html:password property="password"/></td>
        </tr>
        <tr>
            <td>First Name:</td>
            <td><html:text property="firstName"/></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><html:text property="lastName"/></td>
        </tr>
        <tr>
            <td>Manager:</td>
            <td><html:checkbox property="manager"/></td>
        </tr>
        <tr>
            <td></td>
            <td><html:submit value="Create" /></td>
        </tr>
        </tbody>
    </table>
</html:form>
</body>
</html>
