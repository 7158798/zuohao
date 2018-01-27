package com.base.wallet.utils;

import java.sql.Timestamp;
import java.util.Date;

public class Utils {
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}
}
