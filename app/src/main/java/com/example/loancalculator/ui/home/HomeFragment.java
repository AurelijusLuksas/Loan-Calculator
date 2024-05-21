package com.example.loancalculator.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.loancalculator.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

    binding.filterStart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.filterStartText.setText(String.valueOf("Start month: " + progress));
                if (progress < binding.filterEnd.getProgress()) {
                    binding.filterStartText.setText(String.valueOf("Start month: " + progress));
                } else {
                    binding.filterEnd.setProgress(progress);
                    binding.filterStartText.setText(String.valueOf("Start month: " + progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    binding.filterEnd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.filterEndText.setText(String.valueOf("End month: " + progress));
                if (progress > binding.filterStart.getProgress()) {
                    binding.filterEndText.setText(String.valueOf("End month: " + progress));
                } else {
                    binding.filterStart.setProgress(progress);
                    binding.filterEndText.setText(String.valueOf("End month: " + progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
                            
    binding.isAnnuity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.isLinear.setChecked(false);
                }
            }
        });

    binding.isLinear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.isAnnuity.setChecked(false);
                }
            }
        });
        
    binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double loanAmount = 0.0;
                if (!binding.loanAmount.getText().toString().isEmpty()) {
                    loanAmount = Double.parseDouble(binding.loanAmount.getText().toString());
                } else {
                    // Show error message or set default value
                }

                double interestRate = 0.0;
                if (!binding.interestRate.getText().toString().isEmpty()) {
                    interestRate = Double.parseDouble(binding.interestRate.getText().toString());
                } else {
                    // Show error message or set default value
                }

                double loanTerm = 0.0;
                if (!binding.termYears.getText().toString().isEmpty() && !binding.termMonths.getText().toString().isEmpty()) {
                    loanTerm = Double.parseDouble(binding.termYears.getText().toString()) * 12 + Double.parseDouble(binding.termMonths.getText().toString());
                } else {
                    // Show error message or set default value
                }

                double monthlyPayment = 0.0;
                if (loanAmount != 0.0 && interestRate != 0.0 && loanTerm != 0.0) {
                    monthlyPayment = loanAmount * (interestRate / 1200) / (1 - Math.pow(1 + interestRate / 1200, -loanTerm));
                } else {
                    // Show error message or set default value
                }

                boolean isAnnuity = binding.isAnnuity.isChecked();
                boolean isLinear = binding.isLinear.isChecked();

                double startYear = 0.0;
                if (!binding.startYear.getText().toString().isEmpty()) {
                    startYear = Double.parseDouble(binding.startYear.getText().toString());
                } else {
                    // Show error message or set default value
                }
                
                double startMonth = 0.0;
                if (!binding.startMonth.getText().toString().isEmpty()) {
                    startMonth = Double.parseDouble(binding.startMonth.getText().toString());
                } else {
                    // Show error message or set default value
                }
                
                double endYear = 0.0;
                if (!binding.endYear.getText().toString().isEmpty()) {
                    endYear = Double.parseDouble(binding.endYear.getText().toString());
                } else {
                    // Show error message or set default value
                }
                
                double endMonth = 0.0;
                if (!binding.endMonth.getText().toString().isEmpty()) {
                    endMonth = Double.parseDouble(binding.endMonth.getText().toString());
                } else {
                    // Show error message or set default value
                }
                
                binding.filterEnd.setMax((int) loanTerm);
                binding.filterStart.setMax((int) loanTerm);
                binding.filterEnd.setProgress((int) loanTerm);
                binding.filterStart.setProgress(0);
                
                System.out.println(loanAmount);
            }
        });

        return root;
    }

    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}