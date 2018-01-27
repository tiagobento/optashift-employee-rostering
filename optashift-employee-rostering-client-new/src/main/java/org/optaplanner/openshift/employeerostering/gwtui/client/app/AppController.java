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

package org.optaplanner.openshift.employeerostering.gwtui.client.app;

import elemental2.dom.HTMLElement;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.pages.Pages;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppController {

    @Inject
    private AppView view;

    @Inject
    private Pages pages;

    public void onPageChanged(final @Observes AppView.PageChange pageChange) {
        view.goTo(pages.get(pageChange.getPageId()));
    }

    public HTMLElement getElement() {
        return view.getElement();
    }
}
