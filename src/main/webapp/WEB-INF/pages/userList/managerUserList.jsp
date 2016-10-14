<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:errors/>

<table border="1">
    <tbody>
    <tr>
        <td>User name</td>
        <td>Delete</td>
    </tr>
    <logic:iterate id="userId" name="userList">
        <tr>
            <td>
                <bean:define id="id" name="userId" property="id"/>
                <html:link action="/system/editUser" paramId="id" paramName="id">
                    <bean:write name="userId" property="firstName"/> <bean:write name="userId" property="lastName"/>
                </html:link>
            </td>
            <td><html:link action="/system/deleteUser" paramId="id" paramName="id">delete</html:link></td>
        </tr>
    </logic:iterate>

    </tbody>
</table>
