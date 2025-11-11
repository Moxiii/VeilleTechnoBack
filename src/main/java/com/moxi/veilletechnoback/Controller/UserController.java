package com.moxi.veilletechnoback.Controller;

import com.itextpdf.text.DocumentException;
import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.Pdf.PdfReportOptions;
import com.moxi.veilletechnoback.Pdf.PdfService;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moxi.veilletechnoback.DTO.User.PROFILE.UserProfileRes;
import com.moxi.veilletechnoback.Pdf.PdfReportOptions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
private SecurityUtils securityUtils;
@Autowired
private PdfService pdfService;
@GetMapping
public ResponseEntity<UserProfileRes> getUserProfile() {
	User currentUser = securityUtils.getCurrentUser();
	UserProfileRes userProfileRes = new UserProfileRes();
	if (currentUser != null) {
		userProfileRes.setUsername(currentUser.getUsername());
		return ResponseEntity.ok(userProfileRes);
	}
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);

}
@GetMapping("/generate/pdf")
public ResponseEntity<byte[]> generatePDF(@RequestBody(required = false) PdfReportOptions options) throws DocumentException, IOException {
	User currentUser = securityUtils.getCurrentUser();
	if(options == null){
		options = new PdfReportOptions();
	}
	ByteArrayInputStream bis = pdfService.generateUserReport(currentUser , options);
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Disposition", "inline; filename=rapport.pdf");
	return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bis.readAllBytes());
}
}
