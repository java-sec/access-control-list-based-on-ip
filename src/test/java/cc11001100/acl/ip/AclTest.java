package cc11001100.acl.ip;

import java.util.Arrays;
import java.util.Random;

/**
 * @author CC11001100
 */
public class AclTest {

	private static void blackListTest() {
		IpAcl ipAcl = new IpAcl(IpAcl.AclType.BLACK_LIST);
		ipAcl.readFromConfigFile("ip-list");

		System.out.println(ipAcl.check("192.168.0.0"));
		System.out.println(ipAcl.check("192.168.1.0"));
		System.out.println(ipAcl.check("192.168.1.255"));
		System.out.println(ipAcl.check("192.168.2.1"));
		System.out.println(ipAcl.check("8.8.8.8"));
	}

	private static void whiteListTest() {
		IpAcl ipAcl = new IpAcl(IpAcl.AclType.WHITE_LIST);
		ipAcl.readFromConfigFile("ip-list");

		System.out.println(ipAcl.check("192.168.0.0"));
		System.out.println(ipAcl.check("192.168.1.0"));
		System.out.println(ipAcl.check("192.168.1.255"));
		System.out.println(ipAcl.check("192.168.2.1"));
		System.out.println(ipAcl.check("8.8.8.8"));
	}

	// 百万次检测花费约 822 ms
	private static void speedTest() {
		IpAcl ipAcl = new IpAcl(IpAcl.AclType.WHITE_LIST);
		ipAcl.readFromConfigFile("ip-list");

		Random random = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			String ip = random.nextInt(255) + "." + random.nextInt(255)
					+ "." + random.nextInt(255) + "." + random.nextInt(255);
			boolean ok = ipAcl.check(ip);
//			System.out.println(ip + " : " + ok);
		}
		long end = System.currentTimeMillis();
		long cost = end - start;
		System.out.println(cost + " ms");
	}

	private static void usage() {
		// 黑名单
//		IpAcl acl = new IpAcl(IpAcl.AclType.BLACK_LIST);
		// 白名单
		IpAcl acl = new IpAcl(IpAcl.AclType.WHITE_LIST);

		// 从配置文件初始化访问控制列表
		acl.readFromConfigFile("ip-list");
		// 手动添加，以追加的形式添加
		acl.add("192.168.2.15");
		acl.add(Arrays.asList("192.168.2.15", "192.168.1.20"));

		// 检查ip是否允许访问
		System.out.println(acl.check("192.168.2.15")); // true

		// 从访问控制列表中移除ip
		acl.remove("192.168.2.15");

		// 检查ip是否允许访问
		System.out.println(acl.check("192.168.2.15")); // false
	}

	public static void main(String[] args) {
//		blackListTest();
//		whiteListTest();
//		speedTest();
		usage();
	}

}
