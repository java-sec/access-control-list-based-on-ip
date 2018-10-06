## 基于ip的访问控制（ip黑名单、ip白名单）

### Usage:  
1. maven：  
    - add repo:
    ```text
    <repositories>
        <repository>
            <id>maven-repo-cc11001100</id>
            <url>https://raw.github.com/cc11001100/maven-repo/access-control-list-based-on-ip/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
    ```
    - add dependency:
    ```text
    <dependency>
        <groupId>cc11001100</groupId>
        <artifactId>access-control-list-based-on-ip</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ```
2. code:
    ```text
    // 黑名单
    //IpAcl acl = new IpAcl(IpAcl.AclType.BLACK_LIST);
    // 白名单
    IpAcl acl = new IpAcl(IpAcl.AclType.WHITE_LIST);
    
    // 从配置文件初始化访问控制列表
    acl.readFromConfigFile("ip-list");
    // 手动添加，以追加的形式添加
    acl.add("192.168.10.0/24");
    acl.add(Arrays.asList("192.168.2.15", "192.168.3.0/24"));
    
    // 检查ip是否允许访问
    System.out.println(acl.check("192.168.2.15")); // true
    
    // 从访问控制列表中移除ip
    acl.remove("192.168.2.15");
    
    // 检查ip是否允许访问
    System.out.println(acl.check("192.168.2.15")); // false
    ```
