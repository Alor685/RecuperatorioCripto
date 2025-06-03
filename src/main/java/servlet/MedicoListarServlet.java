package servlet;

import com.google.gson.Gson;
import dao.MedicoJpaController;
import dto.Medico;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listarMedicos")
public class MedicoListarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            MedicoJpaController dao = new MedicoJpaController();
            List<Medico> lista = dao.findMedicoEntities();

            // Serializamos con Gson
            Gson gson = new Gson();
            String json = gson.toJson(lista);
            out.print(json);
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\":\"Error al obtener médicos\"}");
        } finally {
            out.close();
        }
    }
}
