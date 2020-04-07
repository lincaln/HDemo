package com.emt.card.services.impl;

import com.baidu.fsg.uid.UidGenerator;
import com.emt.card.services.UidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UidServiceImpl implements UidService {

    @Autowired
    private UidGenerator uidGenerator;

    @Override
    public String getUID() {
        return uidGenerator.getUID()+"";
    }
}
