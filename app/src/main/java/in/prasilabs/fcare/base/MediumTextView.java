package in.prasilabs.fcare.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by prasi on 6/10/16.
 */

public class MediumTextView extends BaseTextView
{
    private String fontName = "fonts/Roboto-Medium.ttf";

    public MediumTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        setTypeface(typeface);
    }
}
