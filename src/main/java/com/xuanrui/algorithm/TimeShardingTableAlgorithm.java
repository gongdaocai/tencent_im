package com.xuanrui.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 表分片规则，PreciseShardingAlgorithm精确分片规则适用于=和IN，RangeShardingAlgorithm适用于between and
 * @author XuLuJiao
 * @since 2018年3月7日上午8:47:38
 * @version 1.0.0
 */
public final class TimeShardingTableAlgorithm
		implements PreciseShardingAlgorithm<String>, RangeShardingAlgorithm<String> {

	private final static int MAX_MONTH = 12;
	
	@Override
	public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<String> shardingValue) {
		for (String each : tableNames) {
			if (each.endsWith(String.valueOf(Integer.parseInt(shardingValue.getValue().substring(0, 6))))) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> doSharding(final Collection<String> availableTargetNames,
			final RangeShardingValue<String> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		// 获取年
		int lowerEndpointYear = Integer.parseInt(shardingValue.getValueRange().lowerEndpoint().substring(0, 4));
		int upperEndpointYear = Integer.parseInt(shardingValue.getValueRange().upperEndpoint().substring(0, 4));
		// 获取年月
		int lowerEndpointYearMonth = Integer.parseInt(shardingValue.getValueRange().lowerEndpoint().substring(0, 6));
		int upperEndpointYearMonth = Integer.parseInt(shardingValue.getValueRange().upperEndpoint().substring(0, 6));
		// 获取月
		int lowerEndpointMonth = Integer.parseInt(shardingValue.getValueRange().lowerEndpoint().substring(4, 6));
		int upperEndpointMonth = Integer.parseInt(shardingValue.getValueRange().upperEndpoint().substring(4, 6));
		//计算的年月
		Integer number = 0;
		// sql路由表规则，分两种情况跨年查询和不跨年查询，其中跨年查询分三种情况第一起始时间所在的年的表，第二介于起始年和结束年之间的年的表，最后结束年所在的年的表
		if (upperEndpointYear - lowerEndpointYear == 0) {
			for (int i = lowerEndpointYearMonth; i <= upperEndpointYearMonth; i++) {
				for (String each : availableTargetNames) {
					if (each.endsWith(String.valueOf(i))) {
						result.add(each);
						break;
					}
				}
			}
		} else if (upperEndpointYear - lowerEndpointYear > 0) {
			for (int i = lowerEndpointYear; i <= upperEndpointYear; i++) {
				if (i == lowerEndpointYear) {
					for (String each : availableTargetNames) {
						if (each.contains(String.valueOf(lowerEndpointYear))) {
							for (int j = lowerEndpointMonth; j <= MAX_MONTH; j++) {
								number = i * 100 + j;
								if (each.endsWith(number.toString())) {
									result.add(each);
									break;
								}
							}
						} else {
							break;
						}
					}
				} else if (i > lowerEndpointYear && i < upperEndpointYear) {
					for (String each : availableTargetNames) {
						if (!each.contains(String.valueOf(lowerEndpointYear))
								&& !each.contains(String.valueOf(upperEndpointYear))) {
							for (int j = 1; j <= MAX_MONTH; j++) {
								result.add(each);
							}
						}
					}
				} else if (i == upperEndpointYear) {
					for (String each : availableTargetNames) {
						if (each.contains(String.valueOf(upperEndpointYear))) {
							for (int j = 1; j <= upperEndpointMonth; j++) {
								number = i * 100 + j;
								if (each.endsWith(number.toString())) {
									result.add(each);
									break;
								}
							}
						} else {
							break;
						}
					}
				}
			}
		} else {
			throw new UnsupportedOperationException();
		}
		return result;
	}
}