package org.optaplanner.openshift.employeerostering.server.common;

import org.optaplanner.openshift.employeerostering.shared.common.AbstractPersistable;

import java.util.Objects;

public class AbstractRestServiceImpl {

    protected void validateTenantIdParameter(Integer tenantId, AbstractPersistable persistable) {
        if (!Objects.equals(persistable.getTenantId(), tenantId)) {
            throw new IllegalStateException("The tenantId ("
                    + tenantId
                    + ") does not match the persistable ("
                    + persistable
                    + ")'s tenantId ("
                    + persistable.getTenantId()
                    + ").");
        }
    }

}
