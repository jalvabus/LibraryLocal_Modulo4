
package Services;

import DAO.DAO_Ticket;
import DAO.Utils;
import Model.Bitacora_tickets;
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
import javax.servlet.http.Part;


@WebServlet(name = "txt_ticket", urlPatterns = {"/txt_ticket"})
@MultipartConfig
public class txt_ticket extends HttpServlet {

    Utils utils = new Utils();
    DAO_Ticket dao_ticket = new DAO_Ticket();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Part filePart = request.getPart("file_txt");
        
        // ArrayList<Bitacora_tickets> bitacora = dao_ticket.createByBitacora(utils.getBitacoraTickets(utils.getTickets(utils.InputStreamtoString(filePart.getInputStream()))));
        
        Gson gson = new Gson();
        // String json = gson.toJson(bitacora);
        // out.print(json);
    }

   
}
