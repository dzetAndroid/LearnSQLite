package com.zet.learnsqlite.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zet on 2017/7/2.
 */

public class People implements Parcelable {

    public static final String genderMale = "男";
    public static final String genderFamale = "女";

    private Long _id;
    private String name;
    private int gender;
    private int age;

    @Override
    public String toString() {
        return "People{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }

    public static People create(Long _id, String name, int gender, int age){
        return new People(_id, name, gender, age);
    }

    public People(Long _id, String name, int gender, int age) {
        this._id = _id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public People() {
    }

    protected People(Parcel in) {
        name = in.readString();
        gender = in.readInt();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(gender);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
