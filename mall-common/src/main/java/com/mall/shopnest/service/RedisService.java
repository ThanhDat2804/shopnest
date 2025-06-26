package com.mall.shopnest.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    /**
     * Save a key-value pair with expiration time
     */
    void set(String key, Object value, long time);

    /**
     * Save a key-value pair without expiration
     */
    void set(String key, Object value);

    /**
     * Get a value by key
     */
    Object get(String key);

    /**
     * Delete a key
     */
    Boolean del(String key);

    /**
     * Delete multiple keys
     */
    Long del(List<String> keys);

    /**
     * Set expiration time for a key
     */
    Boolean expire(String key, long time);

    /**
     * Get remaining expiration time of a key
     */
    Long getExpire(String key);

    /**
     * Check if a key exists
     */
    Boolean hasKey(String key);

    /**
     * Increment a value by delta
     */
    Long incr(String key, long delta);

    /**
     * Decrement a value by delta
     */
    Long decr(String key, long delta);

    /**
     * Get a field from a Hash
     */
    Object hGet(String key, String hashKey);

    /**
     * Set a field in a Hash with expiration time
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * Set a field in a Hash without expiration
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * Get all fields in a Hash
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * Set all fields in a Hash with expiration time
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * Set all fields in a Hash without expiration
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * Delete one or more fields from a Hash
     */
    void hDel(String key, Object... hashKey);

    /**
     * Check if a field exists in a Hash
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Increment a field value in a Hash
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Decrement a field value in a Hash
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * Get all members of a Set
     */
    Set<Object> sMembers(String key);

    /**
     * Add one or more members to a Set
     */
    Long sAdd(String key, Object... values);

    /**
     * Add one or more members to a Set with expiration time
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * Check if a value exists in a Set
     */
    Boolean sIsMember(String key, Object value);

    /**
     * Get the size of a Set
     */
    Long sSize(String key);

    /**
     * Remove one or more members from a Set
     */
    Long sRemove(String key, Object... values);

    /**
     * Get a range of elements from a List
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * Get the size of a List
     */
    Long lSize(String key);

    /**
     * Get an element from a List by index
     */
    Object lIndex(String key, long index);

    /**
     * Push an element to the left of a List
     */
    Long lPush(String key, Object value);

    /**
     * Push an element to the left of a List with expiration time
     */
    Long lPush(String key, Object value, long time);

    /**
     * Push multiple elements to the left of a List
     */
    Long lPushAll(String key, Object... values);

    /**
     * Push multiple elements to the left of a List with expiration time
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * Remove elements from a List
     */
    Long lRemove(String key, long count, Object value);
}
