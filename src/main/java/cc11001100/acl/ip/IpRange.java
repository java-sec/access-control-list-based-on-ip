package cc11001100.acl.ip;

import java.util.Objects;

import static java.lang.Long.parseLong;

/**
 * 用于ip检查，匹配子网或单个ip
 * <p>
 * 192.168.1.0/24
 * 192.168.1.10
 *
 * @author CC11001100
 */
public class IpRange {

	private String ipRange;
	private long low;
	private long high;

	public IpRange(String ipRange) {
		this.ipRange = ipRange;

		String[] ss = ipRange.split("/");
		String ip = ss[0];
		long ipLong = ip2Long(ip);
		// 单个ip地址，比如192.168.1.15
		if (ss.length <= 1) {
			low = high = ipLong;
		} else {
			// ip网段，比如192.168.1.0/24
			long networkBitLength = parseLong(ss[1]);
			long hostBitLength = 32 - networkBitLength;
			long hostBitSetToOne = (1L << hostBitLength) - 1;
			high = ipLong | hostBitSetToOne;
			low = ipLong & (Long.MAX_VALUE ^ hostBitSetToOne);
		}
	}

	public boolean inRange(String ip) {
		return inRange(ip2Long(ip));
	}

	public boolean inRange(long ipLong) {
		return ipLong >= low && ipLong <= high;
	}

	public static long ip2Long(String ip) {
		String[] ss = ip.split("\\.");
		return (parseLong(ss[0]) << 24) | (parseLong(ss[1]) << 16) | (parseLong(ss[2]) << 8) | parseLong(ss[3]);
	}

	public static String long2Ip(long ipLong) {
		return Long.toString(ipLong >> 24) + "." + Long.toString(ipLong >> 16 & 0XFFL) +
				"." + Long.toString(ipLong >> 8 & 0XFFL) + "." + Long.toString(ipLong & 0XFFL);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IpRange ipRange1 = (IpRange) o;
		return low == ipRange1.low &&
				high == ipRange1.high &&
				Objects.equals(ipRange, ipRange1.ipRange);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ipRange, low, high);
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public long getLow() {
		return low;
	}

	public void setLow(long low) {
		this.low = low;
	}

	public long getHigh() {
		return high;
	}

	public void setHigh(long high) {
		this.high = high;
	}

	@Override
	public String toString() {
		return "IpRange{" +
				"ipRange='" + ipRange + '\'' +
				", low=" + low +
				", high=" + high +
				'}';
	}

}
