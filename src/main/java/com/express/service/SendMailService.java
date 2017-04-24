package com.express.service;

import java.io.IOException;
import com.express.model.Express;

public interface SendMailService {

	void sendVertificationCodeByEmail(Express express) throws IOException;

}
