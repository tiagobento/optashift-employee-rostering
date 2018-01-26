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

package org.optaplanner.openshift.employeerostering.shared.tenant;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NamedQueries({ @NamedQuery(name = "Tenant.findAll", query = "select t from Tenant t" +
        // Deliberately order by id instead of name to use generated order
        " order by t.id"), })
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Tenant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PlanningId
    protected Integer id;
    @Version
    protected Long version;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private TenantConfiguration configuration;

    @NotNull
    @Size(min = 1, max = 120)
    private String name;

    @SuppressWarnings("unused")
    public Tenant() {
        this.configuration = new TenantConfiguration();
    }

    public Tenant(String name) {
        this.name = name;
        this.configuration = new TenantConfiguration();
    }

    public Tenant(String name, TenantConfiguration configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tenant other = (Tenant) o;
        if (id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return ((id == null) ? 0 : id.hashCode());
    }

    @Override
    public String toString() {
        return name;
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        configuration.setTenantId(id);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TenantConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(TenantConfiguration configuration) {
        this.configuration = configuration;
        configuration.setTenantId(id);
    }

}
