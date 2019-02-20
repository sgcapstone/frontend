package edu.uark.uarkregisterapp.models.api.enums;

import edu.uark.uarkregisterapp.models.api.Transaction;

/**
 * Created by jaredramirez on 4/15/18.
 */

public enum TransactionType {
    SALE,
    RETURN;

    public static TransactionType fromBoolean(boolean b) {
        if (b) {
            return RETURN;
        }
        return SALE;
    }

    public static boolean toBoolean(TransactionType type) {
        switch (type) {
            case SALE:
                return false;

            case RETURN:
                return true;

            default:
                return true;
        }
    }
}
