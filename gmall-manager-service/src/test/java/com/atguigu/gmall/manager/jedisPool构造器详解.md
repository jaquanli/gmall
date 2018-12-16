```java
package redis.clients.jedis;

import java.net.URI;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.JedisURIHelper;
import redis.clients.util.Pool;

public class JedisPool extends Pool<Jedis> {
    
    //默认构造器，最简单的连接配置，直接使用，默认使用了localhost连接地址和6379默认端口号
  public JedisPool() {
    this(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
  }
  
  /**
    * 带有设置好的jedis配置构造器，必须自己设置连接地址，默认使用了6379默认端口号、默认超时时间2000毫秒，
    * 默认使用了
    * */
  public JedisPool(final GenericObjectPoolConfig poolConfig, final String host) {
    this(poolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null,
        Protocol.DEFAULT_DATABASE, null);
  }
}
```