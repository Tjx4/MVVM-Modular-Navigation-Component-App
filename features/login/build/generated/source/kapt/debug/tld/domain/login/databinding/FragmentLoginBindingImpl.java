package tld.domain.login.databinding;
import tld.domain.login.R;
import tld.domain.login.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentLoginBindingImpl extends FragmentLoginBinding implements tld.domain.login.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etPasswordandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of loginViewModel.password.getValue()
            //         is loginViewModel.password.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etPassword);
            // localize variables for thread safety
            // loginViewModel != null
            boolean loginViewModelJavaLangObjectNull = false;
            // loginViewModel.password
            androidx.lifecycle.MutableLiveData<java.lang.String> loginViewModelPassword = null;
            // loginViewModel.password.getValue()
            java.lang.String loginViewModelPasswordGetValue = null;
            // loginViewModel
            tld.domain.login.LoginViewModel loginViewModel = mLoginViewModel;
            // loginViewModel.password != null
            boolean loginViewModelPasswordJavaLangObjectNull = false;



            loginViewModelJavaLangObjectNull = (loginViewModel) != (null);
            if (loginViewModelJavaLangObjectNull) {


                loginViewModelPassword = loginViewModel.getPassword();

                loginViewModelPasswordJavaLangObjectNull = (loginViewModelPassword) != (null);
                if (loginViewModelPasswordJavaLangObjectNull) {




                    loginViewModelPassword.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etUsernameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of loginViewModel.username.getValue()
            //         is loginViewModel.username.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etUsername);
            // localize variables for thread safety
            // loginViewModel != null
            boolean loginViewModelJavaLangObjectNull = false;
            // loginViewModel.username
            androidx.lifecycle.MutableLiveData<java.lang.String> loginViewModelUsername = null;
            // loginViewModel.username.getValue()
            java.lang.String loginViewModelUsernameGetValue = null;
            // loginViewModel.username != null
            boolean loginViewModelUsernameJavaLangObjectNull = false;
            // loginViewModel
            tld.domain.login.LoginViewModel loginViewModel = mLoginViewModel;



            loginViewModelJavaLangObjectNull = (loginViewModel) != (null);
            if (loginViewModelJavaLangObjectNull) {


                loginViewModelUsername = loginViewModel.getUsername();

                loginViewModelUsernameJavaLangObjectNull = (loginViewModelUsername) != (null);
                if (loginViewModelUsernameJavaLangObjectNull) {




                    loginViewModelUsername.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener tvErrorandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of loginViewModel.errorMessage.getValue()
            //         is loginViewModel.errorMessage.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(tvError);
            // localize variables for thread safety
            // loginViewModel != null
            boolean loginViewModelJavaLangObjectNull = false;
            // loginViewModel.errorMessage.getValue()
            java.lang.String loginViewModelErrorMessageGetValue = null;
            // loginViewModel.errorMessage != null
            boolean loginViewModelErrorMessageJavaLangObjectNull = false;
            // loginViewModel
            tld.domain.login.LoginViewModel loginViewModel = mLoginViewModel;
            // loginViewModel.errorMessage
            androidx.lifecycle.MutableLiveData<java.lang.String> loginViewModelErrorMessage = null;



            loginViewModelJavaLangObjectNull = (loginViewModel) != (null);
            if (loginViewModelJavaLangObjectNull) {


                loginViewModelErrorMessage = loginViewModel.getErrorMessage();

                loginViewModelErrorMessageJavaLangObjectNull = (loginViewModelErrorMessage) != (null);
                if (loginViewModelErrorMessageJavaLangObjectNull) {




                    loginViewModelErrorMessage.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public FragmentLoginBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private FragmentLoginBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (android.widget.Button) bindings[4]
            , (android.widget.EditText) bindings[2]
            , (android.widget.EditText) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[5]
            );
        this.btnLogin.setTag(null);
        this.etPassword.setTag(null);
        this.etUsername.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvError.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new tld.domain.login.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.loginViewModel == variableId) {
            setLoginViewModel((tld.domain.login.LoginViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLoginViewModel(@Nullable tld.domain.login.LoginViewModel LoginViewModel) {
        this.mLoginViewModel = LoginViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.loginViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLoginViewModelUsername((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeLoginViewModelPassword((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeLoginViewModelErrorMessage((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLoginViewModelUsername(androidx.lifecycle.MutableLiveData<java.lang.String> LoginViewModelUsername, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLoginViewModelPassword(androidx.lifecycle.MutableLiveData<java.lang.String> LoginViewModelPassword, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLoginViewModelErrorMessage(androidx.lifecycle.MutableLiveData<java.lang.String> LoginViewModelErrorMessage, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.lifecycle.MutableLiveData<java.lang.String> loginViewModelUsername = null;
        java.lang.String loginViewModelErrorMessageGetValue = null;
        java.lang.String loginViewModelPasswordGetValue = null;
        java.lang.String loginViewModelUsernameGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> loginViewModelPassword = null;
        tld.domain.login.LoginViewModel loginViewModel = mLoginViewModel;
        androidx.lifecycle.MutableLiveData<java.lang.String> loginViewModelErrorMessage = null;

        if ((dirtyFlags & 0x1fL) != 0) {


            if ((dirtyFlags & 0x19L) != 0) {

                    if (loginViewModel != null) {
                        // read loginViewModel.username
                        loginViewModelUsername = loginViewModel.getUsername();
                    }
                    updateLiveDataRegistration(0, loginViewModelUsername);


                    if (loginViewModelUsername != null) {
                        // read loginViewModel.username.getValue()
                        loginViewModelUsernameGetValue = loginViewModelUsername.getValue();
                    }
            }
            if ((dirtyFlags & 0x1aL) != 0) {

                    if (loginViewModel != null) {
                        // read loginViewModel.password
                        loginViewModelPassword = loginViewModel.getPassword();
                    }
                    updateLiveDataRegistration(1, loginViewModelPassword);


                    if (loginViewModelPassword != null) {
                        // read loginViewModel.password.getValue()
                        loginViewModelPasswordGetValue = loginViewModelPassword.getValue();
                    }
            }
            if ((dirtyFlags & 0x1cL) != 0) {

                    if (loginViewModel != null) {
                        // read loginViewModel.errorMessage
                        loginViewModelErrorMessage = loginViewModel.getErrorMessage();
                    }
                    updateLiveDataRegistration(2, loginViewModelErrorMessage);


                    if (loginViewModelErrorMessage != null) {
                        // read loginViewModel.errorMessage.getValue()
                        loginViewModelErrorMessageGetValue = loginViewModelErrorMessage.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x10L) != 0) {
            // api target 1

            this.btnLogin.setOnClickListener(mCallback1);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etPassword, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etPasswordandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etUsername, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etUsernameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.tvError, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, tvErrorandroidTextAttrChanged);
        }
        if ((dirtyFlags & 0x1aL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etPassword, loginViewModelPasswordGetValue);
        }
        if ((dirtyFlags & 0x19L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etUsername, loginViewModelUsernameGetValue);
        }
        if ((dirtyFlags & 0x1cL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvError, loginViewModelErrorMessageGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // loginViewModel != null
        boolean loginViewModelJavaLangObjectNull = false;
        // loginViewModel
        tld.domain.login.LoginViewModel loginViewModel = mLoginViewModel;



        loginViewModelJavaLangObjectNull = (loginViewModel) != (null);
        if (loginViewModelJavaLangObjectNull) {


            loginViewModel.attemptLogin();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): loginViewModel.username
        flag 1 (0x2L): loginViewModel.password
        flag 2 (0x3L): loginViewModel.errorMessage
        flag 3 (0x4L): loginViewModel
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}