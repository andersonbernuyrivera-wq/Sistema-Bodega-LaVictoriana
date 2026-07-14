package api;

import dao.ProductoDAO;
import modelo.Producto;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

@Path("productos")
public class ProductoAPI {
    ProductoDAO dao = new ProductoDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Producto> listar(){
        return dao.listar();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Producto obtenerProducto(@PathParam("id") int id) {
        Producto p = dao.buscarPorId(id);
        if (p == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        return p;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarProducto(Producto p) {

        boolean registrado = dao.agregar(p);

        if (registrado) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity("Producto registrado correctamente")
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("No se pudo registrar el producto")
                    .build();
        }
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarProducto(@PathParam("id") int id,
                                       Producto p) {
        p.setId(id);
        boolean actualizado = dao.actualizar(p);
        if (actualizado) {
            return Response
                    .ok("Producto actualizado correctamente")
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("No se pudo actualizar el producto")
                    .build();
        }
    }
}