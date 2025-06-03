package servlet;

import dao.MedicoJpaController;
import dto.Medico;
import util.hashBcrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/medicoController")
public class MedicoServlet extends HttpServlet {

    private MedicoJpaController medicoDAO;

    @Override
    public void init() {
        medicoDAO = new MedicoJpaController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("editar".equals(action)) {
                editarMedico(request, response);
            } else {
                agregarMedico(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(MedicoServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("buscar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Medico medico = medicoDAO.findMedico(id);
            if (medico != null) {
                response.setContentType("application/json");
                response.getWriter().write("{" +
                        "\"codiMedi\":" + medico.getCodiMedi() + "," +
                        "\"ndniMedi\":" + medico.getNdniMedi() + "," +
                        "\"appaMedi\":\"" + medico.getAppaMedi() + "\"," +
                        "\"apmaMedi\":\"" + medico.getApmaMedi() + "\"," +
                        "\"nombMedi\":\"" + medico.getNombMedi() + "\"," +
                        "\"fechNaciMedi\":\"" + new SimpleDateFormat("yyyy-MM-dd").format(medico.getFechNaciMedi()) + "\"," +
                        "\"logiMedi\":\"" + medico.getLogiMedi() + "\"}");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void agregarMedico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Medico medico = new Medico();

        medico.setNdniMedi(Integer.parseInt(request.getParameter("ndniMedi")));
        medico.setAppaMedi(request.getParameter("appaMedi"));
        medico.setApmaMedi(request.getParameter("apmaMedi"));
        medico.setNombMedi(request.getParameter("nombMedi"));

        String fechaStr = request.getParameter("fechNaciMedi");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse(fechaStr);
        medico.setFechNaciMedi(fecha);

        medico.setLogiMedi(request.getParameter("logiMedi"));

        String pass = request.getParameter("passMedi");
        if (pass != null && !pass.isEmpty()) {
            medico.setPassMedi(hashBcrypt.hashPassword(pass));
        }

        medicoDAO.create(medico);
        response.getWriter().print("OK");
    }

    private void editarMedico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int codiMedi = Integer.parseInt(request.getParameter("codiMedi"));

        Medico medico = medicoDAO.findMedico(codiMedi);
        if (medico == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        medico.setNdniMedi(Integer.parseInt(request.getParameter("ndniMedi")));
        medico.setAppaMedi(request.getParameter("appaMedi"));
        medico.setApmaMedi(request.getParameter("apmaMedi"));
        medico.setNombMedi(request.getParameter("nombMedi"));

        String fechaStr = request.getParameter("fechNaciMedi");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse(fechaStr);
        medico.setFechNaciMedi(fecha);

        medico.setLogiMedi(request.getParameter("logiMedi"));

        // No modificar contraseña en edición

        medicoDAO.edit(medico);
        response.getWriter().print("OK");
    }
}
