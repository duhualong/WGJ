package org.eenie.wgj.ui.login;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.eenie.wgj.R;
import org.eenie.wgj.base.BaseFragment;
import org.eenie.wgj.model.ApiResponse;
import org.eenie.wgj.model.response.Token;
import org.eenie.wgj.util.Constants;
import org.eenie.wgj.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static org.eenie.wgj.util.Constants.COUNT_UNIT;
import static org.eenie.wgj.util.Constants.NUM_COUNTDOWN;

/**
 * Created by Eenie on 2017/4/14 at 9:51
 * Email: 472279981@qq.com
 * Des:
 */

public class RegisterFirstFragment extends BaseFragment {
    private CountDownTimer timer;
    private boolean isCounting;
    @BindView(R.id.root_view)View rootView;
    @BindView(R.id.register_fetch_code_button)
    Button btnSendCaptcha;
    @BindView(R.id.user_register_tab_bar_container)LinearLayout taBbackground;
    @BindView(R.id.login_edit_input_phone)EditText inputPhone;
    @BindView(R.id.register_code_edit_text)EditText  inputCode;
    @BindView(R.id.login_edit_input_password)EditText inputPassword;
    @BindView(R.id.checkbox_password_show_state)CheckBox checkboxFirst;
    @BindView(R.id.login_edit_input_password_twice)EditText reInputPassword;
    @BindView(R.id.checkbox_password_show_state_twice)CheckBox checkSecond;
    @BindView(R.id.checkbox_register)CheckBox checkRegister;
    @BindView(R.id.property_register_button)Button btnManager;
    @BindView(R.id.security_register_button)Button btnWork;


    @Override
    protected int getContentView() {
        return R.layout.fragment_register_first;
    }

