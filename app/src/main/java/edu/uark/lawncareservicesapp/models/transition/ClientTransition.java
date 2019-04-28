package edu.uark.lawncareservicesapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

import edu.uark.lawncareservicesapp.LoginActivity;
import edu.uark.lawncareservicesapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.lawncareservicesapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.lawncareservicesapp.models.api.enums.ClientRole;
import edu.uark.lawncareservicesapp.models.api.Client;

public class ClientTransition implements Parcelable {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public ClientTransition setId(UUID id) {
        this.id = id;
        return this;
    }

    private String password;
    public String  getPassword() { return this.password; }
    public ClientTransition setPassword(String password) {
        this.password = password;
        return this;
    }

    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public ClientTransition setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public ClientTransition setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    private String phoneNumber;
    public String getPhoneNumber(){return phoneNumber;}
    public ClientTransition setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    private String emailAddress;
    public String getEmailAddress(){return emailAddress;}
    public ClientTransition setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
        return this;
    }

    private String address;
    public String getAddress(){return address;}
    public ClientTransition setAddress(String address){
        this.address = address;
        return this;
    }

    private String city;
    public String getCity() {return city;}
    public ClientTransition setCity(String city){
        this.city = city;
        return this;
    }

    private String state;
    public String getState() {return state;}
    public ClientTransition setState(String state){
        this.state = state;
        return this;
    }

    private int zip;
    public int getZip(){return zip;}
    public ClientTransition setZip(int zip){
        this.zip = zip;
        return this;
    }

    private int clientId;
    public int getClientId() {return clientId;}
    public ClientTransition setClientId(int clientId) {
        this.clientId = clientId;
        return this;
    }

    private boolean active;
    public boolean isActive() {
        return active;
    }
    public ClientTransition setActive(boolean active) {
        this.active = active;
        return this;
    }

    private ClientRole role;
    public ClientRole getRole() {
        return role;
    }
    public ClientTransition setRole(ClientRole role) {
        this.role = ClientRole.fromString(LoginActivity.ReturnRole());
        return this;
    }

    private String managerId;
    public String getManagerId() {
        return managerId;
    }
    public ClientTransition setManagerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }
    public ClientTransition setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public ClientTransition setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
        destination.writeString(this.password);
        destination.writeString(this.firstName);
        destination.writeString(this.lastName);
        destination.writeInt(this.clientId);
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

    public static final Parcelable.Creator<ClientTransition> CREATOR = new Parcelable.Creator<ClientTransition>() {
        public ClientTransition createFromParcel(Parcel clientTransitionParcel) {
            return new ClientTransition(clientTransitionParcel);
        }

        public ClientTransition[] newArray(int size) {
            return new ClientTransition[size];
        }
    };

    public ClientTransition() {
        this.id = new UUID(0, 0);
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.clientId = -1;
        this.active = false;
        this.role = ClientRole.fromString(LoginActivity.ReturnRole());
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public ClientTransition(Client client) {
        this.id = client.getId();
        this.password = client.getPassword();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.clientId = client.getClientId();
        this.active = client.isActive();
        this.role = client.getRole();
        this.createdAt = client.getCreatedAt();
        this.updatedAt = client.getUpdatedAt();
    }

    private ClientTransition(Parcel clientTransitionParcel) {
        this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(clientTransitionParcel.createByteArray()).execute();
        this.password = clientTransitionParcel.readString();
        this.firstName = clientTransitionParcel.readString();
        this.lastName = clientTransitionParcel.readString();
        this.clientId = clientTransitionParcel.readInt();
        this.active = clientTransitionParcel.readInt() == 1 ? true : false;
        this.role = ClientRole.fromString(LoginActivity.ReturnRole());

        this.createdAt = new Date();
        this.createdAt.setTime(clientTransitionParcel.readLong());

        this.updatedAt = new Date();
        this.updatedAt.setTime(clientTransitionParcel.readLong());
    }
}
