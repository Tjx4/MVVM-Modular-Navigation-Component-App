package tld.domain.login;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\"\u001a\u00020#J!\u0010$\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\rR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\b8F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\b8F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\b8F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0016R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\b8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0016R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\b8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u0016R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\b8F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0016R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\r0\b8F\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\'"}, d2 = {"Ltld/domain/login/LoginViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "authenticationRepository", "Lcom/domain/repositories/authentication/AuthenticationRepository;", "(Landroid/app/Application;Lcom/domain/repositories/authentication/AuthenticationRepository;)V", "_currentUser", "Landroidx/lifecycle/MutableLiveData;", "Lcom/domain/myapplication/models/User;", "_isValidInput", "", "_loginErrorMessage", "", "_password", "_passwordErrorMessage", "_username", "_usernameErrorMessage", "getAuthenticationRepository", "()Lcom/domain/repositories/authentication/AuthenticationRepository;", "currentUser", "getCurrentUser", "()Landroidx/lifecycle/MutableLiveData;", "isValidInput", "loginErrorMessage", "getLoginErrorMessage", "password", "getPassword", "passwordErrorMessage", "getPasswordErrorMessage", "username", "getUsername", "usernameErrorMessage", "getUsernameErrorMessage", "attemptLogin", "", "loginUser", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateDetails", "login_debug"})
public final class LoginViewModel extends androidx.lifecycle.AndroidViewModel {
    private final android.app.Application app = null;
    @org.jetbrains.annotations.NotNull()
    private final com.domain.repositories.authentication.AuthenticationRepository authenticationRepository = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _username = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _password = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _usernameErrorMessage = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _passwordErrorMessage = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _loginErrorMessage = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isValidInput = null;
    private final androidx.lifecycle.MutableLiveData<com.domain.myapplication.models.User> _currentUser = null;
    
    public LoginViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application app, @org.jetbrains.annotations.NotNull()
    com.domain.repositories.authentication.AuthenticationRepository authenticationRepository) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.domain.repositories.authentication.AuthenticationRepository getAuthenticationRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getUsername() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getPassword() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getUsernameErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getPasswordErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getLoginErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isValidInput() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.domain.myapplication.models.User> getCurrentUser() {
        return null;
    }
    
    public final void attemptLogin() {
    }
    
    public final void validateDetails(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loginUser(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}