<%--
  Created by IntelliJ IDEA.
  User: derevyanko
  Date: 16.09.2016
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<bean:define id="role" name="sessionUser" property="role" scope="session"/>
<logic:equal name="role" value="MANAGER">
    <nav>
        <html:link action="/system/usersList"><h3>List Users</h3></html:link>
        <html:link action="/system/signUp"><h3>Create User</h3></html:link>
        <html:link action="/system/signOut"><h3>Exit</h3></html:link>
    </nav>
</logic:equal>
<logic:equal name="role" value="DEFAULT">
    <html:link action="/system/signOut"><h3>Exit</h3></html:link>
</logic:equal>