package com.ntsphere.ecn.basicweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ntsphere.common.util.ObjectHashMap;
import com.ntsphere.common.util.RestResponse;
import com.ntsphere.ecn.basicweb.repository1.Query1;
import com.ntsphere.ecn.basicweb.repository2.Query2;

@Controller
@RequestMapping("/api")
public class RestApiController extends BaseController {
	
	@Autowired private Query1 query1;
	@Autowired private Query2 query2;
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public int add(@RequestParam("val1") int val1, @RequestParam("val2") int val2) {
		
		int ret = val1 + val2;
		return ret;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/div", method=RequestMethod.GET)
	public double div(@RequestParam("val1") double val1, @RequestParam("val2") double val2) {
		
		double ret = val1 + val2;
		return ret;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/queryTest", method=RequestMethod.GET)
	public ResponseEntity<?> getUserList() {
		
		HashMap<String, Object> res1 = query1.selectTest(null);
		HashMap<String, Object> res2 = query2.selectTest(null);
		
		return RestResponse.ok()
						.setData(ObjectHashMap.newInstance()
									.add("res1", res1)
									.add("res2", res2))
						.responseEntity();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/fileUpload", method=RequestMethod.POST)
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
	@RequestMapping(value="/fileDownload", method=RequestMethod.GET)
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
