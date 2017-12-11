package vico.xin.mvpdemo.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangc on 2017/12/7
 * E-MAIL:274281610@QQ.COM
 */

@Entity
public class Student {

    private String Id;
    private String name;
    private String age;
    @Generated(hash = 1003236172)
    public Student(String Id, String name, String age) {
        this.Id = Id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public String getId() {
        return this.Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
}
