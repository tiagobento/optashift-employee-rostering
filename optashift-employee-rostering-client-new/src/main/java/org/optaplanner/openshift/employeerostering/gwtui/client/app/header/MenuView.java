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

package org.optaplanner.openshift.employeerostering.gwtui.client.app.header;

import com.google.gwt.event.dom.client.ClickEvent;
import elemental2.dom.HTMLAnchorElement;
import org.jboss.errai.ui.client.local.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.AppView;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.Pages;

import javax.enterprise.event.Event;
import javax.inject.Inject;

@Templated
public class MenuView implements IsElement {

    @Inject
    @DataField("skills")
    private HTMLAnchorElement skills;

    @Inject
    @DataField("spots")
    private HTMLAnchorElement spots;

    @Inject
    @DataField("employees")
    private HTMLAnchorElement employees;

    @Inject
    @DataField("rotations")
    private HTMLAnchorElement rotations;

    @Inject
    @DataField("spot-roster")
    private HTMLAnchorElement spotRoster;

    @Inject
    @DataField("employee-roster")
    private HTMLAnchorElement employeeRoster;

    @Inject
    private Event<AppView.PageChange> pageChangeEvent;

    @EventHandler("skills")
    public void skills(final ClickEvent e) {
        pageChangeEvent.fire(new AppView.PageChange(Pages.Id.SKILLS));
    }

    @EventHandler("spots")
    public void spots(final ClickEvent e) {
        pageChangeEvent.fire(new AppView.PageChange(Pages.Id.SPOTS));
    }

    @EventHandler("employees")
    public void employees(final ClickEvent e) {
        pageChangeEvent.fire(new AppView.PageChange(Pages.Id.EMPLOYEES));
    }

    @EventHandler("rotations")
    public void rotations(final ClickEvent e) {
        pageChangeEvent.fire(new AppView.PageChange(Pages.Id.ROTATIONS));
    }

    @EventHandler("spot-roster")
    public void spotRoster(final ClickEvent e) {
        pageChangeEvent.fire(new AppView.PageChange(Pages.Id.SPOT_ROSTER));
    }

    @EventHandler("employee-roster")
    public void employeeRoster(final ClickEvent e) {
        pageChangeEvent.fire(new AppView.PageChange(Pages.Id.EMPLOYEE_ROSTER));
    }
}
