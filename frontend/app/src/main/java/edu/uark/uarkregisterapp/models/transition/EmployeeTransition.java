package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeRole;
import edu.uark.uarkregisterapp.models.api.Employee;

public class EmployeeTransition implements Parcelable {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public EmployeeTransition setId(UUID id) {
        this.id = id;
        return this;
    }

    private String password;
    public String  getPassword() { return this.password; }
    public EmployeeTransition setPassword(String password) {
        this.password = password;
        return this;
    }

    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public EmployeeTransition setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public EmployeeTransition setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    private int employeeId;
    public int getEmployeeId() {
        return employeeId;
    }
    public EmployeeTransition setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    private boolean active;
    public boolean isActive() {
        return active;
    }
    public EmployeeTransition setActive(boolean active) {
        this.active = active;
        return this;
    }

    private EmployeeRole role;
    public EmployeeRole getRole() {
        return role;
    }
    public EmployeeTransition setRole(EmployeeRole role) {
        this.role = role;
        return this;
    }

    private String managerId;
    public String getManagerId() {
        return managerId;
    }
    public EmployeeTransition setManagerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }
    public EmployeeTransition setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public EmployeeTransition setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
        destination.writeString(this.password);
        destination.writeString(this.firstName);
        destination.writeString(this.lastName);
        destination.writeInt(this.employeeId);
        destination.writeInt(this.active ? 1 : 0);
        destination.writeString(this.role.getValue());
        destination.writeString(this.managerId);
        destination.writeLong(this.createdAt.getTime());
        destination.writeLong(this.updatedAt.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<EmployeeTransition> CREATOR = new Parcelable.Creator<EmployeeTransition>() {
        public EmployeeTransition createFromParcel(Parcel employeeTransitionParcel) {
            return new EmployeeTransition(employeeTransitionParcel);
        }

        public EmployeeTransition[] newArray(int size) {
            return new EmployeeTransition[size];
        }
    };

    public EmployeeTransition() {
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

    public EmployeeTransition(Employee employee) {
        this.id = employee.getId();
        this.password = employee.getPassword();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.employeeId = employee.getEmployeeId();
        this.active = employee.isActive();
        this.role = employee.getRole();
        this.createdAt = employee.getCreatedAt();
        this.updatedAt = employee.getUpdatedAt();
    }

    private EmployeeTransition(Parcel employeeTransitionParcel) {
        this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(employeeTransitionParcel.createByteArray()).execute();
        this.password = employeeTransitionParcel.readString();
        this.firstName = employeeTransitionParcel.readString();
        this.lastName = employeeTransitionParcel.readString();
        this.employeeId = employeeTransitionParcel.readInt();
        this.active = employeeTransitionParcel.readInt() == 1 ? true : false;
        this.role = EmployeeRole.fromString(employeeTransitionParcel.readString());

        this.createdAt = new Date();
        this.createdAt.setTime(employeeTransitionParcel.readLong());

        this.updatedAt = new Date();
        this.updatedAt.setTime(employeeTransitionParcel.readLong());
    }
}
