package com.ntsphere.ecn.basicweb.repository2;

import java.util.HashMap;

import org.springframework.stereotype.Repository;


@Repository("query2")
public interface Query2 {
	public HashMap<String, Object> selectTest(HashMap<String, Object> param);
}
