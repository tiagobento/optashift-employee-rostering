package org.optaplanner.openshift.employeerostering.gwtui.client.roster;

import com.google.gwt.user.client.Window;
import org.gwtbootstrap3.client.ui.html.Div;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.optaplanner.openshift.employeerostering.gwtui.client.calendar.Calendar;
import org.optaplanner.openshift.employeerostering.gwtui.client.common.FailureShownRestCallback;
import org.optaplanner.openshift.employeerostering.gwtui.client.spot.SpotData;
import org.optaplanner.openshift.employeerostering.gwtui.client.spot.SpotDataFetchable;
import org.optaplanner.openshift.employeerostering.gwtui.client.spot.SpotDrawable;
import org.optaplanner.openshift.employeerostering.gwtui.client.spot.SpotId;
import org.optaplanner.openshift.employeerostering.gwtui.client.spot.SpotNameFetchable;
import org.optaplanner.openshift.employeerostering.shared.employee.Employee;
import org.optaplanner.openshift.employeerostering.shared.roster.RosterRestServiceBuilder;
import org.optaplanner.openshift.employeerostering.shared.roster.view.SpotRosterView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Map;

@Templated
public class SpotRosterViewPanel extends AbstractRosterViewPanel {

    private SpotRosterView spotRosterView;
    private Map<Long, Employee> employeeMap;

    @Inject
    @DataField
    private Div container;

    private Calendar<SpotId, SpotData> calendar;

    @Inject
    private SyncBeanManager beanManager;

    @Inject
    private TranslationService CONSTANTS;

    boolean isDateSet = false;

    public SpotRosterViewPanel() {
    }

    @PostConstruct
    protected void initWidget() {
        super.init();
        initTable();
    }

    private void initTable() {
        calendar = new Calendar.Builder<SpotId, SpotData, SpotDrawable<SpotData>>(container, tenantId, CONSTANTS).fetchingGroupsFrom(
                new SpotNameFetchable(() -> getTenantId()))
                .withBeanManager(beanManager)
                .asTwoDayView((v, d, i) -> new SpotDrawable<>(v, d, i));
        calendar.setDataProvider(new SpotDataFetchable(calendar, () -> getTenantId()));
        Window.addResizeHandler(
                (e) -> calendar.setViewSize(e.getWidth() - container.getAbsoluteLeft(), e.getHeight() - container.getAbsoluteTop()));
    }

    @Override
    protected void refreshTable() {
        calendar.setViewSize(Window.getClientWidth() - container.getAbsoluteLeft(), Window.getClientHeight() - container.getAbsoluteTop());
        if (tenantId == null) {
            return;
        }
        if (!isDateSet) {
            RosterRestServiceBuilder.getCurrentSpotRosterView(tenantId, new FailureShownRestCallback<SpotRosterView>() {

                @Override
                public void onSuccess(SpotRosterView spotRosterView) {
                    isDateSet = true;
                    calendar.setDate(spotRosterView.getTimeSlotList()
                            .stream()
                            .min((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                            .get()
                            .getStartDateTime());
                }
            });
        }
        calendar.forceUpdate();
    }

    private int getTenantId() {
        return tenantId;
    }

}
