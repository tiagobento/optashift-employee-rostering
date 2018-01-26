package org.optaplanner.openshift.employeerostering.shared.lang.tokens;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.optaplanner.openshift.employeerostering.shared.common.AbstractPersistable;
import org.optaplanner.openshift.employeerostering.shared.jackson.LocalDateTimeDeserializer;
import org.optaplanner.openshift.employeerostering.shared.jackson.LocalDateTimeSerializer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Describes a shift to generate.<br>
 * Properties:<br>
 * {@link ShiftInfo#startTime} <br>
 * {@link ShiftInfo#endTime} <br>
 * {@link ShiftInfo#spotList} <br>
 * {@link ShiftInfo#employeeList} <br>
 * {@link ShiftInfo#exceptionList} (Nullable) <br>
 */
@Entity
public class ShiftInfo extends AbstractPersistable {

    /**
     * How long after the base date to create the shift. The time is calculated by the following formula:
     * <pre>
     * <code>
     * startTime.toEpochSecond(ZoneOffset.UTC) - LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC)
     * </code>
     * </pre>
     * This value must be before endTime
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDateTime startTime;

    /**
     * How long after the base date to end the shift. The time is calculated by the following formula:
     * <pre>
     * <code>
     * endTime.toEpochSecond(ZoneOffset.UTC) - LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC)
     * </code>
     * </pre>
     * This value must be after startTime.
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDateTime endTime;

    /**
     * List of spots/spot groups to create
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "orderIndex")
    List<IdOrGroup> spotList;

    /**
     * List of employees/employee groups and their availability. If an employee appear multiple
     * times or in multiple groups in this list, their last entry in the list determines their
     * availability
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "orderIndex")
    List<EmployeeTimeSlotInfo> employeeList;

    /**
     * List of conditions that causes this Shift not to be generated, potentially causing another
     * one to be generated instead. These are evalulated before {@link ShiftTemplate#universalExceptionList}.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "orderIndex")
    List<ShiftConditional> exceptionList;

    public ShiftInfo() {
    }

    public ShiftInfo(Integer tenantId, ShiftInfo src) {
        this(tenantId, src.startTime, src.endTime, src.spotList, src.employeeList, src.exceptionList);
    }

    public ShiftInfo(Integer tenantId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            List<IdOrGroup> spots,
            List<EmployeeTimeSlotInfo> employees) {
        super(tenantId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.spotList = spots;
        this.employeeList = employees;
    }

    public ShiftInfo(Integer tenantId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            List<IdOrGroup> spots,
            List<EmployeeTimeSlotInfo> employees,
            List<ShiftConditional> exceptions) {
        super(tenantId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.spotList = spots;
        this.employeeList = employees;
        this.exceptionList = exceptions;
    }

    /**
     * Getter for {@link ShiftInfo#startTime}
     *
     * @return Value of {@link ShiftInfo#startTime}
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for {@link ShiftInfo#endTime}
     *
     * @return Value of {@link ShiftInfo#endTime}
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for {@link ShiftInfo#spotList}
     *
     * @return Value of {@link ShiftInfo#spotList}
     */
    public List<IdOrGroup> getSpotList() {
        return spotList;
    }

    /**
     * Setter for {@link ShiftInfo#spotList}
     *
     * @param spots Value to set {@link ShiftInfo#spotList} to
     */
    public void setSpotList(List<IdOrGroup> spotList) {
        this.spotList = spotList;
    }

    /**
     * Getter for {@link ShiftInfo#employeeList}
     *
     * @return Value of {@link ShiftInfo#employeeList}
     */
    public List<EmployeeTimeSlotInfo> getEmployeeList() {
        return employeeList;
    }

    /**
     * Setter for {@link ShiftInfo#employeeList}
     *
     * @param employeeList Value to set {@link ShiftInfo#employeeList} to
     */
    public void setEmployeeList(List<EmployeeTimeSlotInfo> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     * Getter for {@link ShiftInfo#exceptionList}
     *
     * @return Value of {@link ShiftInfo#exceptionList}
     */
    public List<ShiftConditional> getExceptionList() {
        return exceptionList;
    }

    /**
     * Setter for {@link ShiftInfo#exceptionList}
     *
     * @param exceptionList Value to set {@link ShiftInfo#exceptionList} to
     */
    public void setExceptionList(List<ShiftConditional> exceptionList) {
        this.exceptionList = exceptionList;
    }

    /**
     * Setter for {@link ShiftInfo#startTime}
     *
     * @param startTime Value to set {@link ShiftInfo#startTime} to
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Setter for {@link ShiftInfo#endTime}
     *
     * @param endTime Value to set {@link ShiftInfo#endTime} to
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