    @Override
    protected void updateUI() {

    }
    @OnClick({R.id.img_back,R.id.register_submit_button,R.id.register_fetch_code_button,
    R.id.security_register_button,R.id.property_register_button,R.id.checkbox_password_show_state,
    R.id.checkbox_password_show_state_twice,R.id.checkbox_register})
    public void onClick(View view){
        String mPhone=inputPhone.getText().toString();
        String mCaptcha=inputCode.getText().toString();
        String mPassword=inputPassword.getText().toString();
        String mRePassword=reInputPassword.getText().toString();


        switch (view.getId()){
            case R.id.checkbox_password_show_state:
                Utils.setShowHide(checkboxFirst, inputPassword);
                break;
            case R.id.checkbox_password_show_state_twice:
                Utils.setShowHide(checkSecond, reInputPassword);
                break;
            case R.id.img_back:
              onBackPressed();

                break;
            case R.id.register_fetch_code_button:

                if (checkPhone(mPhone)){
                    getCaptcha(mPhone);

                }


                break;
            case R.id.checkbox_register:
                if (checkRegister.isChecked()){
                    Snackbar.make(rootView,"您已同意物管家注册协议",Snackbar.LENGTH_LONG).show();
                }


                break;

            case R.id.register_submit_button:
                //verifyCaptcha(mCaptcha);
//
//               if (checkboxRegisterInfor(mPhone,mCaptcha,mPassword,mRePassword)){
//                   fragmentMgr.beginTransaction()
//                           .addToBackStack(TAG)
//                           .replace(R.id.fragment_login_container,  RegisterSecondFragment.newInstance(mPhone,mPassword))
//                           .commit();
//               }

                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new RegisterSecondFragment())
                        .commit();


                break;
            case R.id.security_register_button:
                taBbackground.setBackgroundResource(R.mipmap.bg_user_register_tab_2);
                btnWork.setTextColor(ContextCompat.getColor
                    (context, R.color.white));
                btnManager.setTextColor(ContextCompat.getColor
                        (context, R.color.colorAccent));
                break;
            case R.id.property_register_button:
                taBbackground.setBackgroundResource(R.mipmap.bg_user_register_tab_1);
                btnWork.setTextColor(ContextCompat.getColor
                        (context, R.color.colorAccent));
                btnManager.setTextColor(ContextCompat.getColor
                        (context, R.color.white));
                break;
        }
    }

    private boolean checkboxRegisterInfor(String phone,String captcha,String password,
                                          String rePassword) {
        boolean mResult=true;
        if (TextUtils.isEmpty(phone)){
            Snackbar.make(rootView,"请填写手机号码！",Snackbar.LENGTH_LONG).show();
            mResult=false;
        }
        if (mResult&&!Utils.isMobile(phone)){
            Snackbar.make(rootView,"请填写正确的手机号码！",Snackbar.LENGTH_LONG).show();
            mResult=false;
        }
        if (mResult&&TextUtils.isEmpty(captcha)){
            mResult=false;
            Snackbar.make(rootView,"请填写验证码",Snackbar.LENGTH_LONG).show();
        }
        if (mResult&&(captcha.length()>6||(captcha.length()>0&&captcha.length()<4))){
            mResult=false;
            Snackbar.make(rootView,"请输入4~6位的验证码！",Snackbar.LENGTH_LONG).show();
        }
        if (mResult){

        }
        if (mResult&&TextUtils.isEmpty(password)){
            mResult=false;
            Snackbar.make(rootView,"设置的密码不能为空！",Snackbar.LENGTH_LONG).show();
        }
        if (mResult&&(password.length()>12||(password.length()>0&&password.length()<6))){
            mResult=false;
            Snackbar.make(rootView,"设置的密码长度必须在6~12为之间！",Snackbar.LENGTH_LONG).show();
        }

        if (mResult&&!password.equals(rePassword)){
            mResult=false;
            inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            reInputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            checkboxFirst.setChecked(true);
            checkSecond.setChecked(true);

            Snackbar.make(rootView,"两次输入的密码不一致,请核对密码！",Snackbar.LENGTH_LONG).show();

        }
        if (mResult&&!checkRegister.isChecked()){
            mResult=false;
            Snackbar.make(rootView,"请勾选物管家服务协议",Snackbar.LENGTH_LONG).show();
        }

        return mResult;

    }

    private void getCaptcha(String mPhone) {
        mSubscription=mRemoteService.fetchMessageCode(mPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ApiResponse<Token>>() {
                    @Override
                    public void onSuccess(ApiResponse<Token> value) {
                        if (value.getResultCode()==200){
                            TimeDown();
                            mPrefsHelper.getPrefs().edit().
                                    putString(Constants.TOKEN, value.getData().getToken()).apply();

                        }else {
                            Snackbar.make(rootView,"获取验证码失败,请检查手机状态！",Snackbar.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable error) {
                        Snackbar.make(rootView,"获取验证码失败！",Snackbar.LENGTH_LONG).show();

                    }
                });


    }

    private boolean checkPhone(String mPhone) {
        boolean result=true;
        if (TextUtils.isEmpty(mPhone)){
            result=false;
            inputPhone.setError("输入的手机号码不能为空!");

        }
        if (result&&!Utils.isMobile(mPhone)){
            Snackbar.make(rootView,"请输入正确的手机号码！",Snackbar.LENGTH_LONG).show();
            result=false;

        }


        return result;


    }
private  void verifyCaptcha(String captcha){
    mSubscription=mRemoteService.verifyCode(mPrefsHelper.getPrefs().getString(Constants.TOKEN,""),captcha)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleSubscriber<ApiResponse>() {
                @Override
                public void onSuccess(ApiResponse value) {
                    System.out.println("token"+value.getResultCode()+value.getResultMessage());
                   if (value.getResultCode()==200){

                   }


                }

                @Override
                public void onError(Throwable error) {

                }
            });
}

    //倒计时
    private void TimeDown() {
        //倒计时开始
        timer = new CountDownTimer(NUM_COUNTDOWN,COUNT_UNIT) {
            @Override
            public void onTick(long l) {
                String info = "重新发送" + l / 1000 + "S";
                @SuppressLint("StringFormatMatches") String count = getString(R.string.find_passwd_send_countdown, l / 1000);
                if (btnSendCaptcha != null && !TextUtils.isEmpty(info)) {
                    btnSendCaptcha.setText(count);
                }
            }

            @Override
            public void onFinish() {
                btnSendCaptcha.setText(R.string.btn_code);
                btnSendCaptcha.setEnabled(true);
                isCounting = false;
            }
        };
        timer.start();
        isCounting = true;
        btnSendCaptcha.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
        isCounting = false;
    }




}
