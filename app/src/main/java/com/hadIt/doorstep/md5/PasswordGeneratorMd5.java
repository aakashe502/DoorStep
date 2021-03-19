package com.hadIt.doorstep.md5;

import android.widget.EditText;

import java.math.BigInteger;
import java.security.MessageDigest;

public class PasswordGeneratorMd5 {
    public String btnMd5(EditText password){
        byte[] md5Input = password.getText().toString().getBytes();
        BigInteger md5Data = null;

        try{
            md5Data = new BigInteger(1, encryptMd5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Data.toString();
    }

    public byte[] encryptMd5(byte[] data)throws Exception{
        MessageDigest md5=MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
}
