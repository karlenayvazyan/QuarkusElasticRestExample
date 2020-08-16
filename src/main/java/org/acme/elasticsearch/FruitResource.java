package org.acme.elasticsearch;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    FruitService fruitService;

    @POST
    public Response index(Fruit fruit) throws IOException {
        if (fruit.getId() == null) {
            fruit.setId(UUID.randomUUID().toString());
        }
        fruitService.index(fruit);
        return Response.created(URI.create("/fruits/" + fruit.getId())).build();
    }

    @GET
    @Path("/{id}")
    public Fruit get(@PathParam("id") String id) throws IOException {
        return fruitService.get(id);
    }

    @GET
    @Path("/search")
    public List<Fruit> search(@QueryParam("name") String name, @QueryParam("color") String color) throws IOException {
        if (name != null) {
            return fruitService.searchByName(name);
        } else if (color != null) {
            return fruitService.searchByColor(color);
        } else {
            throw new BadRequestException("Should provide name or color query parameter");
        }
    }
}