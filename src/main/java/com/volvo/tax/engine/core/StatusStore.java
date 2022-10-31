package com.volvo.tax.engine.core;

import com.volvo.tax.engine.rule.AbstractRuleWIthStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * StatusStore stores the status informatation to be accessed by rules which implements
 * {@link AbstractRuleWIthStatus}
 *
 * <p>Key of the map will be the rule name, and the value type will be the target type of the
 * {@link AbstractRuleWIthStatus}</p>
 *
 * @author Lasith Perera
 */
public class StatusStore {

    private final Map<String, Object> statusMap = new HashMap<>();

    public void add(String key, Object status) {
        statusMap.put(key, status);
    }

    public Object get(String key) {
        return statusMap.get(key);
    }

}
