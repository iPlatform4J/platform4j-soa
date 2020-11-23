package com.platform4j.soa.dao.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisObjectDao {
    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean existsKey(final String key);

    /**
     * 计数
     *
     * @param key
     * @param count
     * @return
     */
    public Long incrBy(final String key, int count);

    /**
     * 删除模糊匹配的key
     *
     * @param likeKey 模糊匹配的key
     * @return 删除成功的条数
     */
    public long delKeysLike(final String likeKey);

    /**
     * 删除
     *
     * @param key 匹配的key
     * @return 删除成功的条数
     */
    public Long delKey(final String key);

    /**
     * 删除
     *
     * @param keys 匹配的key的集合
     * @return 删除成功的条数
     */
    public Long delKeys(final String[] keys);

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * 在 Redis 中，带有生存时间的 key 被称为『可挥发』(volatile)的。
     *
     * @param key    key
     * @param expire 生命周期，单位为秒
     * @return 1: 设置成功 0: 已经超时或key不存在
     */
    public Long expire(final String key, final int expire);

    /**
     * 一个跨jvm的id生成器，利用了redis原子性操作的特点
     *
     * @param key id的key
     * @return 返回生成的Id
     */
    public long makeId(final String key);

    /* ======================================Strings====================================== */

    /**
     * 将字符串值 value 关联到 key 。
     * 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当 setString 成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     * 时间复杂度：O(1)
     *
     * @param key   key
     * @param value string value
     * @return 在设置操作成功完成时，才返回 OK 。
     */
    public String setString(final String key, final String value);

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 expire (以秒为单位)。
     * 如果 key 已经存在， 将覆写旧值。
     * 类似于以下两个命令:
     * SET key value
     * EXPIRE key expire # 设置生存时间
     * 不同之处是这个方法是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，在 Redis 用作缓存时，非常实用。
     * 时间复杂度：O(1)
     *
     * @param key    key
     * @param value  string value
     * @param expire 生命周期
     * @return 设置成功时返回 OK 。当 expire 参数不合法时，返回一个错误。
     */
    public String setString(final String key, final String value, final int expire);

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。
     * 时间复杂度：O(1)
     *
     * @param key   key
     * @param value string value
     * @return 设置成功，返回 1 。设置失败，返回 0 。
     */
    public Long setStringIfNotExists(final String key, final String value);

    /**
     * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。
     * 假如 key 储存的值不是字符串类型，返回一个错误，因为 getString 只能用于处理字符串值。
     * 时间复杂度: O(1)
     *
     * @param key key
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
     */
    public String getString(final String key);

    /**
     * 批量的 {@link #setString(String, String)}
     *
     * @param pairs 键值对数组{数组第一个元素为key，第二个元素为value}
     * @return 操作状态的集合
     */
    public List<Object> batchSetString(final List<Pair<String, String>> pairs);

    /**
     * 批量的 {@link #getString(String)}
     *
     * @param keys key数组
     * @return value的集合
     */
    public List<String> batchGetString(final String[] keys);

    public <K, V> Pair<K, Object> makePair(K key, Object value);

    /* ======================================Hashes====================================== */


    /* ======================================Hashes====================================== */

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     * 时间复杂度: O(1)
     *
     * @param key   key
     * @param field 域
     * @param value string value
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     */
    public Long hashSet(final String key, final String field, final String value);

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *
     * @param key    key
     * @param field  域
     * @param value  string value
     * @param expire 生命周期，单位为秒
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     */
    public Long hashSet(final String key, final String field, final String value, final int expire);

    /**
     * 返回哈希表 key 中给定域 field 的值。
     * 时间复杂度:O(1)
     *
     * @param key   key
     * @param field 域
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
     */
    public String hashGet(final String key, final String field);

    /**
     * 返回哈希表 key 中给定域 field 的值。 如果哈希表 key 存在，同时设置这个 key 的生存时间
     *
     * @param key    key
     * @param field  域
     * @param expire 生命周期，单位为秒
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
     */
    public String hashGet(final String key, final String field, final int expire);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 时间复杂度: O(N) (N为fields的数量)
     *
     * @param key  key
     * @param hash field-value的map
     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
     */
    public String hashMultipleSet(final String key, final Map<String, String> hash);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。同时设置这个 key 的生存时间
     *
     * @param key    key
     * @param hash   field-value的map
     * @param expire 生命周期，单位为秒
     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
     */
    public String hashMultipleSet(final String key, final Map<String, String> hash, final int expire);


    /**
     * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。
     * 时间复杂度: O(N) (N为fields的数量)
     *
     * @param key    key
     * @param fields field的数组
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    public List<String> hashMultipleGet(final String key, final String... fields);

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。
     * 同时设置这个 key 的生存时间
     *
     * @param key    key
     * @param fields field的数组
     * @param expire 生命周期，单位为秒
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    public List<String> hashMultipleGet(final String key, final int expire, final String... fields);

    /**
     * 批量的{@link #hashMultipleSet(String, Map)}，在管道中执行
     *
     * @param pairs 多个hash的多个field
     * @return 操作状态的集合
     */
    public List<Object> batchHashMultipleSet(final List<Pair<String, Map<String, String>>> pairs);


    /**
     * 批量的{@link #hashMultipleSet(String, Map)}，在管道中执行
     *
     * @param data Map<String, Map<String, String>>格式的数据
     * @return 操作状态的集合
     */
    public List<Object> batchHashMultipleSet(final Map<String, Map<String, String>> data);

    /**
     * 批量的{@link #hashMultipleGet(String, String...)}，在管道中执行
     *
     * @param pairs 多个hash的多个field
     * @return 执行结果的集合
     */
    public List<List<String>> batchHashMultipleGet(final List<Pair<String, String[]>> pairs);

    /**
     * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     * 时间复杂度: O(N)
     *
     * @param key key
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    public Map<String, String> hashGetAll(final String key);

    /**
     * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     * 同时设置这个 key 的生存时间
     *
     * @param key    key
     * @param expire 生命周期，单位为秒
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    public Map<String, String> hashGetAll(final String key, final int expire);

    /**
     * 批量的{@link #hashGetAll(String)}
     *
     * @param keys key的数组
     * @return 执行结果的集合
     */
    public List<Map<String, String>> batchHashGetAll(final String... keys);

    /**
     * 批量的{@link #hashMultipleGet(String, String...)}，与{@link #batchHashGetAll(String...)}不同的是，返回值为Map类型
     *
     * @param keys key的数组
     * @return 多个hash的所有filed和value
     */
    public Map<String, Map<String, String>> batchHashGetAllForMap(final String... keys);

    public Long sadd(final String key, final List<String> list);

    public Set<String> smembers(final String key);

    /**
     * 尝试获取redis锁
     * @param key
     * @param value
     * @return
     */
    public boolean tryDistributedLock(String key, String value, long time);

    /**
     * 删除redis锁
     * @param key
     * @return
     */
    public boolean removeDistributedLock(String key, String value);
}
