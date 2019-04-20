package edu.uark.lawncareservicesapp;

import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.uark.lawncareservicesapp.adapters.ProductCountListAdapter;
import edu.uark.lawncareservicesapp.models.api.services.ProductService;
import edu.uark.lawncareservicesapp.models.api.services.TransactionService;
import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.Product;
import edu.uark.lawncareservicesapp.models.api.ProductCount;
import edu.uark.lawncareservicesapp.models.api.Transaction;
import edu.uark.lawncareservicesapp.models.api.enums.TransactionType;

public class TransactionAcitivty extends AppCompatActivity {

    private GetProductsTask getProductsTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_acitivty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        that = this;

        getProductsTask = new GetProductsTask();
        getProductsTask.execute();

        this.productCountList = new ArrayList<>();

        this.productCountListAdapter = new ProductCountListAdapter(this, this.productCountList);
        this.getProductsListView().setAdapter(this.productCountListAdapter);

        searchField = findViewById(R.id.service_search_field);
        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                that.productCountListAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                that.productCountListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.list_view_providers);
    }

    public void createTransaction(View view) {
        int total = 0;
        for (Iterator<ProductCount> i = this.productCountList.iterator(); i.hasNext();) {
            ProductCount productCount = i.next();
            total += productCount.getQuantity() * productCount.getProduct().getPrice();
        }
        Client e = ApplicationState.getClient();
        Transaction t =(new Transaction()
                .setProducts(this.productCountList)
                .setClientId(e.getId())
                .setAmount(total)
                .setType(TransactionType.SALE));

        new AlertDialog.Builder(that).
                setMessage("Total price: ¢" + t.getAmount()).
                setPositiveButton(
                        R.string.transaction_confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                createTransactionTask = new CreateTransactionTask();
                                createTransactionTask.execute(t);
                            }
                        }
                ).
                setNegativeButton(
                        R.string.transaction_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                ).
                create().
                show();
    }

    public class CreateTransactionTask extends AsyncTask<Transaction, Void, ApiResponse<Transaction>> {
        @Override protected void onPreExecute() {
            this.loadingAlert.show();
        }

        @Override
        protected ApiResponse<Transaction> doInBackground(Transaction... params) {
            ApiResponse<Transaction> apiResponse = (new TransactionService()).createTransaction(params[0]);
            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Transaction> apiResponse) {
            this.loadingAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(that).
                        setMessage(R.string.transaction_error).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            } else {
                new AlertDialog.Builder(that).
                        setMessage("New transaction created!\n\nReceipt ID: " + apiResponse.getData().getRecieptId()).
                        setPositiveButton(
                                R.string.transaction_done,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }
                        ).
                        create().
                        show();
            }

            createTransactionTask = null;
        }

        private AlertDialog loadingAlert;

        private CreateTransactionTask() {
            this.loadingAlert = new AlertDialog.Builder(that).
                    setMessage(R.string.transaction_loading).
                    create();
        }
    }

    public class GetProductsTask extends AsyncTask<Void, Void, ApiResponse<List<Product>>> {
        @Override
        protected ApiResponse<List<Product>> doInBackground(Void... params) {
            ApiResponse<List<Product>> apiResponse = (new ProductService()).getProducts();
            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<List<Product>> apiResponse) {
            this.loadingAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(that).
                        setMessage(R.string.get_products_alert).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }
                        ).
                        create().
                        show();
            } else {
                List<Product> products = apiResponse.getData();
                Iterator<Product> productsIterator = products.iterator();
                while (productsIterator.hasNext()) {
                    that.productCountList.add(new ProductCount(productsIterator.next()));
                }
                that.productCountListAdapter.notifyDataSetChanged();
            }
            that.getProductsTask = null;
        }

        private GetProductsTask() {
            this.loadingAlert = new AlertDialog.Builder(that).
                    setMessage(R.string.loading_products).
                    create();
        }


        private AlertDialog loadingAlert;
    }

    private TransactionAcitivty that;
    private SearchView searchField;
    private List<ProductCount> productCountList;
    private ProductCountListAdapter productCountListAdapter;
    private CreateTransactionTask createTransactionTask = null;

}
