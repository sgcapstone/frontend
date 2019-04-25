package edu.uark.lawncareservicesapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.lawncareservicesapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.lawncareservicesapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.lawncareservicesapp.models.transition.ProviderTransition;
import edu.uark.lawncareservicesapp.models.api.fields.ProviderFieldName;


public class Provider implements ConvertToJsonInterface, LoadFromJsonInterface<Provider> {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public Provider setId(UUID id) {
        this.id = id;
        return this;
    }

    private String password;
    public String getPassword() { return this.password; }
    public Provider setPassword(String password) {
        this.password = password;
        return this;
    }

    private String providerName;
    public String getProviderName() {
        return providerName;
    }
    public Provider setProviderName(String firstName) {
        this.providerName = firstName;
        return this;
    }



    private String phoneNumber;
    public String getPhoneNumber(){return phoneNumber;}
    public Provider setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    private String emailAddress;
    public String getEmailAddress(){return emailAddress;}
    public Provider setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
        return this;
    }

    private String address;
    public String getAddress() {return address;}
    public Provider setAddress(String address){
        this.address = address;
        return this;
    }
    private String city;
    public String getCity() {return city;}
    public Provider setCity(String city){
        this.city = city;
        return this;
    }

    private String state;
    public String getState() {return state;}
    public Provider setState(String state){
        this.state = state;
        return this;
    }

    private int zip;
    public int getZip(){return zip;}
    public Provider setZip(int zip){
        this.zip = zip;
        return this;
    }

    private int ProviderId;
    public int getProviderId() {
        return ProviderId;
    }
    public Provider setProviderId(int ProviderId) {
        this.ProviderId = ProviderId;
        return this;
    }

    private boolean active;
    public boolean isActive() {
        return active;
    }
    public Provider setActive(boolean active) {
        this.active = active;
        return this;
    }


    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }
    public Provider setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public Provider setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public static JSONObject convertLogInToJson(String ProviderId, String password){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(ProviderFieldName.Provider_ID.getFieldName(), ProviderId);
            jsonObject.put(ProviderFieldName.PASSWORD.getFieldName(), password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public Provider loadFromJson(JSONObject rawJsonObject) {
        String value = rawJsonObject.optString(ProviderFieldName.ID.getFieldName());
        if (!StringUtils.isBlank(value)) {
            this.id = UUID.fromString(value);
        }
        this.password = rawJsonObject.optString(ProviderFieldName.PASSWORD.getFieldName());
        this.providerName = rawJsonObject.optString(ProviderFieldName.PROVIDER_NAME.getFieldName());
        this.address = rawJsonObject.optString(ProviderFieldName.ADDRESS.getFieldName());
        this.city = rawJsonObject.optString(ProviderFieldName.CITY.getFieldName());
        this.state = rawJsonObject.optString(ProviderFieldName.STATE.getFieldName());
        this.zip = rawJsonObject.optInt(ProviderFieldName.ZIP.getFieldName());
        this.ProviderId = rawJsonObject.optInt(ProviderFieldName.Provider_ID.getFieldName());
        this.active = rawJsonObject.optBoolean(ProviderFieldName.ACTIVE.getFieldName());

        value = rawJsonObject.optString(ProviderFieldName.CREATED_AT.getFieldName());
        if (!StringUtils.isBlank(value)) {
            try {
                this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        value = rawJsonObject.optString(ProviderFieldName.UPDATED_AT.getFieldName());
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
            jsonObject.put(ProviderFieldName.ID.getFieldName(), this.id.toString());
            jsonObject.put(ProviderFieldName.PROVIDER_NAME.getFieldName(), this.providerName);
            jsonObject.put(ProviderFieldName.ADDRESS.getFieldName(), this.address);
            jsonObject.put(ProviderFieldName.PASSWORD.getFieldName(), this.password.toString());
            jsonObject.put(ProviderFieldName.CREATED_AT.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdAt));
            jsonObject.put(ProviderFieldName.UPDATED_AT.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdAt));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public Provider() {
        this.id = new UUID(0, 0);
        this.password = "";
        this.providerName = "";
        this.address = "";
        this.ProviderId = -1;
        this.active = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Provider(ProviderTransition ProviderTransition) {
        this.id = ProviderTransition.getId();
        this.password = ProviderTransition.getPassword();
        this.providerName = ProviderTransition.getProviderName();
        this.address = ProviderTransition.getAddress();
        this.ProviderId = ProviderTransition.getProviderId();
        this.active = ProviderTransition.isActive();
        this.createdAt = ProviderTransition.getCreatedAt();
        this.updatedAt = ProviderTransition.getUpdatedAt();
    }
}
