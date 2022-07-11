<%-- 
    Document   : search
    Created on : Jun 8, 2022, 7:44:41 AM
    Author     : Dell
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>

        <%--  <!--Welcome,[username] with session-->
          <%
              HttpSession session1 = request.getSession(false);
              if (session1 != null) {
                  String username = (String) session1.getAttribute("username");
                  if (username != null) {
          %>
          <font color = "Red">
          Welcome, <%=username%>               

        </font>
        <%
                }

            } else {
                response.sendRedirect("login.html");
            }
        %>

        <!--logout-->
        <form action="DispatchServlet" method="POST">
            <input type="submit" name="btAction" value="Logout"/>
        </form>


        <h1>Search page</h1>
        <a href="DispatchServlet?btAction=shopping">Shopping</a>

        <form action="DispatchServlet">
            <%                String txtSearchValue = request.getParameter("txtSearchValue");
                if (txtSearchValue == null) {
                    txtSearchValue = "";
                }
            %>
            Search value <input type="text" name="txtSearchValue" value="<%= txtSearchValue%>" />
            <input type="submit" value="Search" name="btAction"/>
        </form>
        <%
            String searchValue = request.getParameter("txtSearchValue");
            if (searchValue != null) {
                List<RegistrationDTO> result = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
                if (result != null) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Full name</th>
                    <th>Role</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 0;
                    for (RegistrationDTO dto : result) {
                        String urlRewriting = "DispatchServlet"
                                + "?btAction=delete"
                                + "&pk=" //primary key of relation
                                + dto.getUsername()
                                + "&lastSearchValue=" + searchValue;
                %>
            <form action="DispatchServlet" method="POST">
                <tr>
                    <td>
                        <%= ++count%>
                    </td>
                    <td>
                        <%= dto.getUsername()%>
                        <input type="hidden" value="<%= dto.getUsername()%>" name="txtUsername" />

                    </td>

                    <td>
                        <input type="text" value="<%= dto.getPassword()%>" name="txtPassword" />

                    </td>
                    <td>
                        <%= dto.getFullName()%>
                    </td>
                    <td>
                        <input type="checkbox" name="chkAdmin"  
                               <%
                                   if (dto.isRole() == true) {
                               %>
                               checked ="checked"
                               <%
                                   }
                               %>

                               />
                        <%= dto.isRole()%>
                    </td>
                    <td><a href="<%=urlRewriting%>">Delete</a> </td>
                    <td>
                        <input type="hidden" name="lastSearchValue" value="<%= searchValue%>" />
                        <input type="submit" value="Update" name="btAction" />
                    </td>
                </tr>
            </form>
            <%
                } //end traverse result list
            %>

        </tbody>
    </table>

    <%        } else {

    %>
    <h2>No record is matched!</h2>
    <%                }
        } // end search Value has proceeded


    %>--%>

        <font color = "Red">
        Welcome, ${sessionScope.USER.fullName}               

        </font>
        <!--logout-->
        <form action="DispatchServlet" method="POST">
            <input type="submit" name="btAction" value="Logout"/>
        </form>


        <h1>Search page</h1>
        <a href="DispatchServlet?btAction=shopping">Shopping</a>

        <form action="DispatchServlet">

            Search value <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" />
            <input type="submit" value="Search" name="btAction"/>
        </form>
        <c:if test="${not empty param.txtSearchValue}" >
            <c:set var="searchResult" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty searchResult}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${searchResult}" varStatus="status">
                        <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>
                                    ${status.count}
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" value="${dto.username}" name="txtUsername" />
                                </td>

                                <td>
                                    <input type="text" value="${dto.password}" name="txtPassword" />
                                </td>
                                <td>
                                    ${dto.fullName}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON"
                                           <c:if test="${dto.role}">
                                               checked="checked"
                                           </c:if>
                                           />
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="DispatchServlet">
                                        <c:param name="btAction" value="delete"/>
                                        <c:param name="pk" value="${dto.username}"/>
                                        <c:param name="lastSearchValue" value="${param.txtSearchValue}"/>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                                <td>
                                    <input type="hidden" name="lastSearchValue" value="${param.txtSearchValue}" />
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>        
        </c:if>
        <c:if test="${empty searchResult}">
            <h2>Do not have any record!</h2>
        </c:if>
    </c:if>

</body>
</html>
