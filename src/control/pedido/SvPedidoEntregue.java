package control.pedido;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entidade.DonoRestaurante;
import model.entidade.Pedido;


@WebServlet("/SvPedidoEntregue")
public class SvPedidoEntregue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DonoRestaurante d = (DonoRestaurante) request.getSession().getAttribute("dono");

		long id = Long.parseLong(request.getParameter("idPedido"));
		
		Pedido p = new Pedido(id);
		
		p = p.pegarDados();
		
		p.setStatus("Entregue");
		
		p.getCliente().setPedidoEmAberto(false);
		
		p.getRestaurante().setFaturamento(p.getRestaurante().getFaturamento() + p.getValorTotal());
		
		if (p.editarDados()) {
			
			d = d.pegarDados();
			
			if (d.getRestaurante().listarPedidos()) {

				request.getSession().setAttribute("dono", d);
				
				request.setAttribute("msgSucess", "Pedido entregue com sucesso!");
				
				request.getRequestDispatcher("restaurantePedidos.jsp").forward(request, response);
				
			} else {
				
				request.setAttribute("msgErro", d.msg);

				request.getRequestDispatcher("menuPrincipal.jsp").forward(request, response);
			
			}
			
		} else {
			
			request.setAttribute("msgErro", p.msg);
			
			request.getRequestDispatcher("menuPrincipal.jsp").forward(request, response);
	
		}
		
	}

}
