package org.eenie.wgj.model.requset;

/**
 * Created by Eenie on 2017/4/11 at 14:15
 * Email: 472279981@qq.com
 * Des:
 */

public class ContactsData<T> {
    private int resultCode;
    private T resultMessage;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(T resultMessage) {
        this.resultMessage = resultMessage;
    }
}
