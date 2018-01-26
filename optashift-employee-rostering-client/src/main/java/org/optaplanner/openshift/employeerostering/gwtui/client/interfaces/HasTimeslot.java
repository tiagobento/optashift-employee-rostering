package org.optaplanner.openshift.employeerostering.gwtui.client.interfaces;

import org.optaplanner.openshift.employeerostering.gwtui.client.calendar.HasTitle;

import java.time.LocalDateTime;

public interface HasTimeslot<G extends HasTitle> {

    G getGroupId();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();
}
