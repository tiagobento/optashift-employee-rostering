package org.optaplanner.openshift.employeerostering.shared.roster.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.openshift.employeerostering.shared.employee.Employee;
import org.optaplanner.openshift.employeerostering.shared.jackson.LocalDateDeserializer;
import org.optaplanner.openshift.employeerostering.shared.jackson.LocalDateSerializer;
import org.optaplanner.openshift.employeerostering.shared.spot.Spot;
import org.optaplanner.openshift.employeerostering.shared.timeslot.TimeSlot;
import org.optaplanner.persistence.jackson.api.score.ScoreJacksonJsonSerializer;
import org.optaplanner.persistence.jackson.api.score.buildin.hardsoft.HardSoftScoreJacksonJsonDeserializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class AbstractRosterView implements Serializable {

    @NotNull
    protected Integer tenantId;
    @NotNull
    protected LocalDate startDate; // inclusive
    @NotNull
    protected LocalDate endDate; // inclusive
    @NotNull
    protected List<Spot> spotList;
    @NotNull
    protected List<Employee> employeeList;
    @NotNull
    protected List<TimeSlot> timeSlotList;

    private HardSoftScore score = null;

    @Override
    public String toString() {
        return startDate + " to " + endDate;
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void setTimeSlotList(List<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    @JsonSerialize(using = ScoreJacksonJsonSerializer.class)
    @JsonDeserialize(using = HardSoftScoreJacksonJsonDeserializer.class)
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

}
