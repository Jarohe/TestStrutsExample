<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="/css/loginPage.css">
</head>
<body>
<html:form action="/login" method="POST">
    <html:errors />
    <p style="color: red"><bean:write name="loginForm" property="error" filter="false"/></p>
    <table border="0">
        <tbody>
        <tr>
            <td>Enter your username:</td>
            <td><html:text property="login" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><html:password property="pass"/></td>
        </tr>
        <tr>
            <td></td>
            <td><html:submit value="Login" /></td>
        </tr>
        </tbody>
    </table>
</html:form>
</body>
</html>
