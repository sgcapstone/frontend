package edu.uark.lawncareservicesapp;

import java.util.ArrayList;
import java.util.List;

import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.Transaction;

class ApplicationState {
    static Client getClient() {
        return client;
    }
    static void setClient(Client e) {
        client = e;
    }

    static ArrayList<Client> getProviderList() { return providerList; }

    static void setProviderList(List<Client> e) {
        providerList = new ArrayList<Client>();
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
    private static ArrayList<Client> providerList;
    private static boolean isAuthenticated;
    private static Transaction transaction;
}
