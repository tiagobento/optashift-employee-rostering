package org.optaplanner.openshift.employeerostering.shared.employee;

import com.github.nmorel.gwtjackson.rest.processor.GenRestBuilder;
import org.optaplanner.openshift.employeerostering.shared.common.AbstractPersistable;
import org.optaplanner.openshift.employeerostering.shared.employee.view.EmployeeAvailabilityView;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tenant/{tenantId}/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GenRestBuilder
public interface EmployeeRestService {

    @GET
    @Path("/")
    List<Employee> getEmployeeList(@PathParam("tenantId") Integer tenantId);

    /**
     * @param id never null
     * @return never null, the employee with the id
     */
    @GET
    @Path("/{id}")
    Employee getEmployee(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id);

    /**
     * @param employee never null
     * @return never null, with a {@link AbstractPersistable#getId()} that is never null
     */
    @POST
    @Path("/add")
    Employee addEmployee(@PathParam("tenantId") Integer tenantId, Employee employee);

    /**
     * @param employee never null
     * @return never null, with an updated {@link AbstractPersistable#getVersion()}
     */
    @POST
    @Path("/update/{id}")
    Employee updateEmployee(@PathParam("tenantId") Integer tenantId, Employee employee);

    /**
     * @param id never null
     * @return true if the employee was removed, false otherwise
     */
    @DELETE
    @Path("/{id}")
    Boolean removeEmployee(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id);

    /**
     * @param employeeAvailability never null
     * @return never null, the id
     */
    @POST
    @Path("/availability/add")
    Long addEmployeeAvailability(@PathParam("tenantId") Integer tenantId, EmployeeAvailabilityView employeeAvailability);

    /**
     * @param employeeAvailability never null
     */
    @PUT
    @Path("/availability/update")
    void updateEmployeeAvailability(@PathParam("tenantId") Integer tenantId, EmployeeAvailabilityView employeeAvailability);

    @GET
    @Path("/groups/")
    List<EmployeeGroup> getEmployeeGroups(@PathParam("tenantId") Integer tenantId);

    @GET
    @Path("/groups/{id}")
    EmployeeGroup getEmployeeGroup(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id);

    @GET
    @Path("/groups/find/{name}")
    EmployeeGroup findEmployeeGroupByName(@PathParam("tenantId") Integer tenantId, @PathParam("name") String name);

    @POST
    @Path("/groups/create")
    Long createEmployeeGroup(@PathParam("tenantId") Integer tenantId, EmployeeGroup employeeGroup);

    @POST
    @Path("/groups/{id}/add")
    void addEmployeeToEmployeeGroup(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id, Employee employee);

    @POST
    @Path("/groups/{id}/remove")
    void removeEmployeeFromEmployeeGroup(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id, Employee employee);

    @POST
    @Path("/groups/delete/{id}")
    Boolean deleteEmployeeGroup(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id);

    /**
     * @param id never null
     * @return never null, the id
     */
    @DELETE
    @Path("/availability/{id}")
    Boolean removeEmployeeAvailability(@PathParam("tenantId") Integer tenantId, @PathParam("id") Long id);

}
