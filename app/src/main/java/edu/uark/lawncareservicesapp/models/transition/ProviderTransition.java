package edu.uark.lawncareservicesapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

import edu.uark.lawncareservicesapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.lawncareservicesapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.lawncareservicesapp.models.api.Provider;

public class ProviderTransition implements Parcelable {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public ProviderTransition setId(UUID id) {
        this.id = id;
        return this;
    }

    private String password;
    public String  getPassword() { return this.password; }
    public ProviderTransition setPassword(String password) {
        this.password = password;
        return this;
    }

    private String providerName;
    public String getProviderName() {
        return providerName;
    }
    public ProviderTransition setProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    private String phoneNumber;
    public String getPhoneNumber(){return phoneNumber;}
    public ProviderTransition setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    private String emailAddress;
    public String getEmailAddress(){return emailAddress;}
    public ProviderTransition setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
        return this;
    }

    private String address;
    public String getAddress(){return address;}
    public ProviderTransition setAddress(String address){
        this.address = address;
        return this;
    }

    private String city;
    public String getCity() {return city;}
    public ProviderTransition setCity(String city){
        this.city = city;
        return this;
    }

    private String state;
    public String getState() {return state;}
    public ProviderTransition setState(String state){
        this.state = state;
        return this;
    }

    private int zip;
    public int getZip(){return zip;}
    public ProviderTransition setZip(int zip){
        this.zip = zip;
        return this;
    }

    private int ProviderId;
    public int getProviderId() {return ProviderId;}
    public ProviderTransition setProviderId(int ProviderId) {
        this.ProviderId = ProviderId;
        return this;
    }

    private boolean active;
    public boolean isActive() {
        return active;
    }
    public ProviderTransition setActive(boolean active) {
        this.active = active;
        return this;
    }


    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }
    public ProviderTransition setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public ProviderTransition setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
        destination.writeString(this.password);
        destination.writeString(this.providerName);
        destination.writeInt(this.ProviderId);
        destination.writeInt(this.active ? 1 : 0);
        destination.writeLong(this.createdAt.getTime());
        destination.writeLong(this.updatedAt.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ProviderTransition> CREATOR = new Parcelable.Creator<ProviderTransition>() {
        public ProviderTransition createFromParcel(Parcel ProviderTransitionParcel) {
            return new ProviderTransition(ProviderTransitionParcel);
        }

        public ProviderTransition[] newArray(int size) {
            return new ProviderTransition[size];
        }
    };

    public ProviderTransition() {
        this.id = new UUID(0, 0);
        this.password = "";
        this.providerName = "";
        this.ProviderId = -1;
        this.active = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public ProviderTransition(Provider Provider) {
        this.id = Provider.getId();
        this.password = Provider.getPassword();
        this.providerName = Provider.getProviderName();
        this.ProviderId = Provider.getProviderId();
        this.active = Provider.isActive();
        this.createdAt = Provider.getCreatedAt();
        this.updatedAt = Provider.getUpdatedAt();
    }

    private ProviderTransition(Parcel ProviderTransitionParcel) {
        this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(ProviderTransitionParcel.createByteArray()).execute();
        this.password = ProviderTransitionParcel.readString();
        this.providerName = ProviderTransitionParcel.readString();
        this.ProviderId = ProviderTransitionParcel.readInt();
        this.active = ProviderTransitionParcel.readInt() == 1 ? true : false;
        this.createdAt = new Date();
        this.createdAt.setTime(ProviderTransitionParcel.readLong());
        this.updatedAt = new Date();
        this.updatedAt.setTime(ProviderTransitionParcel.readLong());
    }
}
