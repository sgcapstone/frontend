package edu.uark.lawncareservicesapp.models.api;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uark.lawncareservicesapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.lawncareservicesapp.models.api.fields.ProductCountFieldName;

/**
 * Created by jaredramirez on 4/15/18.
 */

public class ProductCount implements ConvertToJsonInterface {
    private int quantity;
    public int getQuantity() {
        return this.quantity;
    }
    public ProductCount setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    private Product product;
    public Product getProduct() {
        return this.product;
    }
    public ProductCount setProduct(Product p) {
        this.product = p;
        return this;
    }

    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(ProductCountFieldName.PRODUCT_ID.getFieldName(), this.product.getId());
            jsonObject.put(ProductCountFieldName.QUANTITY.getFieldName(), this.quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public ProductCount(Product p) {
        product = p;
        quantity = 0;
    }

    public ProductCount(int q, Product p) {
        product = p;
        quantity = q;
    }
}
