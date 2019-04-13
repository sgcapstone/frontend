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
import edu.uark.lawncareservicesapp.models.transition.ClientTransition;
import edu.uark.lawncareservicesapp.models.api.enums.ClientRole;
import edu.uark.lawncareservicesapp.models.api.fields.ClientFieldName;


public class Client implements ConvertToJsonInterface, LoadFromJsonInterface<Client> {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public Client setId(UUID id) {
        this.id = id;
        return this;
    }

    private String password;
    public String getPassword() { return this.password; }
    public Client setPassword(String password) {
        this.password = password;
        return this;
    }

    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public Client setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public Client setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    private int clientId;
    public int getClientId() {
        return clientId;
    }
    public Client setClientId(int clientId) {
        this.clientId = clientId;
        return this;
    }

    private boolean active;
    public boolean isActive() {
        return active;
    }
    public Client setActive(boolean active) {
        this.active = active;
        return this;
    }

    private ClientRole role;
    public ClientRole getRole() {
        return role;
    }
    public Client setRole(ClientRole role) {
        this.role = role;
        return this;
    }

    private String managerId;
    public String getManagerId() {
        return managerId;
    }
    public Client setManagerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }
    public Client setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public Client setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public static JSONObject convertLogInToJson(String clientId, String password){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(ClientFieldName.CLIENT_ID.getFieldName(), clientId);
            jsonObject.put(ClientFieldName.PASSWORD.getFieldName(), password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public Client loadFromJson(JSONObject rawJsonObject) {
        String value = rawJsonObject.optString(ClientFieldName.ID.getFieldName());
        if (!StringUtils.isBlank(value)) {
            this.id = UUID.fromString(value);
        }
        this.password = rawJsonObject.optString(ClientFieldName.PASSWORD.getFieldName());
        this.firstName = rawJsonObject.optString(ClientFieldName.FIRST_NAME.getFieldName());
        this.lastName = rawJsonObject.optString(ClientFieldName.LAST_NAME.getFieldName());
        this.clientId = rawJsonObject.optInt(ClientFieldName.CLIENT_ID.getFieldName());
        this.active = rawJsonObject.optBoolean(ClientFieldName.ACTIVE.getFieldName());

        this.role = ClientRole.fromString(rawJsonObject.optString(ClientFieldName.ROLE.getFieldName()));
        if (this.role == null) {
            throw new Error("Invalid client role.");
        }

        this.managerId = rawJsonObject.optString(ClientFieldName.MANGER_ID.getFieldName());

        value = rawJsonObject.optString(ClientFieldName.CREATED_AT.getFieldName());
        if (!StringUtils.isBlank(value)) {
            try {
                this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        value = rawJsonObject.optString(ClientFieldName.UPDATED_AT.getFieldName());
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
            jsonObject.put(ClientFieldName.ID.getFieldName(), this.id.toString());
            jsonObject.put(ClientFieldName.PASSWORD.getFieldName(), this.password.toString());
            jsonObject.put(ClientFieldName.FIRST_NAME.getFieldName(), this.firstName);
            jsonObject.put(ClientFieldName.LAST_NAME.getFieldName(), this.lastName);
            jsonObject.put(ClientFieldName.CLIENT_ID.getFieldName(), this.clientId);
            jsonObject.put(ClientFieldName.ACTIVE.getFieldName(), this.active);
            jsonObject.put(ClientFieldName.ROLE.getFieldName(), this.role.getValue());
            jsonObject.put(ClientFieldName.MANGER_ID.getFieldName(), this.managerId);
            jsonObject.put(ClientFieldName.CREATED_AT.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdAt));
            jsonObject.put(ClientFieldName.UPDATED_AT.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdAt));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public Client() {
        this.id = new UUID(0, 0);
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.clientId = -1;
        this.active = false;
        this.role = ClientRole.CONSUMER;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Client(ClientTransition clientTransition) {
        this.id = clientTransition.getId();
        this.password = clientTransition.getPassword();
        this.firstName = clientTransition.getFirstName();
        this.lastName = clientTransition.getLastName();
        this.clientId = clientTransition.getClientId();
        this.active = clientTransition.isActive();
        this.role = clientTransition.getRole();
        this.createdAt = clientTransition.getCreatedAt();
        this.updatedAt = clientTransition.getUpdatedAt();
    }
}
