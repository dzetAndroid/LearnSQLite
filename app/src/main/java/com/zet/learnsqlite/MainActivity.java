package com.zet.learnsqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.zet.learnsqlite.bean.People;
import com.zet.learnsqlite.db.PeopleDao;
import com.zet.learnsqlite.fragment.ChoosePeopleOperationDialogFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private static boolean isUpdate = false;

    private QuickAdapter<People> mPeopleQuickAdapter;
    private EditText mEtId;
    private EditText mEtName;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;
    private RadioGroup mRgGender;
    private EditText mEtAge;
    private LinearLayout mLlInput;
    private Button mBtInsert;
    private Button mBtUpdate;
    private Button mBtDeleteAll;
    private LinearLayout mLlEdit;
    private ListView mLvPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setViews();
    }

    private void setViews() {
        setPeopleListView();
    }

    private void setPeopleListView() {
        // keymap eclipse
        // ctrl alt f ==> 私有全局字段
        mPeopleQuickAdapter = new QuickAdapter<People>(
                MainActivity.this, R.layout.item_people, PeopleDao.with(this).queryAll()) {
            @Override
            protected void convert(BaseAdapterHelper helper, People item) {
//                Log.e(TAG, "convert: item.toString() " + item.toString());
                helper.setText(R.id.mTvId, String.valueOf(item.get_id()));
                helper.setText(R.id.mTvName, item.getName());
                String gender = People.genderMale;
                if (0 == item.getGender()) {
                    gender = People.genderFamale;
                }
                helper.setText(R.id.mTvGender, gender);
                helper.setText(R.id.mTvAge, String.valueOf(item.getAge()));
            }
        };
        mLvPeople.setAdapter(mPeopleQuickAdapter);
        mLvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                clearPeopleInput();

                final People item = mPeopleQuickAdapter.getItem(position);
                final Long item_id = item.get_id();

//                FireMissilesDialogFragment.show(MainActivity.this, String.valueOf(item.get_id()));

                ChoosePeopleOperationDialogFragment.getInstance().show(MainActivity.this, String.valueOf(item_id), new ChoosePeopleOperationDialogFragment.IDealItemsClick() {
                    @Override
                    public void dealItemsClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                PeopleDao.with(MainActivity.this).delete(item_id);
                                updateListViewUI();
                                break;
                            case 1:
                                setPeopleInput(item); // 修改选中执行方法
                                isUpdate = true; // 是否更新, true
                                break;
                        }
                    }
                });

//                new AlertDialog
//                        .Builder(MainActivity.this)
//                        .setTitle("询问框")
//                        .create().show();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PeopleDao.with(this).destroy();
    }

    private void clearPeopleInput() {
        mEtId.setText("");
        mEtName.setText("");
        mRbMale.setChecked(true);
        mEtAge.setText("");
    }

    private void setPeopleInput(People people) {
        Log.e(TAG, "setPeopleInput: " + people.toString());
        final Long item_id = people.get_id();
        final String itemName = people.getName();
        final int itemGender = people.getGender();
        final int itemAge = people.getAge();

        mEtId.setText(String.valueOf(item_id));
        mEtName.setText(itemName);
        switch (itemGender) {
            case 0:
                mRbFemale.setChecked(true);
                break;
            case 1:
                mRbMale.setChecked(true);
                break;
        }
        mEtAge.setText(String.valueOf(itemAge));
    }

    @NonNull
    private People getPeopleInput() {
        String strId = mEtId.getText().toString();
        Long _id = null;
        if (!TextUtils.isEmpty(strId) && !isUpdate) {
            _id = Long.parseLong(strId);
        }
        String name = mEtName.getText().toString();
        int gender = 1;
        RadioButton radioButton = (RadioButton) findViewById(mRgGender.getCheckedRadioButtonId());
        String strGender = radioButton.getText().toString();
        switch (strGender) {
            case "男":
                gender = 1;
                break;
            case "女":
                gender = 0;
                break;
        }
        String strAge = mEtAge.getText().toString();
        int age = 18;
        boolean isEmpty = TextUtils.isEmpty(strAge);
        Log.e(TAG, "getPeopleInput: isEmpty " + isEmpty);
        if (!isEmpty) {
            age = Integer.parseInt(strAge);
        }
        People people = People.create(_id, name, gender, age);
        Log.e(TAG, "getPeopleInput: " + people.toString());
        return people;
    }

    private void initView() {
        mEtId = (EditText) findViewById(R.id.mEtId);
        mEtName = (EditText) findViewById(R.id.mEtName);
        mRbMale = (RadioButton) findViewById(R.id.mRbMale);
        mRbFemale = (RadioButton) findViewById(R.id.mRbFemale);
        mRgGender = (RadioGroup) findViewById(R.id.mRgGender);
        mEtAge = (EditText) findViewById(R.id.mEtAge);
        mLlInput = (LinearLayout) findViewById(R.id.mLlInput);
        mBtInsert = (Button) findViewById(R.id.mBtInsert);
        mBtUpdate = (Button) findViewById(R.id.mBtUpdate);
        mBtDeleteAll = (Button) findViewById(R.id.mBtDeleteAll);
        mLlEdit = (LinearLayout) findViewById(R.id.mLlEdit);
        mLvPeople = (ListView) findViewById(R.id.mLvPeople);

        mBtInsert.setOnClickListener(this);
        mBtUpdate.setOnClickListener(this);
        mBtDeleteAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtInsert:
                mBtInsert();
                break;
            case R.id.mBtUpdate:
                mBtUpdate();
                break;
            case R.id.mBtDeleteAll:
                mBtDeleteAll();
                break;
        }
    }

    private void mBtDeleteAll() {
        PeopleDao.with(MainActivity.this).deleteAll();
        updateListViewUI();
    }

    private void updateListViewUI() {
        mPeopleQuickAdapter.replaceAll(PeopleDao.with(this).queryAll());
    }

    private void mBtUpdate() {
        PeopleDao.with(this).update(getPeopleInput());
        clearPeopleInput();
        updateListViewUI();
    }

    private void mBtInsert() {
        PeopleDao.with(this).insert(getPeopleInput()); // 数据库新增people
        clearPeopleInput();
        updateListViewUI();
    }

}
