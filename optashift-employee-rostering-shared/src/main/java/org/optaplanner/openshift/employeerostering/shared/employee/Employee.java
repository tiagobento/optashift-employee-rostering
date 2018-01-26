/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.openshift.employeerostering.shared.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.optaplanner.openshift.employeerostering.shared.common.AbstractPersistable;
import org.optaplanner.openshift.employeerostering.shared.skill.Skill;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({ @NamedQuery(name = "Employee.findAll",
                            query = "select distinct e from Employee e left join fetch e.skillProficiencySet sp left join fetch sp.skill s"
                                    + " where e.tenantId = :tenantId"
                                    + " order by e.name, s.name"), })
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "tenantId", "name" }))
public class Employee extends AbstractPersistable {

    @NotNull
    @Size(min = 1, max = 120)
    private String name;
    @JsonManagedReference
    @NotNull
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeSkillProficiency> skillProficiencySet;

    @SuppressWarnings("unused")
    public Employee() {
    }

    public Employee(Integer tenantId, String name) {
        super(tenantId);
        this.name = name;
        skillProficiencySet = new HashSet<>(2);
    }

    public boolean hasSkill(Skill skill) {
        return skillProficiencySet.stream().anyMatch(skillProficiency -> skillProficiency.getSkill().equals(skill));
    }

    public boolean hasSkills(Collection<Skill> skills) {
        return skills.stream().allMatch((s) -> hasSkill(s));
    }

    @Override
    public String toString() {
        return name;
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkillProficiency> getSkillProficiencySet() {
        return skillProficiencySet;
    }

    public void setSkillProficiencySet(Set<EmployeeSkillProficiency> skillProficiencySet) {
        this.skillProficiencySet = skillProficiencySet;
    }

}
