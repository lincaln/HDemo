package com.hsbc.sendMail;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hsbc.sendMail.db.dao.IBaseDao;
import com.hsbc.sendMail.db.mapper.SendMailRequestMapper;
import com.hsbc.sendMail.model.SendMailRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

	@Autowired
    private SendMailRequestMapper userMapper;
	@Autowired
    private IBaseDao baseDao;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        /*SendMailRequest smr=new SendMailRequest();
        smr.setSubject("111");
        baseDao.insert(smr);
        System.err.println(smr.getId());*/
        List<SendMailRequest> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
