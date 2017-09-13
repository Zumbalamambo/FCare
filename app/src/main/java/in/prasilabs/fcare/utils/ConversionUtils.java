package in.prasilabs.fcare.utils;


import android.telephony.PhoneNumberUtils;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

/**
 * Created by prasi on 10/9/17.
 *
 * @author Prasi <praslnx8@gmail.com>
 * @version 1.0
 */

public class ConversionUtils {

    public static double convertToCelcius(double fahrenhiet) {
        return  (fahrenhiet-32)*(0.5556);
    }
}
