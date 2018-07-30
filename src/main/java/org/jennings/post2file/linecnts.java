/*
 * (C) Copyright 2017 David Jennings
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     David Jennings
 */
package org.jennings.post2file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davi5017
 */
public class linecnts extends HttpServlet {

    private int countLines(String filename) {
        int cnt = -1;

        try (LineNumberReader reader = new LineNumberReader(new FileReader(filename))) {
            cnt = 0;
            String lineRead = "";
            while ((lineRead = reader.readLine()) != null) {
            }

            cnt = reader.getLineNumber();
        } catch (IOException e) {
            cnt = -2;
        }

        return cnt;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String writeFolder = System.getenv("POST2FILE_WRITE_FOLDER");
            if (writeFolder == null) {
                String msg = "POST2FILE_WRITE_FOLDER environment variable must be set";
                System.err.println(msg);
                out.println(msg);

            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet linecnts</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<ul>");
                File path = new File(writeFolder);
                String filenames[] = path.list();
                Arrays.sort(filenames, Collections.reverseOrder());
                long totalCnt = 0L;
                for (String filename : filenames) {
                    int cnt = countLines(writeFolder + System.getProperty("file.separator") + filename);
                    totalCnt += cnt;
                    out.println("<li>" + filename + " : " + cnt + "</li>");
                }
                out.println("<li>Total : " + totalCnt + "</li>");
                out.println("</ul>");
                out.println("</body>");
                out.println("</html>");

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
