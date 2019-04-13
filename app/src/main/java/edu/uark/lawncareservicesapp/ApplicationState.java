package edu.uark.lawncareservicesapp;

import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.Transaction;

class ApplicationState {
    static Client getClient() {
        return client;
    }
    static void setClient(Client e) {
        client = e;
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
    private static boolean isAuthenticated;
    private static Transaction transaction;
}
