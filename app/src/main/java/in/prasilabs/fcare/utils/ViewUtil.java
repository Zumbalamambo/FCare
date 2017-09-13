package in.prasilabs.fcare.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import in.prasilabs.fcare.R;
import in.prasilabs.fcare.custom.CharacterDrawable;
import in.prasilabs.fcare.custom.CircleTransform;

/**
 * Created by Contus team on 30/8/17.
 */

public class ViewUtil {

    private static final String TAG = ViewUtil.class.getSimpleName();

    public static void t(Context context, String message) {
        if(!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void hideProgressView(ViewGroup viewGroup)
    {
        showProgressView(viewGroup, false, false);
    }

    public static void showProgressView(ViewGroup viewGroup, boolean isBig)
    {
        showProgressView(viewGroup, isBig, true);
    }

    private static void showProgressView(ViewGroup viewGroup, boolean isBig, boolean isShow)
    {
        if(isShow)
        {
            View view = View.inflate(viewGroup.getContext(), R.layout.widget_progress, null);

            ProgressBar bigProgressBar = (ProgressBar) view.findViewById(R.id.big_progress_bar);
            ProgressBar smallProgressBar = (ProgressBar) view.findViewById(R.id.small_progress_bar);

            if(isBig)
            {
                bigProgressBar.setVisibility(View.VISIBLE);
                smallProgressBar.setVisibility(View.GONE);
            }
            else
            {
                bigProgressBar.setVisibility(View.GONE);
                smallProgressBar.setVisibility(View.VISIBLE);
            }


            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                viewGroup.getChildAt(i).setVisibility(View.GONE);
            }
            viewGroup.addView(view);
        }
        else
        {
            if(viewGroup.getChildCount() > 0)
            {
                if(viewGroup.getChildCount() > 1)
                {
                    for(int i= 0; i<viewGroup.getChildCount() -1; i++)
                    {
                        viewGroup.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }

                viewGroup.removeView(viewGroup.getChildAt(viewGroup.getChildCount() - 1));
            }
        }
    }

    public static void renderImage(ImageView view, Uri uri)
    {
        if(uri != null)
        {
            try
            {
                Picasso.with(view.getContext()).load(uri).into(view);
            }
            catch (Exception e)
            {
                ConsoleLog.e(e);
            }
        }
    }

    public static void renderImage(ImageView view, String url, boolean isCircle, String fallBackText)
    {
        if(!TextUtils.isEmpty(url))
        {
            ConsoleLog.i(TAG, "picture url is : " + url);
            try
            {
                Drawable fallBackDrawable = null;
                if(!TextUtils.isEmpty(fallBackText))
                {
                    fallBackDrawable = new CharacterDrawable(fallBackText, CharacterDrawable.SHAPE_CIRCLE);
                }

                RequestCreator requestCreator = Picasso.with(view.getContext()).load(url.trim());

                if(fallBackDrawable != null) {
                    requestCreator.placeholder(fallBackDrawable);
                }
                if(isCircle)
                {
                    requestCreator.transform(new CircleTransform());
                }
                requestCreator.into(view);
            }
            catch (Exception e)
            {
                ConsoleLog.e(e);
            }
        } else {
            if(!TextUtils.isEmpty(fallBackText))
            {
                Drawable fallBackDrawable = new CharacterDrawable(fallBackText, CharacterDrawable.SHAPE_CIRCLE);
                view.setImageDrawable(fallBackDrawable);
            }
        }
    }

    public static void renderImage(ImageView view, int resource, boolean isCircle)
    {
        try
        {
            RequestCreator requestCreator = Picasso.with(view.getContext()).load(resource);
            if(isCircle)
            {
                requestCreator.transform(new CircleTransform());
            }
            requestCreator.into(view);
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }

    public static String toTitleCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        } else {
            return "";
        }
    }

    public static String toUpperCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.toUpperCase();
        } else {
            return "";
        }
    }

    public static String toLowerCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.toLowerCase();
        } else {
            return "";
        }
    }

    public static String toCamelCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            String[] words = text.split(" ");
            StringBuilder camelText = new StringBuilder();
            for (String word : words) {
                camelText.append(toTitleCase(word));
                camelText.append(" ");
            }
            return camelText.toString().trim();
        } else {
            return "";
        }
    }

    public static String getRelativeTime(long timeInMillis)
    {
        CharSequence relativeTimeSpanString = null;
        try {
            // Time comes either as millisecond values from search or string from mysql
            timeInMillis = Long.valueOf(timeInMillis);
        }
        catch (NumberFormatException ne)
        {
            return "";
        }
        relativeTimeSpanString = DateUtils.getRelativeTimeSpanString(timeInMillis, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME);
        if (relativeTimeSpanString != null) {
            return relativeTimeSpanString.toString();
        } else {
            return "";
        }
    }
}
