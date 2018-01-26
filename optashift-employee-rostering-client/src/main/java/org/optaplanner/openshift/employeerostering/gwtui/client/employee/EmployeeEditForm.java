package org.optaplanner.openshift.employeerostering.gwtui.client.employee;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.tagsinput.client.ui.base.SingleValueTagsInput;
import org.gwtbootstrap3.extras.typeahead.client.base.CollectionDataset;
import org.gwtbootstrap3.extras.typeahead.client.base.Dataset;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.optaplanner.openshift.employeerostering.gwtui.client.common.FailureShownRestCallback;
import org.optaplanner.openshift.employeerostering.gwtui.client.popups.FormPopup;
import org.optaplanner.openshift.employeerostering.shared.employee.Employee;
import org.optaplanner.openshift.employeerostering.shared.employee.EmployeeRestServiceBuilder;
import org.optaplanner.openshift.employeerostering.shared.employee.EmployeeSkillProficiency;
import org.optaplanner.openshift.employeerostering.shared.skill.Skill;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Templated
public class EmployeeEditForm implements IsElement {

    @Inject
    @DataField
    private TextBox employeeName;

    @Inject
    @DataField
    private SingleValueTagsInput<Skill> employeeSkills;

    @Inject
    @DataField
    private Button saveButton;

    @Inject
    @DataField
    private Button cancelButton;

    @Inject
    @DataField
    private Button closeButton;

    @Inject
    @DataField
    private @Named(value = "h3")
    HeadingElement title;

    @Inject
    private TranslationService CONSTANTS;

    private static Employee employee;
    private static EmployeeListPanel panel;
    private static List<Skill> skills;

    private FormPopup popup;

    public static EmployeeEditForm create(SyncBeanManager beanManager,
            EmployeeListPanel employeePanel,
            Employee employeeData,
            List<Skill> skillData) {
        panel = employeePanel;
        employee = employeeData;
        skills = skillData;
        return beanManager.lookupBean(EmployeeEditForm.class).newInstance();
    }

    @PostConstruct
    protected void initWidget() {
        employeeName.setValue(employee.getName());
        employeeSkills.removeAll();
        CollectionDataset<Skill> data = new CollectionDataset<Skill>(skills) {

            @Override
            public String getValue(Skill skill) {
                return (skill == null) ? "" : skill.getName();
            }
        };
        employeeSkills.setDatasets((Dataset<Skill>) data);
        employeeSkills.setItemValue(Skill::getName);
        employeeSkills.setItemText(Skill::getName);
        employeeSkills.reconfigure();
        employeeSkills.add(employee.getSkillProficiencySet().stream().map((p) -> p.getSkill()).collect(Collectors.toList()));
        employee.getSkillProficiencySet().stream().map((p) -> p.getSkill()).forEach((s) -> employeeSkills.add(s));

        title.setInnerSafeHtml(new SafeHtmlBuilder().appendEscaped(employee.getName()).toSafeHtml());
        popup = FormPopup.getFormPopup(this);
        popup.center();
    }

    @EventHandler("cancelButton")
    public void cancel(ClickEvent e) {
        popup.hide();
    }

    @EventHandler("closeButton")
    public void close(ClickEvent e) {
        popup.hide();
    }

    @EventHandler("saveButton")
    public void save(ClickEvent click) {
        employee.setName(employeeName.getValue());
        Map<Skill, EmployeeSkillProficiency> proficiencyMap = employee.getSkillProficiencySet()
                .stream()
                .collect(Collectors.toMap(EmployeeSkillProficiency::getSkill, Function.identity()));
        List<Skill> skillList = employeeSkills.getItems();
        for (Skill skill : skillList) {
            if (proficiencyMap.containsKey(skill)) {
                proficiencyMap.remove(skill);
            } else {
                EmployeeSkillProficiency proficiency = new EmployeeSkillProficiency(employee.getTenantId(), employee, skill);
                employee.getSkillProficiencySet().add(proficiency);
            }
        }
        for (EmployeeSkillProficiency proficiency : proficiencyMap.values()) {
            employee.getSkillProficiencySet().remove(proficiency);
        }
        popup.hide();
        EmployeeRestServiceBuilder.updateEmployee(employee.getTenantId(), employee, new FailureShownRestCallback<Employee>() {

            @Override
            public void onSuccess(Employee employee) {
                panel.refresh();
            }
        });

    }

}
