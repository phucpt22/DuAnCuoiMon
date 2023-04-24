package com.poly.da2.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	ServletContext app;
	

	public String getString(String name, String defaultValue) {
		String value = request.getParameter(name);
		return value != null ? value : defaultValue;
	}

	public int getInt(String name, int defaultValue) {
		String result = request.getParameter(name);
		return result != null ? Integer.parseInt(result) : defaultValue;
	}
	

	public double getDouble(String name, double defaultValue) {
		String result = request.getParameter(name);
		return result != null ? Double.parseDouble(result) : defaultValue;
	}
	

	public boolean getBoolean(String name, boolean defaultValue) {
		String result = request.getParameter(name);
		return result != null ? Boolean.parseBoolean(result) : defaultValue;
	}

	public Date getDate(String name, String pattern) {
		String value = request.getParameter(name);
		if (!value.matches(pattern)) {
			return Date.valueOf(value);
		}
		return null;
	}

	
	// Nếu thư mục upload chưa được tạo thì sẽ tạo
	public File createFloder(String nameFolder) {
		File uploadRootDir = new File(app.getRealPath(nameFolder));
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		return uploadRootDir;
	}
	
	// Lưu hình người dùng đã chọn
	public String saveImage(MultipartFile attach,String path) throws IOException {
		// Nếu thư mục chưa được tạo thì sẽ tạo
		File uploadRootDir= createFloder(path);
		// Lấy tên file được chọn
		String filename = attach.getOriginalFilename();
		File serverFile = new File(uploadRootDir.getAbsoluteFile() + File.separator + filename);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(attach.getBytes());
		stream.close();
		return filename;
	}
}
