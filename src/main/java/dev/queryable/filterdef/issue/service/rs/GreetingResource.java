package dev.queryable.filterdef.issue.service.rs;

import dev.queryable.filterdef.issue.model.Area;
import dev.queryable.filterdef.issue.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello")
@ApplicationScoped
public class GreetingResource {

    @Inject
    EntityManager entityManager;


    public GreetingResource() {
    }

    @Transactional
    public void init() {
        for (int i = 0; i < 100; i++) {
            Area area = new Area();
            area.name = "name " + i;
            area.description = "description " + i;
            if (i % 2 == 0) {
                area.active = "N";
            } else {
                area.active = "S";
            }

            area.persistAndFlush();
            User user = new User();
            user.name = "name " + i;
            user.surname = "name " + i;
            if (i % 2 == 0) {
                user.active = false;
            } else {
                user.active = true;
            }
            user.persistAndFlush();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    @Path(value = "/init")
    public String initRest() {
        init();
        return "Hello INIT";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String hello() {
        PanacheQuery<Area> areaPanacheQuery = Area.find(null);
        areaPanacheQuery.filter("obj.name", Parameters.with("name", "%" + "name"));
        areaPanacheQuery.filter("obj.active", Parameters.with("active", ""));
        List<Area> areaList = areaPanacheQuery.list();
        System.out.println(areaList);

        PanacheQuery<User> userPanacheQuery = User.find(null);
        userPanacheQuery.filter("obj.name", Parameters.with("name", "%" + "name"));
        userPanacheQuery.filter("obj.active", Parameters.with("active", Boolean.TRUE));
        List<User> userList = userPanacheQuery.list();
        System.out.println(userList);
        return "Hello RESTEasy";
    }
}