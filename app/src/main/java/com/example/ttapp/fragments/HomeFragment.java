package com.example.ttapp.fragments;

import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ttapp.R;
import com.example.ttapp.databinding.FragmentHomeBinding;

/**
 * Class for a fragment that presents the home page to the application
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Amanda Cyrén & Philip Winsnes
 */
public class HomeFragment extends Fragment {

    private SharedPreferences sharedPref;

    private Button buttonStartSurvey, buttonSignOut;
    private ImageButton buttonUser, buttonCloseUserSection;
    private TextView textViewIdentifier, textViewHomeHeader;
    ConstraintLayout userContainer;

    private boolean userSectionIsOpen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        buttonStartSurvey = binding.buttonStartSurvey;
        buttonSignOut = binding.buttonSignOut;
        buttonUser = binding.buttonUser;
        buttonCloseUserSection = binding.buttonCloseUserSection;
        userContainer = binding.userContainer;
        textViewHomeHeader = binding.textViewHomeHeader;

        buttonStartSurvey.setOnClickListener(view1 -> startSurvey(root));

        buttonSignOut.setOnClickListener(view -> signOut(root));

        buttonCloseUserSection.setOnClickListener(view -> onUserSectionButtonClick());

        buttonUser.setOnClickListener(view -> onUserSectionButtonClick());
            //FragmentTransaction ft = getChildFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).hide().commit();

        return root;
    }

    private void startSurvey(View view) {
        changeStatusBarColor(R.color.toto_background_gradient_blue);
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_surveyFragment);
    }

    private void signOut(View view) {
        changeStatusBarColor(R.color.toto_background_gradient_blue);
        forgetIdentifier();
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registerFragment);
    }

    public void closeUserSection() {
        buttonUser.setEnabled(false);
        buttonCloseUserSection.setEnabled(false);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                -userContainer.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        userContainer.startAnimation(animate);
        userContainer.setVisibility(GONE);
        new CountDownTimer(500,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                buttonUser.setEnabled(true);
                buttonCloseUserSection.setEnabled(true);
                changeStatusBarColor(R.color.toto_background_gradient_blue);
            }
        }.start();
    }

    public void openUserSection() {
        buttonUser.setEnabled(false);
        buttonCloseUserSection.setEnabled(false);
        changeStatusBarColor(R.color.toto_dark_grey);
        userContainer.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                -userContainer.getHeight(),
                0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        userContainer.startAnimation(animate);
        new CountDownTimer(500,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                buttonCloseUserSection.setEnabled(true);
                buttonCloseUserSection.setEnabled(true);
            }
        }.start();
    }

    public void onUserSectionButtonClick() {
        if (userSectionIsOpen) {
            closeUserSection();
        } else {
            openUserSection();
        }
        userSectionIsOpen = !userSectionIsOpen;
    }

    private void changeStatusBarColor(int color) {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(color));
    }

    private void forgetIdentifier() {
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", "");
        editor.apply();
    }

}