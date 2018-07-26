
package Services;

import DAO.DAO_Ticket;
import DAO.Utils;
import Model.Ticket;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "tickets", urlPatterns = {"/tickets"})
public class tickets extends HttpServlet {

    DAO_Ticket dao_ticket = new DAO_Ticket();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        
        if(action.equals("getAll")){
            ArrayList<Ticket> tickets = dao_ticket.getAll();
            Gson gson = new Gson();
            String json = gson.toJson(tickets);
            out.print(json);
        }
        
        
    }

}
