package in.prasilabs.fcare.base;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by bala on 30/9/15.
 */
public class BaseTextView extends android.support.v7.widget.AppCompatTextView
{
    protected String fontName = "fonts/Roboto-Regular.ttf";
    private CharSequence origText = "";

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        setTypeface(typeface);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        origText = text;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onPreDraw();
    }
}

