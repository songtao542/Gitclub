package org.gitclub.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.gitclub.ApplicationComponent;
import org.gitclub.GitApplication;
import org.gitclub.GithubApi;
import org.gitclub.R;
import org.gitclub.model.AccessToken;
import org.gitclub.model.Scopes;
import org.gitclub.model.User;
import org.gitclub.provider.GitclubContent;
import org.gitclub.ui.module.DaggerLoginComponent;
import org.gitclub.ui.module.LoginModule;
import org.gitclub.utils.SLog;
import org.gitclub.utils.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginActivity.UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private WebView mWebView;
    private boolean mIsReady;
    private boolean mWaitLogin;
    private int mLoadCounter = 0;

    private String mCode;

    private String mEmail;
    private String mPassword;

    static final String LOGIN_MATCH_URL = "https://github.com/login?client_id=" + GithubApi.CLIENT_ID + "&return_to=%2Flogin%2Foauth%2Fauthorize%3Fclient_id%3D8c148bb9efb6382368f4%26";

    static final String AUTHORIZE_URL = GithubApi.AUTHORIZE_URL + Scopes.user + " " + Scopes.repo;

    @Inject
    LoginModule.LoginApi mLoginApi;

    ApplicationComponent mAppComponent;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_app_bar_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        mEmailView.setText("songtao542@gmail.com");
        mPasswordView.setText("wst472036045");


        ApplicationComponent appComponent = ((GitApplication) getActivity().getApplication()).getApplicationComponent();
        DaggerLoginComponent.builder().applicationComponent(appComponent).build().inject(this);

        mWebView = new WebView(getActivity());
        //mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setDomStorageEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        mWebView.addJavascriptInterface(new LoginActivity.InJavaScriptInterface(), "local");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mLoadCounter++;
                SLog.d("onPageStarted url:" + url);
                String prefix = "http://localhost:4567/callback?code=";
                if (url.startsWith(prefix)) {
                    mCode = url.substring(prefix.length());
                    SLog.d("onPageStarted mCode:" + mCode);
                    accessToken(mCode);
                } else if (view.getTitle().contains("Authorize GitClub")) {
                    needAuthorize();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                SLog.d("onPageFinished url:" + url);
                //view.loadUrl("javascript:window.local.showSource('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

                if (url.startsWith(LOGIN_MATCH_URL)) {
                    SLog.d("login page is ready");
                    mIsReady = true;
                    hiddenHeader();

                    if (mWaitLogin) {
                        mWaitLogin = false;
                        login(mEmail, mPassword);
                    }
                } else {
                    mIsReady = false;
                    if (view.getTitle().contains("Authorize GitClub")) {
                        needAuthorize();
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (mLoadCounter == 1) {
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                SLog.d("onReceivedError error:" + error);
                Uri uri = request != null ? request.getUrl() : null;
                String url = uri != null ? uri.toString() : "";
                String prefix = "http://localhost:4567/callback?code=";
                if (!url.startsWith(prefix)) {
                    showError();
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                SLog.d("onReceivedHttpError errorResponse:" + errorResponse);
                Uri uri = request != null ? request.getUrl() : null;
                String url = uri != null ? uri.toString() : "";
                String prefix = "http://localhost:4567/callback?code=";
                if (!url.startsWith(prefix)) {
                    showError();
                }
            }
        });

        mWebView.loadUrl(AUTHORIZE_URL);
    }

    final static class InJavaScriptInterface {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println(html);
        }
    }

    private void needAuthorize() {
        String js = "javascript:(function(){" +
                "var authorize = document.getElementsByName(\"authorize\"); " +
                "authorize[0].click();" +
                "console.log(\"点击授权\");" +
                "})()";
        mWebView.evaluateJavascript(js, null);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);

            SLog.d("start login ...");
            mEmail = email;
            mPassword = password;
            if (!mIsReady) {
                mWebView.loadUrl(AUTHORIZE_URL);
                mWaitLogin = true;
            } else {
                login(mEmail, mPassword);
            }

