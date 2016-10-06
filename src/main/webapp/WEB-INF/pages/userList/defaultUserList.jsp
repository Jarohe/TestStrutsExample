<%--
  Created by IntelliJ IDEA.
  User: derevyanko
  Date: 16.09.2016
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

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
