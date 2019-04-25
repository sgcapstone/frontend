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

<<<<<<< HEAD
    static ArrayList<Client> getProviderList() { return providerList; }

    static void setProviderList(List<Client> e) {
        providerList = new ArrayList<Client>();
        providerList.addAll(e);
        System.out.println(providerList);

    }


=======
    static Provider getProvider() { return provider; }
    static void setProvider(Provider e) { provider = e; }

    static ArrayList<Provider> getProviderList() { return providerList; }
    static void setProviderList(List<Provider> e) {
        providerList = new ArrayList<Provider>();
        providerList.addAll(e);
        System.out.println(providerList);
    }

>>>>>>> master
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
<<<<<<< HEAD
    private static ArrayList<Client> providerList;
=======
    private static Provider provider;
    private static ArrayList<Provider> providerList;
>>>>>>> master
    private static boolean isAuthenticated;
    private static Transaction transaction;
}
