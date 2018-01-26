package org.optaplanner.openshift.employeerostering.gwtui.client;

import com.github.nmorel.gwtjackson.rest.api.RestRequestBuilder;
import org.jboss.errai.common.client.dom.Document;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.optaplanner.openshift.employeerostering.gwtui.client.app.MenuPanel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@EntryPoint
@Bundle("resources/i18n/OptaShiftUIConstants.properties")
public class OptaShiftRosteringEntryPoint {

    static {
        // Keep in sync with web.xml
        RestRequestBuilder.setDefaultApplicationPath("/rest");
    }

    @Inject
    private Document document;

    @Inject
    private MenuPanel menuPanel;

    @PostConstruct
    public void onModuleLoad() {
        document.getBody().appendChild(menuPanel.getElement());
        /*GWT.setUncaughtExceptionHandler(new
                GWT.UncaughtExceptionHandler() {
                public void onUncaughtException(Throwable e) {
                  ErrorPopup.show(Arrays.asList(e.getStackTrace()).stream().reduce("", (a,b) -> a.toString() + "\n" + b.toString(), (a,b) -> a + "\n" + b));
              }
          });*/
    }
}