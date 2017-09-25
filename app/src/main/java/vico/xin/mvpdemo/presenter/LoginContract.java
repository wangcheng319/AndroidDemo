package vico.xin.mvpdemo.presenter;

import vico.xin.mvpdemo.dto.LoginDto;
import vico.xin.mvpdemo.dto.UserAllInfo;

/**
 * Created by wangc on 2017/5/18
 * E-MAIL:274281610@QQ.COM
 */

public interface LoginContract {

        interface  View extends  BaseView<Presenter>{

                void onSuccess(UserAllInfo userAllInfo);

                void onError(String s);

        }

        interface  Presenter extends  BasePresenter{

                void  doLogin(LoginDto loginDto);

        }
}
