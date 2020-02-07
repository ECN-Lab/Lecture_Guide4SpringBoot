package com.ntsphere.ecn.basicweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ntsphere.common.util.RestResponse;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	
	@ResponseBody
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ResponseEntity<?> fileUpload(MultipartHttpServletRequest request) {
		
		try {
			//  Get file from request
			MultipartFile multipartFile = request.getFile("file");
			if (multipartFile == null) {
				return RestResponse.badRequest("No attached file.")
							.responseEntity();
			}

			String baseDir = "C:\\";
			String originalFilename = multipartFile.getOriginalFilename();
			
			
			//  Save file
			String savePath = Paths.get(baseDir, originalFilename).toString();
			File file = new File(savePath);
			file.delete();		//  Delete file if same named file exists.
			multipartFile.transferTo(file);
			
			
			//  Modify file permissions
			file.setReadable(true, false);
			file.setWritable(false, false);
			file.setExecutable(false, false);
		}
		catch (Exception e) {
			return RestResponse.internalServerError(e)
					.responseEntity();
		}
		
		return RestResponse.ok()
							.responseEntity();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public void fileDownload(HttpServletResponse response) throws Exception {
		String originalFilename = "Filename.ext";
		String sourcePath = "C:\\" + originalFilename;
		
		File fileHandle = new File(sourcePath);
		InputStream stream = null;
		String encodedFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
		
		
		try {
			stream = new FileInputStream(fileHandle);
			
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentLength((int)fileHandle.length());

			byte[] buffer = new byte[1024 * 1024];
			int readBytes = 0;
			while ((readBytes = stream.read(buffer)) > 0) {
				response.getOutputStream().write(buffer, 0, readBytes);
				response.getOutputStream().flush();
			}
			
			stream.close();
		}
		catch (Exception e) {
			if (stream != null)
				stream.close();
			
			throw e;
		}
	}
}
