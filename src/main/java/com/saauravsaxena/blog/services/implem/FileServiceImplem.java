package com.saauravsaxena.blog.services.implem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saauravsaxena.blog.services.FileServices;

@Service
public class FileServiceImplem implements FileServices{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		String name = file.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		
		System.out.println(name.charAt(0)+" "+"lola");
		
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		String filePath = path + File.separator + fileName;
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
