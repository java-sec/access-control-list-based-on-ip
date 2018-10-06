package cc11001100.acl.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CC11001100
 */
public class IpAcl {

	private List<IpRange> ipRangeList = new ArrayList<>();
	private AclType aclType;

	public IpAcl(AclType aclType) {
		this.aclType = aclType;
	}

	public void add(String line) {
		if (!isComment(line)) {
			ipRangeList.add(new IpRange(line));
		}
	}

	public void add(List<String> lineList) {
		lineList.forEach(this::add);
	}

	/**
	 * 将某个ip从访问控制列表移除，必须完全匹配ipRange才可移除成功
	 *
	 * @param ipRangeNeedToRemove
	 */
	public void remove(String ipRangeNeedToRemove) {
		ipRangeList.removeIf(ipRange -> ipRange.getIpRange().equals(ipRangeNeedToRemove));
	}

	public void readFromConfigFile(String configFilePath) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePath), "UTF-8"));
			String s = null;
			while ((s = reader.readLine()) != null) {
				// 使用#开头的行作为注释
				if (isComment(s)) {
					continue;
				}
				ipRangeList.add(new IpRange(s));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isComment(String s) {
		return s.startsWith("#");
	}

	/**
	 * @param ip 要检查的ip，比如192.168.1.1
	 * @return 是否允许访问，true允许访问，false不允许访问
	 */
	public boolean check(String ip) {
		long ipLong = IpRange.ip2Long(ip);
		switch (aclType) {
			case WHITE_LIST:
				return whiteListCheck(ipLong);
			case BLACK_LIST:
				return blackListCheck(ipLong);
			default:
				throw new IllegalArgumentException("nonsupport type!");
		}
	}

	private boolean blackListCheck(long ipLong) {
		for (IpRange ipRange : ipRangeList) {
			if (ipRange.inRange(ipLong)) {
				return false;
			}
		}
		return true;
	}

	private boolean whiteListCheck(long ipLong) {
		for (IpRange ipRange : ipRangeList) {
			if (ipRange.inRange(ipLong)) {
				return true;
			}
		}
		return false;
	}

	public enum AclType {
		// 白名单
		WHITE_LIST,
		// 黑名单
		BLACK_LIST;
	}

}
