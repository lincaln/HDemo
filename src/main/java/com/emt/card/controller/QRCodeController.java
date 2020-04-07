package com.emt.card.controller;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emt.card.support.BaseController;
import com.emt.card.utils.QRCodeGeneratorUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpHeaders;
import com.google.zxing.WriterException;

@Api(value="二维码",tags ="二维码")
@RestController
@RequestMapping(value = "/QRCode")
public class QRCodeController extends BaseController{
	
	    @RequestMapping(value = "/qrimage",
		produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
		@ApiOperation(value = "生成二维码字节数组")
		@ApiImplicitParams(value = { 
	    		@ApiImplicitParam(name="info",value="二维码内容",required=true,dataType="String"),
		})
		public ResponseEntity<byte[]> getQRImage(String info) {
			
			byte[] qrcode = null;
			try {
				qrcode = QRCodeGeneratorUtil.getQRCodeImage(info, 360, 360);
			} catch (WriterException e) {
				System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
			} 
	 
		    final HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_PNG);
	 
		    return new ResponseEntity<byte[]> (qrcode, headers, HttpStatus.CREATED);
		}


	

}