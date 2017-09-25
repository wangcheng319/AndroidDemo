package vico.xin.mvpdemo.crypto;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoming on 15/11/5.
 */
public class Client {

    private static String secretKey = "fsgjernfjwnfjeft";

    public static void main(String[] args) {
        System.out.print(encryptoForm());
    }

    public static String encryptoForm() {
        Form form = new Form();
        List<Form.Log> logs = new ArrayList<Form.Log>();
        logs.add(new Form.Log("工作单位", "上海市工商局", "上海市工商管理局", "上海市新闸路68号", "20151105164523"));
        logs.add(new Form.Log("工作单位", "上海市工商管理局", "上海市工商局", "上海市新闸路68号", "20151105165553"));

        form.userName = "xiaoming";
        form.age = 27;
        form.logs = logs;

        Gson gson = new Gson();

        String josn = gson.toJson(form, Form.class);

        String encrytoJson = RRCrypto.encryptAES(josn, secretKey);

        return encrytoJson;
    }
}

