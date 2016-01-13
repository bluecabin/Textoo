# Textoo
Textoo is a library providing simple Fluent API for configuring text objects including:
- TextView
- Spanned
- String

With Textoo, developers can

- capture click events of links in text and perform any custom processing.
- turn text into clickable links.
Textoo wraps <a href="http://developer.android.com/reference/android/text/util/Linkify.html">android.text.util.Linkify</a>
and expose the same functions with a simple and consistent fluent style API.
- parse html source.
Textoo wraps <a href="http://developer.android.com/reference/android/text/Html.html">android.text.Html</a>
and expose the same functions with a simple and consistent fluent style API.

## Usage examples

### Link TextView to system settings

- res/values/strings.xml:
    ```
    <resources>
    ...
         <string name="str_location_disabled">Location is off.  Turn on in <a href="internal://settings/location">Location settings</a>.</string>
    ...
     </resources>
    ```

- res/layout/myActivity.xml:
    ```
    ...
        <TextView
            android:id="@+id/view_location_disabled"
            android:text="@string/str_location_disabled"
            />
    ...
    ```
- java/myPackage/MyActivity.java:
    ```
    public class MyActivity extends Activity {
        ...
        protected void onCreate(Bundle savedInstanceState) {
            ...
            TextView locNotFound = Textoo
                .config((TextView) findViewById(R.id.view_location_disabled))
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        if ("internal://settings/location".equals(url)) {
                            Intent locSettings = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(locSettings);
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .apply();
            ...
        }
        ...
    }

    ```

### Linkify TextView

- java/myPackage/MyActivity.java:
    ```
    public class MyActivity extends Activity {
        ...
        private static Linkify.MatchFilter matchFilter = new Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence cs, int start, int end) {
                return start > 162;
            }
        };
        ...
        private static Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url + "&whatever=fool";
            }
        };
        ...
        protected void onCreate(Bundle savedInstanceState) {
            ...
            TextView myTextView = Textoo
                .config((TextView) findViewById(R.id.view_location_disabled))
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .linkify(pattern, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern, "http://www.google.ie/search2?q=", null, transformFilter)
                .linkify(pattern, "http://www.google.ie/search3?q=", matchFilter, transformFilter)
                .apply();
            ...
        }
        ...
    }

    ```

### Parse HTML and handle links

- java/myPackage/MyActivity.java:
    ```
    public class MyActivity extends Activity {
        ...
        protected void onCreate(Bundle savedInstanceState) {
            ...
            Spanned linksLoggingText = Textoo
                .config("Links: <a href='http://www.google.com'>Google</a>")
                .parseHtml()
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        Log.i("MyActivity", "Linking to google...");
                        return false; // event not handled.  Continue default processing i.e. link to google
                    }
                })
                .apply();
            ...
        }
        ...
    }

    ```

