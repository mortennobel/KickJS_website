package org.kickjs.shadereditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirect request
 */
public class Redirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = req.getRequestURI()+"?"+req.getQueryString();
        request = "/tool"+request.substring("/example".length());
        //resp.sendRedirect(request);
        getServletContext().getRequestDispatcher(request).forward(req, resp);
    }
}