/*
 * Copyright (C) 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.openshift.employeerostering.gwtui.client.app.pages;

import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.employeeroster.EmployeeRosterPage;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.employees.EmployeesPage;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.rotations.RotationsPage;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.skills.SkillsPage;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.spotroster.SpotRosterPage;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.spots.SpotsPage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Dependent
public class Pages {

    public enum Id {
        SKILLS, SPOTS, EMPLOYEES, ROTATIONS, SPOT_ROSTER, EMPLOYEE_ROSTER;
    }

    @Inject
    private SkillsPage skillsPage;

    @Inject
    private SpotsPage spotsPage;

    @Inject
    private EmployeesPage employeesPage;

    @Inject
    private RotationsPage rotationsPage;

    @Inject
    private SpotRosterPage spotRosterPage;

    @Inject
    private EmployeeRosterPage employeeRosterPage;

    private Map<Id, Page> mapping = new HashMap<>();

    @PostConstruct
    public void init() {
        mapping.put(Id.SKILLS, skillsPage);
        mapping.put(Id.SPOTS, spotsPage);
        mapping.put(Id.EMPLOYEES, employeesPage);
        mapping.put(Id.ROTATIONS, rotationsPage);
        mapping.put(Id.SPOT_ROSTER, spotRosterPage);
        mapping.put(Id.EMPLOYEE_ROSTER, employeeRosterPage);
    }

    public Page get(final Id id) {
        return Optional.ofNullable(mapping.get(id)).orElseThrow(() -> new RuntimeException("Unmapped page " + id));
    }
}
