package edu.uark.lawncareservicesapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.lawncareservicesapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.lawncareservicesapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.lawncareservicesapp.models.api.Product;

public class ProductTransition implements Parcelable {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public ProductTransition setId(UUID id) {
		this.id = id;
		return this;
	}

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public ProductTransition setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int count;
	public int getCount() {
		return this.count;
	}
	public ProductTransition setCount(int count) {
		this.count = count;
		return this;
	}

	private Date createdAt;
	public Date getCreatedAt() {
		return this.createdAt;
	}
	public ProductTransition setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	private Date updatedAt;
	public Date getUpdateAt() {
		return this.updatedAt;
	}
	public ProductTransition setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeString(this.lookupCode);
		destination.writeInt(this.count);
		destination.writeLong(this.createdAt.getTime());
		destination.writeLong(this.updatedAt.getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<ProductTransition> CREATOR = new Parcelable.Creator<ProductTransition>() {
		public ProductTransition createFromParcel(Parcel productTransitionParcel) {
			return new ProductTransition(productTransitionParcel);
		}

		public ProductTransition[] newArray(int size) {
			return new ProductTransition[size];
		}
	};

	public ProductTransition() {
		this.count = -1;
		this.id = new UUID(0, 0);
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.lookupCode = StringUtils.EMPTY;
	}

	public ProductTransition(Product product) {
		this.id = product.getId();
		this.count = product.getCount();
		this.createdAt = product.getCreatedAt();
		this.updatedAt = product.getUpdatedAt();
		this.lookupCode = product.getLookupCode();
	}

	private ProductTransition(Parcel productTransitionParcel) {
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(productTransitionParcel.createByteArray()).execute();
		this.lookupCode = productTransitionParcel.readString();
		this.count = productTransitionParcel.readInt();

		this.createdAt = new Date();
		this.createdAt.setTime(productTransitionParcel.readLong());

		this.updatedAt = new Date();
		this.updatedAt.setTime(productTransitionParcel.readLong());
	}
}
