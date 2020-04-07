package com.emt.card.config;

import org.hibernate.dialect.MySQL5Dialect;


public class MysqlConfig extends MySQL5Dialect{
	@Override
    public String getTableTypeString() {
        return " ENGINE=MyISAM DEFAULT CHARSET=utf8";
    }
}
