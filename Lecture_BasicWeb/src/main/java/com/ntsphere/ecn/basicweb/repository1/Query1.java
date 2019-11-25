package com.ntsphere.ecn.basicweb.repository1;

import java.util.HashMap;

import org.springframework.stereotype.Repository;


@Repository("query1")
public interface Query1 {
	public HashMap<String, Object> selectTest(HashMap<String, Object> param);
}
