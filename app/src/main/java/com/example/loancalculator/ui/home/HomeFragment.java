package com.example.loancalculator.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import java.util.ArrayList;
import java.util.List;

import com.example.loancalculator.Calculations;
import com.example.loancalculator.Data;
import com.example.loancalculator.R;
import com.example.loancalculator.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public List<Data> monthList = new ArrayList<>();
    public HomeFragment homeFragment = this;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.root = root;


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

    binding.graphStart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.graphStartText.setText(String.valueOf("Start month: " + progress));
                if (progress < binding.graphEnd.getProgress()) {
                    binding.graphStartText.setText(String.valueOf("Start month: " + progress));
                } else {
                    binding.graphEnd.setProgress(progress);
                    binding.graphStartText.setText(String.valueOf("Start month: " + progress));
                }
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    binding.graphEnd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.graphEndText.setText(String.valueOf("End month: " + progress));
                if (progress > binding.graphStart.getProgress()) {
                    binding.graphEndText.setText(String.valueOf("End month: " + progress));
                } else {
                    binding.graphStart.setProgress(progress);
                    binding.graphEndText.setText(String.valueOf("End month: " + progress));
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
        
    binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTable();
            }
        });
    
    binding.graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillGraph();
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

                binding.graphStart.setMax((int) loanTerm);
                binding.graphEnd.setMax((int) loanTerm);
                binding.graphEnd.setProgress((int) loanTerm);
                binding.graphStart.setProgress(0);

                int postponeStart = (int)startYear * 12 + (int)startMonth;
                int postponeEnd = (int)endYear * 12 + (int)endMonth;


                new Calculations(isAnnuity, isLinear, loanAmount, interestRate,(int)loanTerm, postponeStart, postponeEnd, homeFragment);
                

                

                System.out.println(loanAmount);
            }


        });

        return root;
    }

    public void fillGraph() {
        LineChart lineChart = root.findViewById(R.id.line_chart);
        lineChart.clear();
    
        int graphStart = binding.graphStart.getProgress();
        int graphEnd = binding.graphEnd.getProgress();
    
        List<Entry> entries = new ArrayList<>();
        for (int i = graphStart; i <= graphEnd && i < monthList.size(); i++) {
            entries.add(new Entry(i - graphStart, (float)monthList.get(i).getMonthlyPayment()));
        }
    
        LineDataSet dataSet = new LineDataSet(entries, "Payments");
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.white)); // Get the actual color from the resource id
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
    
        // Create a formatter that converts index values to month names
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value + graphStart; // Adjust the index calculation here
                if (index < 0 || index >= monthList.size()) {
                    return "";
                } else {
                    return monthList.get(index).getMonthName();
                }
            }
        };
    
        // Set the formatter on the X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(formatter);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    
        lineChart.getAxisLeft().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        lineChart.getAxisRight().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        lineChart.getLegend().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    
        lineChart.invalidate(); // refreshes the LineChart
    }

    public void fillTable() {

        int startMonth = binding.filterStart.getProgress();
        int endMonth = binding.filterEnd.getProgress();

        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        TableLayout tableLayout = root.findViewById(R.id.payTable);
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(getContext());

        
        String[] columnNames = {"Month\n", "Payment\n ", "Interest\n", "Remaining Balance\n "};
        for (String columnName : columnNames) {
            TextView header = new TextView(getContext());
            header.setText(columnName);
            header.setGravity(Gravity.CENTER);
            headerRow.addView(header);
        }
    
        
        tableLayout.addView(headerRow);

        for (Data data : monthList) {
            if (data.getIndex() >= startMonth && data.getIndex() <= endMonth) {
                TableRow row = new TableRow(getContext());
                TextView index = new TextView(getContext());
                index.setText(String.valueOf(data.getIndex()));
                index.setGravity(Gravity.CENTER);
                row.addView(index);
                TextView monthlyPayment = new TextView(getContext());
                monthlyPayment.setText(String.format("%.2f", data.getMonthlyPayment()));
                monthlyPayment.setGravity(Gravity.CENTER);
                row.addView(monthlyPayment);
                TextView monthlyInterest = new TextView(getContext());
                monthlyInterest.setText(String.format("%.2f", data.getMonthlyInterest()));
                monthlyInterest.setGravity(Gravity.CENTER);
                row.addView(monthlyInterest);
                TextView remainingBalance = new TextView(getContext());
                remainingBalance.setText(String.format("%.2f", data.getRemainingBalance()));
                remainingBalance.setGravity(Gravity.CENTER);
                row.addView(remainingBalance);
                tableLayout.addView(row);
            }
        }
        
    }

    public void setMonthList(List<Data> monthList) {
        this.monthList = monthList;
        fillTable();
        fillGraph();
    }
    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}