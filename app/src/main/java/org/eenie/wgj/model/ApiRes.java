package org.eenie.wgj.model;

/**
 * Created by Eenie on 2017/4/13 at 12:53
 * Email: 472279981@qq.com
 * Des:
 */

public class ApiRes {
    private int resultCode;
    private String resultMessage;
    private String  data;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
