package vico.xin.mvpdemo.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wangc on 2017/10/26
 * E-MAIL:274281610@QQ.COM
 */

@Entity
public class Student {
    @Id
    private int Id;


    @NotNull
    private String name;
    private String age;
    @Generated(hash = 1721670929)
    public Student(int Id, @NotNull String name, String age) {
        this.Id = Id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public int getId() {
        return this.Id;
    }
    public void setId(int Id) {
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
