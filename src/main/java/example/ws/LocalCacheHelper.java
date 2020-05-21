package example.ws;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @description 模拟本地缓存
 * 
 * 		<p>
 * 		只作为示例，未添加缓存失效以及缓存淘汰机制，实际使用中，可使用redis缓存，如果是非分布式缓存架构，可使用guava的loadingCache来代替
 * 
 * @author 宫清
 * @date 2019年7月20日 下午3:52:41
 * @since JDK1.7
 */
public class LocalCacheHelper {

	private static final Map<String, Object> localCache = Maps.newConcurrentMap();

	
	/**
	 *  不允许外部创建实例
	 */
	private LocalCacheHelper() {
	}

	/**
	 * @description 添加元素
	 *
	 * @param k
	 * @param v
	 * @author 宫清
	 * @date 2019年7月20日 下午3:52:56
	 */
	public static void put(String k, Object v) {
		localCache.put(k, v);
	}
	
	/**
	 * @description 获取元素
	 *
	 * @param k
	 * @return
	 * @author 宫清
	 * @date 2019年7月20日 下午3:53:04
	 */
	public static Object get(String k) {
		return localCache.get(k);
	}
	
	/**
	 * @description 移除元素
	 *
	 * @param k
	 * @author 宫清
	 * @date 2019年7月20日 下午3:55:54
	 */
	public static void remove(String k) {
		localCache.remove(k);
	}
	

}
