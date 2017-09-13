package in.prasilabs.fcare.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

/**
 * Created by prasi on 25/2/16.
 */
public class FontAweSomeTextView extends android.support.v7.widget.AppCompatTextView
{
    private static final String TAG = FontAweSomeTextView.class.getSimpleName();
    private Typeface font;

    public FontAweSomeTextView(Context context) {
        super(context);
        setFont(context);
    }

    public FontAweSomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public FontAweSomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        // prevent exception in Android Studio / ADT interface builder
        if (this.isInEditMode()) {
            return;
        }

        //Check for font is already loaded
        if(font == null)
        {
            try {
                font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
                ConsoleLog.i(TAG, "Font awesome loaded");
            } catch (RuntimeException e) {
                ConsoleLog.i(TAG, "Font awesome not loaded");
            }
        }

        //Finally set the font
        setTypeface(font);
    }
}

