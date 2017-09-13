package in.prasilabs.fcare.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by prasi on 6/10/16.
 */

public class ThinTextView extends BaseTextView
{
    private String fontName = "fonts/Roboto-Thin.ttf";

    public ThinTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        setTypeface(typeface);
    }
}
