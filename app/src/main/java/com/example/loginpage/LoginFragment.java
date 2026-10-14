package com.example.loginpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginFragment extends Fragment {

    private EditText etUsername;
    private TextView tvSignupLink;
    private EditText etPassword;
    private Button btnLogin;
    private FirebaseServices fbs;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fbs = FirebaseServices.getInstance();
        etUsername= getView().findViewById(R.id.etUsernameLogin);
        etPassword= getView().findViewById(R.id.etPasswordLogin);
        btnLogin= getView().findViewById(R.id.btnLoginLogin);
        tvSignupLink = getView().findViewById(R.id.tvSignupLinkLogin);
        tvSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignupFragment();

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (email.trim().isEmpty() && password.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                OnCompleteListener<AuthResult> onCompleteListener = new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                };
                Task<AuthResult> authResultTask = fbs.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), onCompleteListener);
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Authentication successful.", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                };
            }
        });
    }
    private void gotoSignupFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        FragmentTransaction ft = transaction;
        ft  = ft.replace(R.id.FragmentLayoutMain, new SignupFragment());
        ft.commit();
    }
}
