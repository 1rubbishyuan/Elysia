package com.java.yuanjiarui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.java.yuanjiarui.adapters.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    String keyword;
    String category;
    String startYear, startMonth, startDay;
    String endYear, endMonth, endDay;
    ImageButton button;
    EditText KE, CE, SYE, SME, SDE, EYE, EME, EDE;
    RecyclerView recyclerView;
    int record;
    SearchHistoryAdapter searchHistoryAdapter;
    TextView clearHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SharedPreferences sharedPreferences=getSharedPreferences("history",MODE_PRIVATE);
        record = sharedPreferences.getInt("record", 0);
        KE = findViewById(R.id.search_keyword);
        CE = findViewById(R.id.search_category);
        SYE = findViewById(R.id.search_startYear);
        SME = findViewById(R.id.search_startMonth);
        SDE = findViewById(R.id.search_startDay);
        EYE = findViewById(R.id.search_EndYear);
        EME = findViewById(R.id.search_EndMonth);
        EDE = findViewById(R.id.search_EndDay);
        button = findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = KE.getText().toString();
                if (keyword!=null&&!keyword.equals("")){
                    boolean had=false;
                    for(int i=0;i<record;i++){
                        if (keyword.equals(sharedPreferences.getString("history"+i,"没找到呜呜呜"))){
                            had=true;
                            break;
                        }
                    }
                    if(!had){
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("history"+record,keyword);
                        record+=1;
                        editor.putInt("record",record);
                        editor.apply();
                        searchHistoryAdapter.histories.add(keyword);
                        searchHistoryAdapter.notifyDataSetChanged();
                    }
                }
                category = CE.getText().toString();
                startYear = SYE.getText().toString();
                startMonth = SME.getText().toString();
                if (startMonth != null) {
                    if (startMonth.length() != 2 && startMonth.length() != 0) {
                        startMonth = "0" + startMonth;
                    }
                }
                startDay = SDE.getText().toString();
                if (startDay != null)
                    if (startDay.length() != 2 && startDay.length() != 0) {
                        startDay = "0" + startDay;
                    }
                endYear = EYE.getText().toString();
                endMonth = EME.getText().toString();
                if (endMonth != null)
                    if (endMonth.length() != 2 && endMonth.length() != 0) {
                        endMonth = "0" + endMonth;
                    }
                endDay = EDE.getText().toString();
                if (endDay != null)
                    if (endDay.length() != 2 && endDay.length() != 0) {
                        endDay = "0" + endDay;
                    }
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("keyword", keyword);
                intent.putExtra("category", category);
                if (startYear==null || startMonth==null || startDay==null||startYear.equals("")||startMonth.equals("")||startDay.equals("")) {
                    intent.putExtra("startDate", "");
                } else {
                    intent.putExtra("startDate", startYear + "-" + startMonth + "-" + startDay);
                }
                if (endYear==null || endDay==null || endMonth==null||endYear.equals("")||endMonth.equals("")||endDay.equals("")) {
                    //设置为到今天
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // 注意，Calendar.MONTH返回的月份是从0开始计数的，所以要加1
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    String monthString;
                    String dayString;
                    if (month<10){
                        monthString="0"+month;
                    }
                    else {
                        monthString=String.valueOf(month);
                    }
                    if (day<10){
                        dayString="0"+day;
                    }
                    else {
                        dayString=String.valueOf(day);
                    }

                    String currentDate = year + "-" + monthString + "-" + dayString;

                    intent.putExtra("endDate", currentDate);
                } else {
                    intent.putExtra("endDate", endYear + "-" + endMonth + "-" + endDay);
                }
                startActivity(intent);
            }
        });

        List<String> histories=new ArrayList<>();
        int now =0;
        for(int i=0;i<record;i++){
               String s=sharedPreferences.getString("history"+i,"没找到呜呜呜");
               if(!s.equals("没找到呜呜呜")){
                   histories.add(s);
               }
        }

        recyclerView=findViewById(R.id.history_recyclerView);
        searchHistoryAdapter=new SearchHistoryAdapter(SearchActivity.this,histories);
        searchHistoryAdapter.setOnItemClickedListener(new SearchHistoryAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                KE.setText(searchHistoryAdapter.histories.get(searchHistoryAdapter.histories.size()-1-position));
            }
        });
        recyclerView.setAdapter(searchHistoryAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        clearHistory=findViewById(R.id.clear_history);
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record=0;
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("record",0);
                editor.apply();
                histories.clear();
                searchHistoryAdapter.notifyDataSetChanged();
            }
        });
    }
}