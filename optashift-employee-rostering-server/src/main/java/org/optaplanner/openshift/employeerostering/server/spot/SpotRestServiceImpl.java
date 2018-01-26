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

package org.optaplanner.openshift.employeerostering.server.spot;

import org.optaplanner.openshift.employeerostering.server.common.AbstractRestServiceImpl;
import org.optaplanner.openshift.employeerostering.shared.spot.Spot;
import org.optaplanner.openshift.employeerostering.shared.spot.SpotGroup;
import org.optaplanner.openshift.employeerostering.shared.spot.SpotRestService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class SpotRestServiceImpl extends AbstractRestServiceImpl implements SpotRestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Spot> getSpotList(Integer tenantId) {
        return entityManager.createNamedQuery("Spot.findAll", Spot.class).setParameter("tenantId", tenantId).getResultList();
    }

    @Override
    @Transactional
    public Spot getSpot(Integer tenantId, Long id) {
        Spot spot = entityManager.find(Spot.class, id);
        validateTenantIdParameter(tenantId, spot);
        return spot;
    }

    @Override
    @Transactional
    public Spot addSpot(Integer tenantId, Spot spot) {
        validateTenantIdParameter(tenantId, spot);
        entityManager.persist(spot);
        return spot;
    }

    @Override
    @Transactional
    public Spot updateSpot(Integer tenantId, Spot spot) {
        validateTenantIdParameter(tenantId, spot);
        spot = entityManager.merge(spot);
        return spot;
    }

    @Override
    @Transactional
    public Boolean removeSpot(Integer tenantId, Long id) {
        Spot spot = entityManager.find(Spot.class, id);
        if (spot == null) {
            return false;
        }
        validateTenantIdParameter(tenantId, spot);
        entityManager.remove(spot);
        return true;
    }

    @Override
    public List<SpotGroup> getSpotGroups(Integer tenantId) {
        return entityManager.createNamedQuery("SpotGroup.findAll", SpotGroup.class).setParameter("tenantId", tenantId).getResultList();
    }

    @Override
    public SpotGroup getSpotGroup(Integer tenantId, Long id) {
        SpotGroup group = entityManager.find(SpotGroup.class, id);
        validateTenantIdParameter(tenantId, group);
        return group;
    }

    @Override
    @Transactional
    public Long createSpotGroup(Integer tenantId, SpotGroup spotGroup) {
        validateTenantIdParameter(tenantId, spotGroup);
        entityManager.persist(spotGroup);
        return spotGroup.getId();
    }

    @Override
    @Transactional
    public void addSpotToSpotGroup(Integer tenantId, Long id, Spot spot) {
        SpotGroup group = getSpotGroup(tenantId, id);
        validateTenantIdParameter(tenantId, spot);
        group.getSpots().add(spot);
        entityManager.merge(group);

    }

    @Override
    @Transactional
    public void removeSpotFromSpotGroup(Integer tenantId, Long id, Spot spot) {
        SpotGroup group = getSpotGroup(tenantId, id);
        validateTenantIdParameter(tenantId, spot);
        group.getSpots().remove(spot);
        entityManager.merge(group);
    }

    @Override
    @Transactional
    public Boolean deleteSpotGroup(Integer tenantId, Long id) {
        SpotGroup group = entityManager.find(SpotGroup.class, id);
        if (group == null) {
            return false;
        }
        validateTenantIdParameter(tenantId, group);
        entityManager.remove(group);
        return true;
    }

    @Override
    public SpotGroup findSpotGroupByName(Integer tenantId, String name) {
        return entityManager.createNamedQuery("SpotGroup.findByName", SpotGroup.class)
                .setParameter("tenantId", tenantId)
                .setParameter("name", name)
                .getSingleResult();
    }

}
