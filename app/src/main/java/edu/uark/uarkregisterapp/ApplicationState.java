package edu.uark.uarkregisterapp;

import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.Transaction;

class ApplicationState {
    static Employee getEmployee() {
        return employee;
    }
    static void setEmployee(Employee e) {
        employee = e;
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

    private static Employee employee;
    private static boolean isAuthenticated;
    private static Transaction transaction;
}
