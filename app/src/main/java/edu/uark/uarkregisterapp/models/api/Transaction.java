package edu.uark.uarkregisterapp.models.api;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.enums.TransactionType;
import edu.uark.uarkregisterapp.models.api.fields.ProductFieldName;
import edu.uark.uarkregisterapp.models.api.fields.TransactionFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;

import static edu.uark.uarkregisterapp.models.api.enums.TransactionType.SALE;

/**
 * Created by jaredramirez on 4/15/18.
 */

public class Transaction implements ConvertToJsonInterface, LoadFromJsonInterface<Transaction> {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public Transaction setId(UUID id) {
        this.id = id;
        return this;
    }

    private UUID employeeId;
    public UUID getEmployeeId() {
        return this.employeeId;
    }
    public Transaction setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    private int amount;
    public int getAmount() {
        return this.amount;
    }
    public Transaction setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    private TransactionType type;
    public TransactionType getType() {
        return this.type;
    }
    public Transaction setType(TransactionType type) {
        this.type = type;
        return this;
    }

    private UUID refId;
    public UUID getRefId() {
        return this.refId;
    }
    public Transaction setRefId(UUID employeeId) {
        this.refId = employeeId;
        return this;
    }

    private int recieptId;
    public int getRecieptId() {
        return this.recieptId;
    }
    public Transaction setRecieptId(int recieptId) {
        this.recieptId = recieptId;
        return this;
    }

    private List<ProductCount> products;
    public List<ProductCount> getProducts() {
        return this.products;
    }
    public Transaction setProducts(List<ProductCount> products) {
        this.products = products;
        return this;
    }

    private Date createdAt;
    public Date getCreatedAt() {
        return this.createdAt;
    }
    public Transaction setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    private Date updatedAt;
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
    public Transaction setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }


    @Override
    public Transaction loadFromJson(JSONObject rawJsonObject) {
        String id = rawJsonObject.optString(TransactionFieldName.ID.getFieldName());
        if (!StringUtils.isBlank(id)) {
            this.id = UUID.fromString(id);
        }

        String employeeId = rawJsonObject.optString(TransactionFieldName.EMPLOYEE_ID.getFieldName());
        if (!StringUtils.isBlank(employeeId)) {
            this.employeeId = UUID.fromString(employeeId);
        }

        this.amount = rawJsonObject.optInt(TransactionFieldName.AMOUNT.getFieldName());
        this.type = TransactionType.fromBoolean(rawJsonObject.optBoolean(TransactionFieldName.TYPE.getFieldName()));

        String refId = rawJsonObject.optString(TransactionFieldName.REF_ID.getFieldName());
        if (!StringUtils.isBlank(refId) && refId != "null") {
            this.refId = UUID.fromString(refId);
        }

        this.recieptId = rawJsonObject.optInt(TransactionFieldName.RECIEPT_ID.getFieldName());

        String dateValue = rawJsonObject.optString(ProductFieldName.CREATED_AT.getFieldName());
        if (!StringUtils.isBlank(dateValue)) {
            try {
                this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(dateValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dateValue = rawJsonObject.optString(ProductFieldName.UPDATED_AT.getFieldName());
        if (!StringUtils.isBlank(dateValue)) {
            try {
                this.updatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(dateValue);
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
            jsonObject.put(TransactionFieldName.ID.getFieldName(), this.id.toString());
            if (this.employeeId != null) {
                jsonObject.put(TransactionFieldName.EMPLOYEE_ID.getFieldName(), this.employeeId.toString());
            }
            jsonObject.put(TransactionFieldName.AMOUNT.getFieldName(), this.amount);

            jsonObject.put(TransactionFieldName.TYPE.getFieldName(), TransactionType.toBoolean(this.type));

            if (this.refId != null) {
                jsonObject.put(TransactionFieldName.REF_ID.getFieldName(), this.refId.toString());
            }
            if (this.recieptId != -1) {
                jsonObject.put(TransactionFieldName.RECIEPT_ID.getFieldName(), this.recieptId);
            }

            JSONArray products = new JSONArray();
            for (ProductCount product: this.products) {
                products.put(product.convertToJson());
            }
            jsonObject.put(TransactionFieldName.PRODUCTS.getFieldName(), products);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public Transaction() {
        this.id = new UUID(0, 0);
        this.employeeId = null;
        this.amount = 0;
        this.type = SALE;
        this.refId = null;
        this.recieptId = -1;
        this.products = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
