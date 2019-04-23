package edu.uark.lawncareservicesapp;

import java.util.ArrayList;
import java.util.List;

import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.Provider;
import edu.uark.lawncareservicesapp.models.api.Transaction;

class ApplicationState {
    static Client getClient() {
        return client;
    }
    static void setClient(Client e) {
        client = e;
    }

    static Provider getProvider() { return provider; }
    static void setProvider(Provider e) { provider = e; }

    static ArrayList<Provider> getProviderList() { return providerList; }
    static void setProviderList(List<Provider> e) {
        providerList = new ArrayList<Provider>();
        providerList.addAll(e);
        System.out.println(providerList);
    }

    static boolean isAuthenticated() {
        return isAuthenticated;
    }
    static void setIsAuthenticated(boolean a) {
        isAuthenticated = a;
    }

    static Transaction getTransaction() {
        return transaction;
    }
    static void setTransaction(Transaction t) {
        transaction = t;
    }

    private static Client client;
    private static Provider provider;
    private static ArrayList<Provider> providerList;
    private static boolean isAuthenticated;
    private static Transaction transaction;
}
