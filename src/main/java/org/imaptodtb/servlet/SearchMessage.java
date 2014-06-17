/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imaptodtb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.imaptodtb.log.IMAP;

/**
 *
 * @author memiks
 */
@WebServlet(name = "SearchMessage", urlPatterns = {"/SearchMessage"})
public class SearchMessage extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {

            String searchTerm = request.getParameter("search");
            String folderName = request.getParameter("folder");
            if (searchTerm != null && !"".equals(searchTerm.trim()) && folderName != null && !"".equals(folderName.trim())) {
                IMAP imap = new IMAP();
                out.println(imap.retrieveMailFromSearch(searchTerm, folderName));
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet SearchMessage</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>ERROR YOU MUST SPECIFY A SEARCH TERM !!</h1>");
                out.println("<h1>ERROR YOU MUST SPECIFY A FOLDER !!</h1>");
            }

        } catch (Exception ex) {
            out.println("<h1>Servlet ISSUE " + ex.getMessage() + "</h1>");
            Logger.getLogger(SearchMessage.class.getName()).log(Level.SEVERE, null, ex);
            out.println("<h1>Servlet SearchMessage at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
