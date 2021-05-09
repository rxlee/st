package cn.jy.stork.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("upload")
public class FileUploadController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("")
	public Resp upload(@RequestParam MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return Resp.fail("可能未选择文件");
		}

		String originalFileName = file.getOriginalFilename();
		logger.debug("originalFileName = {}", originalFileName);
		return Resp.okWithMessage(originalFileName);
	}
}
