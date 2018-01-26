package org.optaplanner.openshift.employeerostering.shared.lang.tokens;

import org.optaplanner.openshift.employeerostering.shared.common.AbstractPersistable;
import org.optaplanner.openshift.employeerostering.shared.employee.EmployeeAvailabilityState;
import org.optaplanner.openshift.employeerostering.shared.lang.parser.DateMatcher;

import javax.persistence.Entity;

/**
 * Describes date related changes to {@link EmployeeAvailabilityState} and when they should happen.<br>
 * Properties:<br>
 * {@link EmployeeConditional#condition} <br>
 * {@link EmployeeConditional#avaliability} (Nullable) <br>
 */
@Entity
public class EmployeeConditional extends AbstractPersistable {

    /**
     * A date matcher written in the language described in
     * {@link DateMatcher}
     */
    String condition;

    /**
     * The {@link EmployeeAvailabilityState} to use for the employee/employee group
     * if {@link EmployeeConditional#condition} is met
     */
    EmployeeAvailabilityState avaliability;

    public EmployeeConditional() {

    }

    public EmployeeConditional(Integer tenantId, String condition, EmployeeAvailabilityState avaliability) {
        super(tenantId);
        this.condition = condition;
        this.avaliability = avaliability;
    }

    /**
     * Getter for {@link EmployeeConditional#condition}
     *
     * @return Value of {@link EmployeeConditional#condition}
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Setter for {@link EmployeeConditional#condition}
     *
     * @param condition Value to set {@link EmployeeConditional#condition} to
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Getter for {@link EmployeeConditional#avaliability}
     *
     * @return Value of {@link EmployeeConditional#avaliability}
     */
    public EmployeeAvailabilityState getAvaliability() {
        return avaliability;
    }

    /**
     * Setter for {@link EmployeeConditional#avaliability}
     *
     * @param avaliability Value to set {@link EmployeeConditional#avaliability} to
     */
    public void setAvaliability(EmployeeAvailabilityState avaliability) {
        this.avaliability = avaliability;
    }
}
