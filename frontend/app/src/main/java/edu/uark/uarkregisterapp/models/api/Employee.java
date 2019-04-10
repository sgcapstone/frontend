package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.enums.EmployeeRole;
import edu.uark.uarkregisterapp.models.api.fields.EmployeeFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;


public class Employee implements ConvertToJsonInterface, LoadFromJsonInterface<Employee> {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public Employee setId(UUID id) {
        this.id = id;
        return this;
    }

    private String password;
    public String getPassword() { return this.password; }
    public Employee setPassword(String password) {
        this.password = password;
        return this;
    }

    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    private int employeeId;
    public int getEmployeeId() {
        return employeeId;
    }
    public Employee setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    private boolean active;
    public boolean isActive() {
        return active;
    }
    public Employee setActive(boolean active) {
        this.active = active;
        return this;
    }

    private EmployeeRole role;
    public EmployeeRole getRole() {
        return role;
    }
    public Employee setRole(EmployeeRole role) {
        this.role = role;
        return this;
    }

    private String managerId;
    public String getManagerId() {
        return managerId;
    }
    public Employee setManagerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }
    public Employee setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public Employee setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public static JSONObject convertLogInToJson(String employeeId, String password){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(EmployeeFieldName.EMPLOYEE_ID.getFieldName(), employeeId);
            jsonObject.put(EmployeeFieldName.PASSWORD.getFieldName(), password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public Employee loadFromJson(JSONObject rawJsonObject) {
        String value = rawJsonObject.optString(EmployeeFieldName.ID.getFieldName());
        if (!StringUtils.isBlank(value)) {
            this.id = UUID.fromString(value);
        }
        this.password = rawJsonObject.optString(EmployeeFieldName.PASSWORD.getFieldName());
        this.firstName = rawJsonObject.optString(EmployeeFieldName.FIRST_NAME.getFieldName());
        this.lastName = rawJsonObject.optString(EmployeeFieldName.LAST_NAME.getFieldName());
        this.employeeId = rawJsonObject.optInt(EmployeeFieldName.EMPLOYEE_ID.getFieldName());
        this.active = rawJsonObject.optBoolean(EmployeeFieldName.ACTIVE.getFieldName());

        this.role = EmployeeRole.fromString(rawJsonObject.optString(EmployeeFieldName.ROLE.getFieldName()));
        if (this.role == null) {
            throw new Error("Invalid employee role.");
        }

        this.managerId = rawJsonObject.optString(EmployeeFieldName.MANGER_ID.getFieldName());

        value = rawJsonObject.optString(EmployeeFieldName.CREATED_AT.getFieldName());
        if (!StringUtils.isBlank(value)) {
            try {
                this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        value = rawJsonObject.optString(EmployeeFieldName.UPDATED_AT.getFieldName());
        if (!StringUtils.isBlank(value)) {
            try {
                this.updatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(EmployeeFieldName.ID.getFieldName(), this.id.toString());
            jsonObject.put(EmployeeFieldName.PASSWORD.getFieldName(), this.password.toString());
            jsonObject.put(EmployeeFieldName.FIRST_NAME.getFieldName(), this.firstName);
            jsonObject.put(EmployeeFieldName.LAST_NAME.getFieldName(), this.lastName);
            jsonObject.put(EmployeeFieldName.EMPLOYEE_ID.getFieldName(), this.employeeId);
            jsonObject.put(EmployeeFieldName.ACTIVE.getFieldName(), this.active);
            jsonObject.put(EmployeeFieldName.ROLE.getFieldName(), this.role.getValue());
            jsonObject.put(EmployeeFieldName.MANGER_ID.getFieldName(), this.managerId);
            jsonObject.put(EmployeeFieldName.CREATED_AT.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdAt));
            jsonObject.put(EmployeeFieldName.UPDATED_AT.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdAt));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public Employee() {
        this.id = new UUID(0, 0);
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.employeeId = -1;
        this.active = false;
        this.role = EmployeeRole.CASHIER;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Employee(EmployeeTransition employeeTransition) {
        this.id = employeeTransition.getId();
        this.password = employeeTransition.getPassword();
        this.firstName = employeeTransition.getFirstName();
        this.lastName = employeeTransition.getLastName();
        this.employeeId = employeeTransition.getEmployeeId();
        this.active = employeeTransition.isActive();
        this.role = employeeTransition.getRole();
        this.createdAt = employeeTransition.getCreatedAt();
        this.updatedAt = employeeTransition.getUpdatedAt();
    }
}
