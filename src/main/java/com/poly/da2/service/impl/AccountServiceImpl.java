package com.poly.da2.service.impl;

import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.entity.NewUserEachMonth;
import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.entity.Account;
import com.poly.da2.repository.PasswordResetTokenRepository;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository acc;
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailService emailService;


	@Override
	public List<Account> findAll() {
		return acc.findAll();
	}

	@Override
	public Account findById(String id) {
		return acc.findById(id).get();
	}

	@Override
	public Account create(Account account) {
		return acc.save(account);
	}

	@Override
	public Account update(Account account) {
		return acc.save(account);
	}

	@Override
	public void delete(String id) {
		acc.deleteById(id);
	}

	@Override
	public Account findbyEmail(String email) {
		return acc.findByEmail(email);
	}


	@Override
	public void deletePasswordResetToken(PasswordResetToken resetToken) {
		passwordResetTokenRepository.delete(resetToken);
	}
	@Override
	public PasswordResetToken generateToken(Account user) {
		// Tạo một token ngẫu nhiên
		String token = UUID.randomUUID().toString();

		// Tạo một đối tượng PasswordResetToken với người dùng và token
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setUsername(user);
		resetToken.setToken(token);

		// Lưu token vào cơ sở dữ liệu
		passwordResetTokenRepository.save(resetToken);

		return resetToken;
	}

	@Override
	public void sendResetEmail(Account user, PasswordResetToken resetToken) {
		// Tạo URL đặt lại mật khẩu với token
		String resetUrl = "https://localhost:8080/reset-password?token=" + resetToken.getToken();

		// Tạo nội dung email
		String emailContent = "Xin chào " + user.getUsername() + ",\n\n"
				+ "Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn.\n"
				+ "Vui lòng nhấp vào liên kết dưới đây để đặt lại mật khẩu:\n"
				+ resetUrl + "\n\n"
				+ "Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.\n\n"
				+ "Trân trọng,\n"
				+ "Đội ngũ quản trị viên";

		// Gửi email đến người dùng
		emailService.sendEmail(user.getGmail(), "Đặt lại mật khẩu", emailContent);
	}
	@Override
	public PasswordResetToken findPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

    @Override
    public List<Account> getAdministrators() {

		return acc.getAdministrators();
    }

    @Override
	public void resetPassword(Account user, String newPassword) {
		// Đặt lại mật khẩu mới cho người dùng
		user.setPassword(newPassword);
		// Lưu người dùng đã được cập nhật
		acc.save(user);
	}
	@Override
	public List<NewUserEachMonth> getNewUserEachMonth(int year) {
		return acc.getNewUserEachMonth(year);
	}

}
