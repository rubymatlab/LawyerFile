package com.lyf.utils;

import java.io.InputStream;

import com.aspose.words.License;



public class LicenseUtil {
    /**
     * 获取license
     * 
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = LicenseUtil.class.getClassLoader().getResourceAsStream("\\license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
