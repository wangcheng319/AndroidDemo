package vico.xin.mvpdemo.crypto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoming on 15/11/5.
 */
public class Form {
    public String userName;
    public Integer age;
    public List<Log> logs = new ArrayList<Log>();

    static class Log {
        public String item;
        public String before;
        public String after;
        public String location;
        public String date;

        public Log(String item, String before, String after, String location, String date){
            this.item = item;
            this.before = before;
            this.after = after;
            this.location = location;
            this.date = date;
        }
    }
}


