package com.poly.da2.controller;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.PasswordResetTokenRepository;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PasswordResetTokenController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("/forgot-password")
    public String form(){
        return "security/forgot-password";
    }
    @PostMapping("/forgot-password")
    public String submitForgotPasswordForm(@RequestParam("email") String email, Account account, Model model) {
        Account acc = accountRepository.findByEmail(email);
        System.out.println(acc);

        // Kiểm tra nếu email không tồn tại trong hệ thống
        if (acc == null) {
            // Xử lý thông báo lỗi hoặc chuyển hướng đến trang lỗi
            model.addAttribute("messerror","Không tìm thấy email");
            return "security/forgot-password";
        }

        // Tạo mã token ngẫu nhiên
        String token = generateToken();
//        PasswordResetToken passwordResetToken = new PasswordResetToken();
//        passwordResetToken.setToken(token);
//        passwordResetToken.setUsername(acc);
//        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION));
//        passwordResetTokenRepository.save(passwordResetToken);

        // Gửi mã token qua email
        String subject = "Reset your password";
        String text = "  <table bgcolor=\"\" style=\"font-family:'Helvetica','Arial',san-serif;margin:0;max-width:100%;padding:0;width:100%\">\r\n"
                + "        <tbody>\r\n"
                + "            <tr>\r\n"
                + "                <td>\r\n"
                + "                    <center>&nbsp;\r\n"
                + "                        <table style=\"margin:10px auto;max-width:100%;width:600\">\r\n"
                + "                            <tbody>\r\n"
                + "                                <tr>\r\n"
                + "                                    <td width=\"600\" align=\"right\" style=\"font-size:12px;font-style:italic;padding-right:50px;text-align:right\">\r\n"
                + "                                        <a href=\"\" target=\"_blank\" data-saferedirecturl=\"\">\r\n"
                + "                                            <font color=\"#aaaaaa\">Xem bằng trình duyệt →</font>\r\n"
                + "                                        </a>\r\n"
                + "                                    </td>\r\n"
                + "                                </tr>\r\n"
                + "                                <tr>\r\n"
                + "                                    <td style=\"text-align:center\">\r\n"
                + "                                        <center>\r\n"
                + "                                            <a href=\"\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/a/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWTqGHXhhM0un2KAYAbgvL7T9P63W7Ek8N74h_YiKlDIK2peUTJQVk-0iMclFMbPGWFLuSxGJkr1LqXnw25fZFkzE_hF90JSun50tsEu5lXjrBE6t2Cymd7QzlXVH_5QaWtUEU6qWxoPloX_4MXM5LfwTdUno1sMIBvGQ8M_PcYXt5DGGy8hJCfhfCvn8Pt95yc236G8GQsYo3mNLthAExdvC7gx4CyKeUqQB2QVVMTIdXFgq16yDFaOVMpxj0OnqgAZgwKp_TrzN0DgzZChb6UJyauu9Gl7xZFv5tsUU-Wpq5zTiY5DPLQcxTNLZZ4UXNvgS7DeWRdPJ_yg1bFroV-4%253D&amp;source=gmail&amp;ust=1655616138544000&amp;usg=AOvVaw3R0sNrjI68wzw-61Yi5Ekp\"><img height=\"102\" width=\"200\" alt=\"\" style=\"display:block;margin:20px auto 0\" src=\"https://thietkelogo.vn/wp-content/uploads/2016/02/logo-dep.png\" class=\"CToWUd\"></a>\r\n"
                + "                                        </center>\r\n"
                + "                                    </td>\r\n"
                + "                                </tr>\r\n"
                + "                            </tbody>\r\n"
                + "                        </table>\r\n"
                + "                        <table style=\"margin:0 auto;max-width:100%;width:600px\">\r\n"
                + "                            <tbody>\r\n"
                + "                                <tr>\r\n"
                + "                                    <td>\r\n"
                + "                                        <table bgcolor=\"white\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background:white;border-radius:8px;border:0;margin:0px auto;width:100%\">\r\n"
                + "                                            <tbody>\r\n"
                + "                                                <tr>\r\n"
                + "                                                    <td>\r\n"
                + "                                                        <table border=\"0\" cellpadding=\"30\" cellspacing=\"0\">\r\n"
                + "                                                            <tbody>\r\n"
                + "                                                                <tr>\r\n"
                + "                                                                    <td style=\"color:#444444;font-size:18px;line-height:24px\">\r\n"
                + "                                                                        <center>\r\n"
                + "                                                                            <p style=\"color:#444444;text-align:left;line-height:22px;font-size:16px\">\r\n"
                + "                                                                                <font color=\"#444444\">Chào quý khách hàng,</font>\r\n"
                + "                                                                            </p>\r\n"
                + "                                                                            <p style=\"color:#444444;text-align:left;line-height:22px;font-size:16px\">\r\n"
                + "                                                                                <font color=\"#444444\"></font>\r\n"
                + "                                                                            </p>\r\n"
                + "                                                                            <p style=\"color:#444444;text-align:left;line-height:22px;font-size:16px\">\r\n"
                + "                                                                                <font color=\"#444444\">PTP shop đã nhận được đơn đặt hàng của quý khách:</font>\r\n"
                + "                                                                            </p>\r\n"
                + "                                                                            <p style=\"color:#444444;text-align:left;line-height:22px;font-size:16px\"></p>\r\n"
                + "\r\n"
                + "                                                                            <p style=\"color:#444444;font-size:18px;line-height:24px\"></p>\r\n"
                + "                                                                            <p style=\"color:#444444;text-align:left;line-height:22px;font-size:16px\">\r\n"
                + "                                                                                <font color=\"#444444\">Quý khách vui lòng kiểm tra đơn hàng và thời gian nhận hàng <a href=\"\" target=\"_blank\" data-saferedirecturl=\"\">Mọi thắc mắc vui lòng liên hệ qua hotline: 190012345</a>\r\n"
                + "                                                                            </p>\r\n"
                + "                                                                            <p style=\"color:#444444;text-align:left;line-height:22px;font-size:16px\">\r\n"
                + "                                                                                <font color=\"#444444\">Rất vui khi được phục dụ quý khách</font>\r\n"
                + "                                                                            </p>\r\n"
                + "                                                                        </center>\r\n"
                + "                                                                    </td>\r\n"
                + "                                                                </tr>\r\n"
                + "                                                            </tbody>\r\n"
                + "                                                        </table>\r\n"
                + "                                                    </td>\r\n"
                + "                                                </tr>\r\n"
                + "                                            </tbody>\r\n"
                + "                                        </table>\r\n"
                + "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"12\" style=\"border:0;font-size:14px;line-height:20px;max-width:100%;width:100%\">\r\n"
                + "                                            <tbody>\r\n"
                + "                                                <tr>\r\n"
                + "                                                    <td>\r\n"
                + "                                                        <center>\r\n"
                + "                                                            <font color=\"#777777\"><em>Bạn có muốn thêm sản phẩm khác không?<br> Xem các sản phẩm của PTP Shop </em></font>\r\n"
                + "                                                            <a style=\"color:#777777\" href=\"\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/u/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWaxD7L90pL2AlFkYqLCTkVplhZ3pODHtQvPLv03WkHMTmn8YChUeWu5i90OgoX_MxM6R9817RkvKF_VNYHsN2Ie733dw3KL9sJVsxj0x9fKgx_VZHpUTilZv9sF1zRtRD3jylPO6cnlA78hkqBVY-eNvJV36i2v-tzwI6wvfERoSqXJmw5byt0JlS90qv_5Vw0hjXLVfhQG217PicFwYzOYWdfnb5C_D71gDMdiQ1aIZWxZPUPfZUrthSF_J5cHHswAUBPyu3UAGVulXhEVP2r14VcCHARzXwsU2TkyPCi78gzQeI6HC2hd2b1OmVtysDUOjBrHSP6BlGMjn-D_yDgci7tJKAi4nRjCw6Ufa026Q&amp;source=gmail&amp;ust=1655616138544000&amp;usg=AOvVaw39XQotlgkkr2nRWlsQE5_H\">\r\n"
                + "                                                                <font color=\"#777777\"><em>PTP product →</em></font>\r\n"
                + "                                                            </a>\r\n"
                + "                                                        </center>\r\n"
                + "                                                    </td>\r\n"
                + "                                                </tr>\r\n"
                + "                                                <tr>\r\n"
                + "                                                    <td>\r\n"
                + "                                                        <center>\r\n"
                + "                                                            <a style=\"text-decoration:none;padding:8px 0\" href=\"\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/u/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWdfh-xyf92hvtUSrZbksS7r-xrjpTdRJXnQImN8ci6w30dJO6A0FicluRhhYVr9MVKk7GxQShSa_m_XV33k8oYDqfGg69XoQ7jejqf7oEsKYlnAl3p4ZYerTaKHQvPWiPCUgU_qr9FLihgQuK38vBtX2B5_ATQOihoq9kkY3AKYQ8SXPmWLFRcNla7WKWbIbl184YRbO6Tgdwvmw3dyRgZmocZ7FzDDcMTrjg7lYP8uByBseyy4y4v7fDlnTrohD8g%253D%253D&amp;source=gmail&amp;ust=1655616138544000&amp;usg=AOvVaw1SqqZh5mkxAEPhHY3m9oTR\"><img width=\"32\" height=\"32\" alt=\"\" style=\"display:inline\" src=\"https://ci3.googleusercontent.com/proxy/gaTzIwXYgS-BZSovoWDEMJAgklnvHRuCpCbeWBmhi4gUCI6J8uZPlj64CgSKo6UEhIN3S8XN3mG7Lj2n5pmWX5PWG3FPxXI4QKKJDacsfaFL_4jGy7so=s0-d-e1-ft#https://dm9d104elaxz5.cloudfront.net/Plaintext_blue%20buttons/fb.png\"\r\n"
                + "                                                                    class=\"CToWUd\"></a>&nbsp;&nbsp;\r\n"
                + "                                                            <a style=\"text-decoration:none;padding:8px 0\" href=\"\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/u/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWW_wfLiiZ5rTqoag9uXbtzR5YaASDnGK4zeg3QqRVsOnFUJd0Jof5UO-Dt_FMCo7lcBvUzxaZdouIte2wLt-P6Vv5O6D_s6FSXSIzwsBQgLavHvTzXB_pGXWurKPIngaVaBo58SVEDNRqP_Xy8XZxZe7vknE5fFQBI0lKP4BzlpvHQ8VtK3VFmQ_5nnG4iSObB5QwqIj_wDxVlh46A55mZPw5z_TwDEJETIn6_oep3zUe3c1k6mIv_IS6sF2LVjohA%253D%253D&amp;source=gmail&amp;ust=1655616138545000&amp;usg=AOvVaw0PH76FaL8qTFOdOMarhVo5\">\r\n"
                + "                                                                <img width=\"32\" height=\"32\" alt=\"\" style=\"display:inline\" src=\"https://ci5.googleusercontent.com/proxy/fE060TO2gyz1QTuf2ltNw4Y50ee6mcXWxsBWLnF9JUwOcP4rWS6BKrpDfr9FEyqYzjRNkHh9J7Z-l3ce8pBCGm-8Id8zPNZq6nYi2gasYv4ZLchLxonGfYobjN4=s0-d-e1-ft#https://dm9d104elaxz5.cloudfront.net/Plaintext_blue%20buttons/twitter.png\"\r\n"
                + "                                                                    class=\"CToWUd\"></a>&nbsp;&nbsp;\r\n"
                + "                                                            <a style=\"text-decoration:none;padding:8px 0\" href=\"\r\n"
                + "                                                                target=\" _blank \" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/u/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWRt5Yrcy13K8GNak9VdTJTJIzvmiRi2A3Kj-XP6ECQzBT28n_TBGLyhf9gqVMCWJuODwblgRqc2QPr4FMpnU-8_fRKQSgMI9UZoI002G7f6q6hZ8XVtc4q5vRkIjSyj0LOV3xhbxMayXuoLZBq_z4SdNmQCvUri-DXgxVBqYRK89FeyXNFAKBLWJqhlr85zrHB2gzGK8doFhetfJH8fCD0-5kYx1tTOPkoK_SpSNHQABpuDWvxDqMSXbextYtFoltxcJJjYVuTxNQJ1vLxDJMkw%253D&amp;source=gmail&amp;ust=1655616138545000&amp;usg=AOvVaw34rwVqdCh9zULDVNpAOkrf \">\r\n"
                + "                                                                <img width=\"32 \" height=\"32 \" alt=\" \" style=\"display:inline \" src=\"https://ci6.googleusercontent.com/proxy/oeEuH6RkSL3DnUUmgDQIKe_w7A8WrAAh38HDjGJXoPa5WnnjDEE8iInGhKeNcDvl2-p58p1KYxIKYjC7YrsH0sZ4VrHRNEoQnPKqgqFGQw3-_TjlanrXuDSMpGPJ=s0-d-e1-ft#https://dm9d104elaxz5.cloudfront.net/Plaintext_blue%20buttons/linkedin.png \"\r\n"
                + "                                                                    class=\"CToWUd \"></a>&nbsp;&nbsp;\r\n"
                + "                                                            <a style=\"text-decoration:none;padding:8px 0 \" href=\" \"\r\n"
                + "                                                                target=\"_blank \" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/u/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWTispRWWwMEtCkHGgKF_Oiofp818rJ4yjk8fHjMfQZyfsPwFcPJ9D0L7gHqRh2UBu1F0MqMaw9icBNRQsmIDn-hKCwvSfkzq38c8wLbny1DiNI6iVCTMVypw-WRaMRjqVtEoxAeIJVXwTk-_aVH_DaktcLQpqw90CAxKb3TO8RrJXp_EzCFleffht290Xi-KkwKW_VhjUgYWc-dg_nXtisPXVJRu2CszXlVD-n-WZwUHia3urG9RgMvFRzgCAwGOUA%253D%253D&amp;source=gmail&amp;ust=1655616138545000&amp;usg=AOvVaw3gUs-Ik9BMtD3dY-f_rH1N \">\r\n"
                + "                                                                <img width=\"32 \" height=\"32 \" alt=\" \" style=\"display:inline \" src=\"https://ci3.googleusercontent.com/proxy/-WdOA81mUfH0-oRw0hEQxxEaw3K-xgmXserSktQtQX6VmaIfoh7ZCO-JIGxf6qbV3SBK1dE65trXa0H7eAqgQe4r1DrVyBZiWESN9eeNhOXhtCUSYIku8m90=s0-d-e1-ft#https://dm9d104elaxz5.cloudfront.net/Plaintext_blue%20buttons/insta.png \"\r\n"
                + "                                                                    class=\"CToWUd \"></a>\r\n"
                + "                                                        </center>\r\n"
                + "                                                    </td>\r\n"
                + "                                                </tr>\r\n"
                + "                                                <tr>\r\n"
                + "                                                    <td>\r\n"
                + "                                                        <center>\r\n"
                + "                                                            <font color=\"#777777 \"><em>Bản quyền © 2023 PhamPhuc., Bảo lưu toàn quyền.<br> 123, Quận 12</em></font>\r\n"
                + "                                                        </center>\r\n"
                + "                                                    </td>\r\n"
                + "                                                </tr>\r\n"
                + "                                                <tr>\r\n"
                + "                                                    <td>\r\n"
                + "                                                        <center>\r\n"
                + "                                                            <a href=\" \" target=\"_blank \" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/e/evib?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DT0IlUq_i4WSUGuIVhcSn_1w7F95dplBXLs69q1h0-7Hks-M_f9gRah0DXhDHUusm3oAda1PnS53jy6cBgBub_8uRLSrKnVE_igsX5Sqg-ouUWVE0pyy-GPLt6vn29tVY&amp;source=gmail&amp;ust=1655616138545000&amp;usg=AOvVaw2_pjQRruU-YasUHLqIUEzb \">\r\n"
                + "                                                                <font color=\"#aaaaaa \"><em>Xem bằng trình duyệt</em></font>\r\n"
                + "                                                            </a>\r\n"
                + "                                                            <font color=\"#aaaaaa \">|</font>\r\n"
                + "                                                            <a href=\" \" target=\"_blank \" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/a/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWTWEhTbX9UGdNiGgGb93jLvCnrqOvAjYLyLQ6HP4j01jckoqZpGZC_v6j8hQ77VJqfcWYTCGYlzJILtZ6Nx4DeIt2Qix-RPxD2XbORgVaO3hgh2wFA7UgQugu-jIRtW7xuGq_pD8Pxk48zNPkMZsB-Klre0F476H8MsDElBrv9ThsIC0h_oT-MeRi7n-H1XshhxW5vzHl81D2djwTkckjnfzgb_s0iJQ67wfKmSrDicxCL2IPKgV_p_HUGZxWI1iVLumEniZjyWzw7X2udGZnbjShu7P6CDMbbzUs3gQRlCp9xjPQlo_t5vBYFWGIdigcEjUrEsSXuV7_blUS3O5_m4ZCWdlPUCjc0Ixj3VpH77o&amp;source=gmail&amp;ust=1655616138545000&amp;usg=AOvVaw1M5_URBS1nM5mMe8Pie-EA \">\r\n"
                + "                                                                <font color=\"#aaaaaa \"><em>Chính sách Bảo mật</em></font>\r\n"
                + "                                                            </a>\r\n"
                + "                                                            <font color=\"#aaaaaa \">|</font>\r\n"
                + "                                                            <a href=\" \" target=\"_blank \" data-saferedirecturl=\"https://www.google.com/url?q=https://i.trellomail.com/a/click?_t%3Dcce9a87967214196ae72b08c7283fda9%26_m%3Dd82e0b9ebebb45b7ac464b16b74c73eb%26_e%3DnKt1uyMrDz_riD8Kd73sWYmvWAGtW3ACPbZ3iMIM7qLalkq_u0w9f-QXVDCnGbGP2POyabVoC8HLrVIuF8uuPnUqNGEUzzkYPjbYylvwKEPQ7mkaTJQfdTsnRy23SfUz0tn5AJIwAtei3B40s63oK9ScBavF0DHryufh3FF6LKVwamPVB9iQ3VhQHDKRnXPO6LObnWwqHcRp8APm1nN76stVNN7OlPHqX93PRfJnwMKAnaxoJR65Hu2_ZopdN4ccGal2Pbb3nL7uQJs8AeT5dIsv0KE7AQ_EcDtT7NA7rlHTl1ATLUlRtZMQMdFrpfwbhj80sJHxZRU8VQjzOwTNh6YJKt62966bnpTgPhmZ9TVbP28vCH_K1jC0Ox5x-0r5&amp;source=gmail&amp;ust=1655616138545000&amp;usg=AOvVaw0TGCYuT1EIa7-3hziRKFcz \">\r\n"
                + "                                                                <font color=\"#aaaaaa \"><em>Quản lý Tài khoản</em></font>\r\n"
                + "                                                            </a>\r\n"
                + "                                                            <font color=\"#aaaaaa \">|</font>\r\n"
                + "                                                            <a href=\" \">\r\n"
                + "                                                                <font color=\"#aaaaaa \"><em>Bỏ theo dõi</em></font>\r\n"
                + "                                                            </a>\r\n"
                + "                                                        </center>\r\n"
                + "                                                    </td>\r\n"
                + "                                                </tr>\r\n"
                + "                                            </tbody>\r\n"
                + "                                        </table>\r\n"
                + "                                    </td>\r\n"
                + "                                </tr>\r\n"
                + "                            </tbody>\r\n"
                + "                        </table>\r\n"
                + "                    </center>\r\n"
                + "                </td>\r\n"
                + "            </tr>\r\n"
                + "        </tbody>\r\n"
                + "    </table>";
        emailService.sendEmail(email, subject, text);
        model.addAttribute("token",token);
        model.addAttribute("email",email);
        // Chuyển hướng đến trang xác nhận đã gửi mã token thành công
        return "security/reset-password";
    }
    @GetMapping("/reset-password")
    public String form2(){
        return "security/reset-password";
    }
    private String generateToken() {
        // Độ dài của mã token
        int tokenLength = 20;

        // Chuỗi chứa các ký tự cho mã token
        String tokenChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Tạo ngẫu nhiên một chuỗi dấu mở ngoặc với độ dài bằng với tokenLength
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder tokenBuilder = new StringBuilder(tokenLength);
        for (int i = 0; i < tokenLength; i++) {
            int randomIndex = secureRandom.nextInt(tokenChars.length());
            char randomChar = tokenChars.charAt(randomIndex);
            tokenBuilder.append(randomChar);
        }

        // Mã hóa chuỗi token bằng Base64 để tạo mã token dạng chuỗi an toàn
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBuilder.toString().getBytes());

        return token;
    }

    @PostMapping("/reset-password")
    public String submitResetPasswordForm(@RequestParam("token") String token,
                                          @RequestParam("password") String password,
                                          @RequestParam("email") String email,
                                          @RequestParam("confirmPassword") String confirmPassword,
                                          Model model) {
        // Kiểm tra mã token trong cơ sở dữ liệu hoặc bất kỳ nơi nào phù hợp
        // Kiểm tra trùng khớp mật khẩu xác nhận
        if (!password.equals(confirmPassword) ) {
            // Xử lý thông báo lỗi hoặc chuyển hướng đến trang lỗi
            model.addAttribute("messerror","Password không trùng nhau");
            return "security/reset-password";
        }else{
            // Lấy thông tin người dùng dựa trên mã token
            //Account user = getUserDetailsFromToken(token);
            //PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
            if (token != null ) {
                // Tìm thông tin người dùng dựa trên id người dùng từ mã token
                Account user = accountRepository.findByEmail(email);
                if (user != null) {
                    user.setPassword(confirmPassword);
                    accountRepository.save(user);
                    model.addAttribute("message","Đổi mật khẩu thành công");
                }
            }
        }
        //String encodedPassword = passwordEncoder.encode(password);
        //updateUserPassword(user.getGmail(), encodedPassword);
        // Chuyển hướng đến trang xác nhận đã đổi mật khẩu thành công
        return "security/login";
    }

//    private Account getUserDetailsFromToken(String token) {
//        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
//        if (passwordResetToken != null && passwordResetToken.isExpired()) {
//            // Tìm thông tin người dùng dựa trên id người dùng từ mã token
//            Account user = accountRepository.findByEmail(passwordResetToken.getUsername().getGmail());
//            if (user != null) {
//                return new Account(user.getGmail(), user.getPassword());
//            }
//        }
//        return null;
//    }
    private void updateUserPassword(String gmail, String encodedPassword) {
        Account user = accountRepository.findByEmail(gmail);
        if (user != null) {
            user.setPassword(encodedPassword);
            accountRepository.save(user);
        }
    }
}
