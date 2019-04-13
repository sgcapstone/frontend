package edu.uark.lawncareservicesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uark.lawncareservicesapp.R;
import edu.uark.lawncareservicesapp.models.api.Product;
import edu.uark.lawncareservicesapp.models.api.ProductCount;

public class ProductCountListAdapter extends ArrayAdapter<ProductCount> implements Filterable {

    public ProductCountListAdapter (Context context, List<ProductCount> masterProductCounts) {
        super(context, R.layout.list_view_item_product, masterProductCounts);
        this.productCounts = masterProductCounts;
        this.masterProductCounts = masterProductCounts;
        this.that = this;
    }

    @Override
    public int getCount() {
        return productCounts.size();
    }

    @Override
    public ProductCount getItem(int i) {
        return productCounts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull final ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			view = inflater.inflate(R.layout.list_view_item_product, parent, false);
		}

		ProductCount productCount = this.getItem(position);
		if (productCount != null) {

            final Product product = productCount.getProduct();
            if (product != null) {
                TextView lookupCodeTextView = view.findViewById(R.id.list_view_item_product_lookup_code);
                if (lookupCodeTextView != null) {
                    lookupCodeTextView.setText(product.getLookupCode());
                }

                TextView priceCodeTextView = view.findViewById(R.id.list_view_item_product_price);
                if (priceCodeTextView != null) {
                    priceCodeTextView.setText(String.valueOf(product.getPrice()));
                }

                Button incrementButton = view.findViewById(R.id.list_view_increment_button);
                if (incrementButton != null) {
                    incrementButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int quantity = productCount.getQuantity();
                            int count = product.getCount();
                            if (quantity <= count) {
                                productCount.setQuantity(productCount.getQuantity() + 1);
                                that.notifyDataSetChanged();
                            }
                        }
                    });
                }

                Button decrementButton = view.findViewById(R.id.list_view_decrement_button);
                if (decrementButton != null) {
                    decrementButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int quantity = productCount.getQuantity();
                            if (quantity != 0) {
                                productCount.setQuantity(productCount.getQuantity() - 1);
                                that.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }

            TextView quantityTextView = view.findViewById(R.id.list_view_item_quantity);
            if (quantityTextView != null) {
                quantityTextView.setText(String.valueOf(productCount.getQuantity()));
            }

        }

		return view;
	}

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                that.productCounts = (List<ProductCount>) results.values;
                that.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<ProductCount> filteredItems = new ArrayList<ProductCount>();

                Product curProduct;
                constraint = constraint.toString().toLowerCase();
                for (ProductCount productCount : that.masterProductCounts) {
                    curProduct = productCount.getProduct();
                    if (curProduct.getLookupCode().toLowerCase().contains(constraint)) {
                        filteredItems.add(productCount);
                    }
                }


                results.count = filteredItems.size();
                results.values = filteredItems;
                return results;
            }
        };

        return filter;
    }

    private ProductCountListAdapter that;
    private List<ProductCount> masterProductCounts;
    private List<ProductCount> productCounts;
}
