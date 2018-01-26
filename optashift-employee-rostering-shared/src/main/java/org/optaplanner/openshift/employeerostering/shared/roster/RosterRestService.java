package org.optaplanner.openshift.employeerostering.shared.roster;

import com.github.nmorel.gwtjackson.rest.processor.GenRestBuilder;
import org.optaplanner.openshift.employeerostering.shared.employee.Employee;
import org.optaplanner.openshift.employeerostering.shared.roster.view.EmployeeRosterView;
import org.optaplanner.openshift.employeerostering.shared.roster.view.SpotRosterView;
import org.optaplanner.openshift.employeerostering.shared.spot.Spot;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tenant/{tenantId}/roster")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GenRestBuilder
public interface RosterRestService {

    @GET
    @Path("/spotRosterView/current")
    SpotRosterView getCurrentSpotRosterView(@PathParam("tenantId") Integer tenantId);

    @GET
    @Path("/spotRosterView")
    SpotRosterView getSpotRosterView(@PathParam("tenantId") Integer tenantId,
            @QueryParam("startDate") String startDateString,
            @QueryParam("endDate") String endDateString);

    //TODO: find out if there a way to pass lists in GET requests
    @POST
    @Path("/spotRosterView/for")
    SpotRosterView getSpotRosterViewFor(@PathParam("tenantId") Integer tenantId,
            @QueryParam("startDate") String startDateString,
            @QueryParam("endDate") String endDateString,
            List<Spot> spots);

    @GET
    @Path("/employeeRosterView/current")
    EmployeeRosterView getCurrentEmployeeRosterView(@PathParam("tenantId") Integer tenantId);

    @GET
    @Path("/employeeRosterView")
    EmployeeRosterView getEmployeeRosterView(@PathParam("tenantId") Integer tenantId,
            @QueryParam("startDate") String startDateString,
            @QueryParam("endDate") String endDateString);

    @POST
    @Path("/employeeRosterView/for")
    EmployeeRosterView getEmployeeRosterViewFor(@PathParam("tenantId") Integer tenantId,
            @QueryParam("startDate") String startDateString,
            @QueryParam("endDate") String endDateString,
            List<Employee> employees);

    @POST
    @Path("/solve")
    void solveRoster(@PathParam("tenantId") Integer tenantId);

    @POST
    @Path("/terminate")
    void terminateRosterEarly(@PathParam("tenantId") Integer tenantId);

    // Not a REST method
    Roster buildRoster(Integer tenantId);

    // Not a REST method
    void updateShiftsOfRoster(Roster newRoster);

}
