package com.xuanrui.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 数据源分片规则，PreciseShardingAlgorithm精确分片规则适用于=和IN，RangeShardingAlgorithm适用于between
 * @author XuLuJiao
 * @since 2018年3月7日上午8:45:54
 * @version 1.0.0
 */
public final class TimeShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<String>, RangeShardingAlgorithm<String> {

	@Override
	public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<String> shardingValue) {
		for (String each : tableNames) {
			if (each.endsWith(shardingValue.getValue().substring(0, 4))) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> doSharding(final Collection<String> availableTargetNames,
			final RangeShardingValue<String> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		int lowerEndpoint = Integer.parseInt(shardingValue.getValueRange().lowerEndpoint().substring(0, 4));
		int upperEndpoint = Integer.parseInt(shardingValue.getValueRange().upperEndpoint().substring(0, 4));
		//根据传入的时间遍历库名找到对应的库，防止全库查询
		for (int i = lowerEndpoint; i <= upperEndpoint; i++) {
			for (String each : availableTargetNames) {
				if (each.endsWith(String.valueOf(i))) {
					result.add(each);
					break;
				}
			}
		}
		return result;
	}

}
