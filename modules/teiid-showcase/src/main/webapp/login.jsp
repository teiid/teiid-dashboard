<%--

    Copyright (C) 2012 JBoss Inc

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.jboss.dashboard.LocaleManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/redhat/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/redhat/forms.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/redhat/login-screen.css">
    <title>Red Hat Data Virtualization</title>
</head>

<body id="login">
    <div id="rcue-login-screen">
      <img id="logo" src="<%=request.getContextPath()%>/redhat/login-screen-logo.png">
<div id="login-wrapper" class="png_bg">
    

    <div id="login-content">
        <%
            ResourceBundle i18nBundle = ResourceBundle.getBundle("org.jboss.dashboard.login", LocaleManager.currentLocale());
            String messageKey = request.getParameter("message");
            if (messageKey == null) messageKey = "login.hint";
        %>
     
        <form action="j_security_check" method="POST">
         <fieldset>
         <legend><img src="redhat/RH-Product-Name.png" alt="" /></legend>
            <p>
                <label><%= i18nBundle.getString("login.username") %></label>
                <input value="" name="j_username" type="text" autofocus/>
            </p>
            <br style="clear: both;"/>

            <p>
                <label><%= i18nBundle.getString("login.password") %></label>
                <input name="j_password" type="password"/>
            </p>
            <br style="clear: both;"/>

            <p>
                <input type="submit" value="Log In"/>
            </p>
		</fieldset>
        </form>
    </div>
</div>
</div>

</body>
</html>
