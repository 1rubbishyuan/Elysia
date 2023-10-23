package com.java.yuanjiarui.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.java.yuanjiarui.R;
import com.java.yuanjiarui.SearchActivity;
import com.java.yuanjiarui.SearchResultActivity;
import com.java.yuanjiarui.adapters.CategoryAdapter;
import com.java.yuanjiarui.adapters.CategoryInfo;
import com.java.yuanjiarui.adapters.ViewPageAdapter;
import com.java.yuanjiarui.tools.UrlDealer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class MainFragment extends Fragment {
    private BottomSheetDialog bottomSheetDialog;
    private ImageButton unfoldCategoryButton;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPageAdapter viewPageAdapter;
    private View dialogView;
    private TextView searchView;
    private boolean editPermission;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment,container,false);
        bottomSheetDialog=new BottomSheetDialog(getActivity());
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_catecory, null);
        bottomSheetDialog.setContentView(dialogView);
        searchView=(TextView) view.findViewById(R.id.searchView);
        editPermission=false;
        //Button sureForEditButton=dialogView.findViewById(R.id.sure_for_edit);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mtoday=format.format(calendar.getTime());
        mtoday=mtoday.substring(0,10);

        Fragment all=(Fragment)new NewsPageFragement(UrlDealer.create_URL("8","","",1,"",mtoday));
        super.onViewCreated(view, savedInstanceState);
        unfoldCategoryButton=(ImageButton) getView().findViewById(R.id.unfold_category);
      //  bottomSheetDialog=new BottomSheetDialog(getActivity());
      //  View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_catecory, null);
      //  Button sureForEditButton=dialogView.findViewById(R.id.sure_for_edit);
      //  bottomSheetDialog.setContentView(dialogView);
       // bottomSheetDialog.setContentView(R.layout.bottom_sheet_catecory);

        String[] categories={"娱乐","军事","教育","文化","健康","财经","体育","汽车","科技","社会"};
        List<CategoryInfo> categoryInfoList=new ArrayList<>();
        for(int i=0;i<categories.length;i++){
            categoryInfoList.add(new CategoryInfo(categories[i],true));
        }

        RecyclerView bottomRecyclerView=bottomSheetDialog.findViewById(R.id.bottom_sheet_recyclerView);
        CategoryAdapter categoryAdapter= new CategoryAdapter(getActivity(),categoryInfoList);
        boolean[] record=new boolean[10];
        for(int i=0;i<record.length;i++){
            record[i]=true;
        }
        categoryAdapter.setOnItemClickedListener(new CategoryAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (editPermission){
                    if(categoryInfoList.get(position).isClicked){
                        categoryAdapter.categoryInfoList.get(position).isClicked=false;
                        record[position]=false;
                    }
                    else{
                        categoryAdapter.categoryInfoList.get(position).isClicked=true;
                        record[position]=true;
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        });
        bottomRecyclerView.setAdapter(categoryAdapter);
        bottomRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        // bottomSheetDialog.setCancelable(false);
        // bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomRecyclerView.addItemDecoration(new MainFragment.CategoryItemDecoration());

        ImageButton sureForEditButton=dialogView.findViewById(R.id.sure_for_edit);

        sureForEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<categoryInfoList.size();i++){
                    categoryInfoList.get(i).shaking=false;
                }
                if (editPermission) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String mtoday = format.format(calendar.getTime());
                    mtoday = mtoday.substring(0, 10);
                    List<Fragment> tmp = new ArrayList<>();
                    tabLayout.removeAllTabs();
                    tmp.add(all);
                    TabLayout.Tab tab = tabLayout.newTab();
                    tab.setText("推荐");
                    tabLayout.addTab(tab);
                    for (int i = 0; i < categories.length; i++) {
                        if (record[i]) {
                            tmp.add(new NewsPageFragement(UrlDealer.create_URL("8", "", categories[i], 1, "", mtoday)));
                            TabLayout.Tab tab1 = tabLayout.newTab();
                            tab1.setText(categories[i]);
                            tabLayout.addTab(tab1);
                            Log.d("theurl", UrlDealer.create_URL("8", "", categories[i], 1, "", mtoday).Url);
                        }
                    }
                    for (int i = 0; i < tmp.size(); i++) {
                        NewsPageFragement f = (NewsPageFragement) tmp.get(i);
                        Log.d("theurl1", f.initURL.Url);
                    }
//                viewPageAdapter.setFragments(tmp);
//                viewPageAdapter.notifyDataSetChanged();
                    ViewPageAdapter viewPageAdapter1 = new ViewPageAdapter(getActivity().getSupportFragmentManager(), getLifecycle(), tmp);
                    viewPager2.setAdapter(viewPageAdapter1);
                }
                categoryAdapter.notifyDataSetChanged();
                editPermission=false;
            }
        });
        //确定的按钮按下后我们对adapter进行更新
        TextView startEdit=bottomSheetDialog.findViewById(R.id.start_edit);
        startEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<categoryInfoList.size();i++){
                    categoryInfoList.get(i).shaking=true;
                    categoryAdapter.notifyDataSetChanged();
                }
                editPermission=true;
            }
        });



        tabLayout=getView().findViewById(R.id.category_tablayout);
        viewPager2=getView().findViewById(R.id.category_viewPage);
        List<Fragment>  init_fragment=new ArrayList<>();
        init_fragment.add(all);
        for(int i=0;i<categories.length;i++){
            init_fragment.add((Fragment)new NewsPageFragement(UrlDealer.create_URL("8","",categories[i],1,"",mtoday)));
        }
        viewPageAdapter=new ViewPageAdapter(getActivity().getSupportFragmentManager(),getLifecycle(),init_fragment);
        viewPager2.setAdapter(viewPageAdapter);

        TabLayout.Tab tab=tabLayout.newTab();
        tab.setText("推荐");
        tabLayout.addTab(tab);
        for(int i=0;i<categories.length;i++){
            TabLayout.Tab tab1=tabLayout.newTab();
            tab1.setText(categories[i]);
            tabLayout.addTab(tab1);
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



        unfoldCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent shareIntent=new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
                
//                shareIntent.setPackage("com.tencent.mm");
//                shareIntent.putExtra(Intent.EXTRA_TEXT,"软件开发测试");
//                startActivity(shareIntent);
//有微信可以使用，没有安装微信的话会闪退

               bottomSheetDialog.show();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","clicked");
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Intent intent=new Intent(getActivity(), SearchResultActivity.class);
//                intent.putExtra("keyword",query);
//                startActivity(intent);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

//                (new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Intent intent=new Intent(getActivity(), SearchResultActivity.class);
//                intent.putExtra("keyword",s);
//                startActivity(intent);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });


    }
    class CategoryItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom=100;
            outRect.left=100;
        }
    }
}
