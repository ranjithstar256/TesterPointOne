package com.am.testerpointone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.am.testerpointone.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ExamsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Exam_Adapter mAdapter;
    List<Exam_Model_List> rowListItem;
    private JSONArray result;
    public static final String JSON_ARRAY = "result";

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AutoCompleteTextView edit_search;
    LinearLayout empty_view;
    PrefManager prefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_exams,container,false);
        deleteCache(getContext());
        showDialog();
        empty_view  = (LinearLayout) root.findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView)  root.findViewById(R.id.recyclerview);
        edit_search=(AutoCompleteTextView) root.findViewById( R.id.et_search);
        rowListItem=new ArrayList<Exam_Model_List>();
        prefManager= new PrefManager(getActivity());
        if (prefManager.isregistered()){
            get_Examinations();
        }else {
            startActivity(new Intent(getActivity(),Registration.class));
        }

        return root;
    }

    public void get_Examinations(){
        StringRequest stringRequest = new StringRequest( Request.Method.POST, MyConfig.URL_GET_Examinations,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(JSON_ARRAY);
                            getCategory(result);
                            Log.i("123sdgsdgsd",result+"\n"+response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                sharedPreferences=getContext().getSharedPreferences("Mydata",MODE_PRIVATE);
                sharedPreferences.edit();
                //String student_class="CL-376624"; //sharedPreferences.getString("student_class",null);
                //String student_id= "ST-309939";//sharedPreferences.getString("idtag",null);
                String student_class=sharedPreferences.getString("student_class",null);
                String student_id= sharedPreferences.getString("idtag",null);

                Log.e("student_class",student_class +" student_id:"+student_id);

          //      params.put("student_class",student_class);
         //       params.put("student_id",student_id);
                params.put("student_class","CL-473725");
                params.put("student_id","ST-075246");

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }
    private void getCategory(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                rowListItem.add(new Exam_Model_List(json.getString("exam_id"),
                        json.getString("duration"),
                        json.getString("passmark"),
                        json.getString("re_take_after"),
                        json.getString("exam_title"),
                        json.getString("type"),
                        json.getString("exam_fee"),
                        json.getString("dept_name"),
                        json.getString("class_name"),
                        json.getString("subject"),
                        json.getString("deadline"),
                        json.getString("exam_status"),
                        json.getString("next_retake"),
                        json.getString("quetions"),
                        json.getString("exam_attended"),
                        json.getString("student_retake"),
                        json.getString("exam_allowed"),
                        json.getString("next_retake_b"),
                        json.getString("terms")

                ) );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (rowListItem.size() > 0){
            progressDialog.dismiss();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1, LinearLayoutManager.VERTICAL,false);
            mRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            //rowListItem = get_ALL_CATEGORY();
            mAdapter =new Exam_Adapter(getActivity(),rowListItem);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }else{
            progressDialog.dismiss();
            empty_view.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }


        // Capture Text in EditText
        edit_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = edit_search.getText().toString().toLowerCase( Locale.getDefault());
                mAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


    }
    private void showDialog() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Exams");
            progressDialog.setMessage("Please wait while showing Data ...  ");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            ProgressBar progressbar=(ProgressBar)progressDialog.findViewById(android.R.id.progress);
            progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FF7043"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
            }
}