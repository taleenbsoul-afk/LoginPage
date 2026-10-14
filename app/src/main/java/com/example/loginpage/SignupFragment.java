package com.example.loginpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupFragment extends Fragment {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignup;
    private FirebaseServices fbs;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fbs = FirebaseServices.getInstance();

        etUsername = view.findViewById(R.id.etUsernameSignup);
        etPassword = view.findViewById(R.id.etPasswordSignup);
        btnSignup = view.findViewById(R.id.btnSignupSignup);

        btnSignup.setOnClickListener(v -> {
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etUsername.setError("Email is required");
                etUsername.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etUsername.setError("Please enter a valid email address");
                etUsername.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                etPassword.setError("Password must be at least 6 characters");
                etPassword.requestFocus();
                return;
            }

            fbs.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = task.getException() != null ? task.getException().getMessage() : "Signup failed";
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
