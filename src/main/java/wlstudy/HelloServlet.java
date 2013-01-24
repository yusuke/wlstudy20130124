/*
 * Copyright 2013 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wlstudy;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@WebServlet(name="hello", urlPatterns={"/hello"})
public class HelloServlet extends HttpServlet {
    @Resource(name = "jdbc/wlstudy")
    private javax.sql.DataSource wlstudyConnection;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = wlstudyConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM emp")) {
            PrintWriter writer = response.getWriter();
            writer.print("<html><body>");
            while (rs.next()) {
                writer.print("name:"+ rs.getString("ename") +
                        " empno:"+ rs.getString("empno") + "<br>");
            }
            writer.print("</body></html>");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
