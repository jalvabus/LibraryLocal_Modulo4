
package Services;

import DAO.DAO_Tarjeta;
import DAO.Utils;
import Model.Bitacora_tarjeta;
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


@WebServlet(name = "txt_tarjeta", urlPatterns = {"/txt_tarjeta"})
@MultipartConfig
public class txt_tarjeta extends HttpServlet {
  
    Utils utils = new Utils();
    DAO_Tarjeta dao_tarjeta = new DAO_Tarjeta();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Part filePart = request.getPart("file_txt");
        
        ArrayList<Bitacora_tarjeta> listBitacora = dao_tarjeta.createByBitacora(utils.getBitacoraTarjeta(utils.getTarjetas(utils.InputStreamtoString(filePart.getInputStream()))));
        
        Gson gson = new Gson();
        String json = gson.toJson(listBitacora);
        out.print(json);
    }

}
