package servlet;
import dao.VentaDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DevolucionServlet")
public class DevolucionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        int ventaId =
                Integer.parseInt(
                        request.getParameter("id"));
        VentaDAO dao = new VentaDAO();
        HttpSession session =
                request.getSession();

        if (dao.devolverVenta(ventaId)) {
            session.setAttribute(
                    "mensaje",
                    "Venta devuelta correctamente.");
        } else {
            session.setAttribute(
                    "mensaje",
                    "No se pudo realizar la devolución.");
        }
        response.sendRedirect("reportes.jsp");
    }
}