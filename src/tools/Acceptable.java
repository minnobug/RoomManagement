/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.util.regex.Pattern;

/**
 *
 * @author LE VAN MINH
 */
public interface Acceptable {
    
    // Định dạng mã phòng (chữ + số, tối đa 5 ký tự)
    String ROOM_ID_VALID = "^[A-Za-z][0-9]{0,4}$";

    // Họ tên: chữ cái và khoảng trắng, 2–25 ký tự
    String NAME_VALID = "^[A-Za-z][A-Za-z ]{1,24}$";

    // Giới tính: chỉ chấp nhận "Male" hoặc "Female"
    String GENDER_VALID = "^(Male|Female)$";

    // Số điện thoại hợp lệ (di động Việt Nam)
    String PHONE_REGEX = "^(03[2-9]|05[6|8|9]|07\\d|08[1-6,8,9]|09\\d)\\d{7}$";
    Pattern PHONE_VALID = Pattern.compile(PHONE_REGEX);

    // CCCD/CMND: đúng 12 chữ số
    String NATIONAL_ID_VALID = "^\\d{12}$";

    // Số nguyên dương
    String POSITIVE_INT = "^[1-9]\\d*$";

    // Số thực dương
    String DOUBLE_POSITIVE = "^[1-9]\\d*(\\.\\d+)?$";

    // Hàm kiểm tra hợp lệ theo regex
    static boolean isValid(String data, String pattern) {
        return data != null && data.matches(pattern);
    }
}
