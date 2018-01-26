/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.openshift.employeerostering.server.tenant;

import org.optaplanner.openshift.employeerostering.server.common.AbstractRestServiceImpl;
import org.optaplanner.openshift.employeerostering.shared.tenant.Tenant;
import org.optaplanner.openshift.employeerostering.shared.tenant.TenantConfiguration;
import org.optaplanner.openshift.employeerostering.shared.tenant.TenantRestService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class TenantRestServiceImpl extends AbstractRestServiceImpl implements TenantRestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Tenant> getTenantList() {
        return entityManager.createNamedQuery("Tenant.findAll", Tenant.class).getResultList();
    }

    @Override
    @Transactional
    public Tenant getTenant(Long id) {
        Tenant tenant = entityManager.find(Tenant.class, id);
        return tenant;
    }

    @Override
    @Transactional
    public Tenant addTenant(Tenant tenant) {
        entityManager.persist(tenant);
        return tenant;
    }

    @Override
    @Transactional
    public Tenant updateTenantConfiguration(TenantConfiguration tenantConfiguration) {
        Tenant tenant = entityManager.find(Tenant.class, tenantConfiguration.getTenantId());
        if (null == tenant) {
            throw new IllegalStateException("There is no tenant with id (" + tenantConfiguration.getTenantId() + ").");
        }
        tenant.setConfiguration(tenantConfiguration);
        return entityManager.merge(tenant);
    }

}
