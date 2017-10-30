package vico.xin.mvpdemo.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wangc on 2017/10/26
 * E-MAIL:274281610@QQ.COM
 */

@Entity
public class Teacher {
    @NotNull
    private String name;

    @Generated(hash = 1621495743)
    public Teacher(@NotNull String name) {
        this.name = name;
    }

    @Generated(hash = 1630413260)
    public Teacher() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