//            mEmailView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showProgress(false);
//                    loginSuccess();
//                }
//
//            }, 3000);
        }
    }

    private void showError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadCounter > 1) {
                    Toast.makeText(getActivity(), getString(R.string.error_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hiddenHeader() {
        String js = "javascript:(function(){" +
                "document.getElementsByClassName(\"nav-bar\")[0].style.visibility=\"hidden\";" +
                "console.log(\"隐藏标题栏\");" +
                "})()";
        mWebView.evaluateJavascript(js, null);
    }

    private void login(String email, String password) {
        String js = "javascript:(function(){" +
                "var username = document.getElementsByName(\"login\");" +
                "var password = document.getElementsByName(\"password\"); " +
                "var commit = document.getElementsByClassName(\"btn btn-block\"); " +
                "username[0].value = \"" + email + "\";" +
                "password[0].value= \"" + password + "\";" +
                "commit[0].click();" +
                "console.log(\"点击登录\");" +
                "})()";
        mWebView.evaluateJavascript(js, null);
    }

    private void accessToken(String code) {
        mLoginApi.rxAccessToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        showError();
                    }
                })
                .subscribe(new Consumer<AccessToken>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull final AccessToken accessToken) throws Exception {
                        if (accessToken != null) {
                            SLog.d("response accessToken:" + accessToken.accessToken);
                            SLog.d("response tokenType:" + accessToken.tokenType);
                            SLog.d("response scope:" + accessToken.scope);
                            mAppComponent = ((GitApplication) getActivity().getApplication()).initApplicationComponentWithApiToken(accessToken.accessToken);
                            GithubApi githubApi = mAppComponent.api();
                            githubApi.rxGetUser()
                                    .subscribeOn(Schedulers.io())
                                    .doOnError(new Consumer<Throwable>() {
                                        @Override
                                        public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                                            showError();
                                        }
                                    })
                                    .subscribe(new Consumer<User>() {
                                        @Override
                                        public void accept(@io.reactivex.annotations.NonNull User user) throws Exception {
                                            saveUserAccessToken(user, accessToken);
                                            showProgressOnMainThread(false);
                                            loginSuccess();
                                        }
                                    })
                            ;
                        } else {
                            SLog.d("response accessToken is null");
                        }
                    }
                });
    }


    private void saveUserAccessToken(User user, AccessToken accessToken) {
        SLog.d("saveUserAccessToken accessToken:" + accessToken + "\nuser:" + ToString.toString(user));
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();


        if (user == null) {
            user = new User();
        }
        if (user.email == null) {
            user.email = mEmail;
        }

        SharedPreferences sharedPreferences = mAppComponent.sharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", user.email);
        editor.commit();


        Uri.Builder builder = GitclubContent.User.CONTENT_URI.buildUpon();
        builder.appendEncodedPath("email")
                .appendEncodedPath(user.email);
        Uri uri = builder.build();
        SLog.d("query uri:" + uri);
        Cursor cursor = getActivity().getContentResolver().query(uri, new String[]{GitclubContent.UserColumns._ID}, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            ops.add(ContentProviderOperation.newInsert(GitclubContent.User.CONTENT_URI)
                    .withValues(user.toContentValues())
                    .build());

            ContentValues backReferences = new ContentValues();
            backReferences.put(GitclubContent.AccessTokenColumns.USER_KEY, 0);

            ops.add(ContentProviderOperation.newInsert(GitclubContent.AccessToken.CONTENT_URI)
                    .withValues(accessToken.toContentValues())
                    .withValueBackReferences(backReferences)
                    .build());
        } else {
            int idIndex = cursor.getColumnIndex(GitclubContent.UserColumns._ID);
            cursor.moveToNext();
            long id = cursor.getLong(idIndex);
            SLog.d("user _id=" + id);
            ops.add(ContentProviderOperation.newUpdate(GitclubContent.User.CONTENT_URI)
                    .withSelection(GitclubContent.UserColumns._ID + "=?", new String[]{String.valueOf(id)})
                    .withValues(user.toContentValues())
                    .build());

            ContentValues backReferences = new ContentValues();
            backReferences.put(GitclubContent.AccessTokenColumns.USER_KEY, 0);

            ops.add(ContentProviderOperation.newUpdate(GitclubContent.AccessToken.CONTENT_URI)
                    .withValues(accessToken.toContentValues())
                    .withSelection(GitclubContent.AccessTokenColumns.USER_KEY + "=?", new String[]{String.valueOf(id)})
                    .withValueBackReferences(backReferences)
                    .build());
        }


        try {
            getActivity().getContentResolver().applyBatch(GitclubContent.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    Uri.Builder builder = new Uri.Builder();
                    builder.authority("org.gitclub.fragment")
                            .appendEncodedPath("login")
                            .appendEncodedPath(mEmail);
                    mListener.onFragmentInteraction(builder.build());
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgressOnMainThread(final boolean show) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgress(show);
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), LoginFragment.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(LoginFragment.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
