package com.prasilabs.fcare.backend.security;

import com.prasilabs.fcare.backend.services.apiRequest.ApiFetcher;
import com.prasilabs.fcare.backend.services.apiRequest.ApiRequestType;
import com.prasilabs.fcare.backend.services.apiRequest.ApiResult;
import com.prasilabs.fcare.backend.services.utils.JsonUtil;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

/**
 * Created by Contus team on 30/8/17.
 */

public class FaceBookOTPVerifier {

    private static final String FACEBOOK_AUTH_URL = "https://graph.accountkit.com/v1.2/me/";

    public static String verifyOTP(String accessToken) {

        String url = FACEBOOK_AUTH_URL + "?access_token="+accessToken;

        ApiResult apiResult = ApiFetcher.makeStringRequest(url, ApiRequestType.GET);

        if (apiResult.isSuccess()) {
            String responseData = apiResult.getResult();

            FacebookOtpResponse facebookOtpResponse = JsonUtil.getObjectFromJson(responseData, FacebookOtpResponse.class);

            if(facebookOtpResponse != null && facebookOtpResponse.getPhone() != null) {
                return facebookOtpResponse.getPhone().getNumber();
            }
        }

        return null;
    }

    private static class FacebookOtpResponse {

        private String id;

        private Phone phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        /**
         * Default constructor for gson parsing.
         */
        public FacebookOtpResponse() {

        }
    }

    private static class Phone {

        /**
         * Default constructor for gson parsing.
         */
        public Phone() {}

        private String number;

        @SerializedName("country_prefix")
        private String countryPrefix;

        @SerializedName("national_number")
        private String nationalNumber;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCountryPrefix() {
            return countryPrefix;
        }

        public void setCountryPrefix(String countryPrefix) {
            this.countryPrefix = countryPrefix;
        }

        public String getNationalNumber() {
            return nationalNumber;
        }

        public void setNationalNumber(String nationalNumber) {
            this.nationalNumber = nationalNumber;
        }
    }

}
