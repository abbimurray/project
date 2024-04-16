package controller;

import dao.PaymentMethodDao;
import model.PaymentMethod;

import java.util.List;

public class PaymentMethodController {
    private PaymentMethodDao paymentMethodDao;

    public PaymentMethodController() {
        this.paymentMethodDao = new PaymentMethodDao();
    }

    public boolean addPaymentMethod(PaymentMethod paymentMethod) {
        //add logic to check conditions before adding such as fields are all have correct input type and not empty
        return paymentMethodDao.addPaymentMethod(paymentMethod);
    }

    public List<PaymentMethod> getPaymentMethodsForCustomer(int customerID) {
        return paymentMethodDao.getPaymentMethodsByCustomerId(customerID);
    }
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) {
        // Implement logic to check conditions before update
        return paymentMethodDao.updatePaymentMethod(paymentMethod);
    }
    public boolean deletePaymentMethod(int paymentMethodId) {
        // Retrieve the customerID from the UserSession
        int customerID = UserSession.getInstance().getCustomerID();

        // Call the DAO method to delete the payment method for this customerID
        return paymentMethodDao.deletePaymentMethod(paymentMethodId, customerID);
    }


}//end class
