/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.utils;

/**
 *
 * @author MSI
 */
public class MyAppConstants {
    
    // Role
    public static final int ROLE_ADMIN = 0;
    
    public class LoginFeature {
        public static final String INVALID_PAGE = "errorLoginPage";
        public static final String LOGIN_ACTION = "loginAction";
        public static final String LOGIN_PAGE = "loginPage";
        public static final String SEARCH_HOTEL_ACTION = "searchAction";
    }
    
    public class SearchHotelFeature {
        public static final String SEARCH_HOTEL_ACTION = "searchAction";
        public static final String HOTEL_PAGE = "searchHotelPage";
    } 
    
    public class RegisterAccountFeature {
        public static final String REGISTER_ACTION = "registerAction";
        public static final String REGISTER_PAGE = "registerPage";
    } 
    
    public class ViewBookingFeature {
        public static final String BOOKING_PAGE = "viewBookingPage";
    } 
    
    public class UpdateBookingFeature {
        public static final String UPDATE_BOOKING_ACTION = "updateBookingAction";
        public static final String BOOKING_PAGE = "viewBookingPage";
    } 
    
    public class DeleteBookingFeature {
        public static final String DELETE_BOOKING_ACTION = "deleteBookingAction";
        public static final String BOOKING_PAGE = "viewBookingPage";
    }
    
    public class CheckoutBookingFeature {
        public static final String CHECKOUT_BOOKING_ACTION = "checkoutBookingAction";
        public static final String CHECKINGOUT_PAGE = "checkoutBookingPage";
        public static final String CONFIRM_PAGE = "confirmBookingPage";
    }
    
    public class ConfirmBookingFeature {
        public static final String CONFIRM_BOOKING_ACTION = "confirmAction";
        public static final String CONFIRM_PAGE = "confirmBookingPage";
    }
    
    public class ResendVerifyCodeFeature {
        public static final String RESEND_VERIFY_CODE_ACTION = "resendVerifyCodeAction";
        public static final String CONFIRM_PAGE = "confirmBookingPage";
    }
    
    public class ViewBookingHistoryFeature {
        public static final String VIEW_BOOKING_HISTORY_ACTION = "viewBookingHistoryAction";
        public static final String BOOKING_HISTORY_PAGE = "viewBookingHistoryPage";
    }
    
    public class DeleteBookingHistoryFeature {
        public static final String DELETE_BOOKING_HISTORY_ACTION = "deleteBookingHistoryAction";
        public static final String VIEW_BOOKING_HISTORY_ACTION = "viewBookingHistoryAction";
        public static final String BOOKING_HISTORY_PAGE = "viewBookingHistoryPage";
    }
    
    public class FeedbackBookingHistoryFeature {
        public static final String FEEDBACK_BOOKING_HISTORY_ACTION = "feedbackBookingHistoryAction";
        public static final String VIEW_BOOKING_HISTORY_ACTION = "viewBookingHistoryAction";
        public static final String BOOKING_HISTORY_PAGE = "viewBookingHistoryPage";
    }
    
}
