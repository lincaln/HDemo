package com.emt.card.db;

import com.emt.card.services.UidService;
import com.emt.card.utils.SpringContextHolder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class IdGenerator implements IdentifierGenerator {

    public IdGenerator() {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return SpringContextHolder.getBean(UidService.class).getUID();
    }
}
