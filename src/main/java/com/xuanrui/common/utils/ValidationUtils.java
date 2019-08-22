package com.xuanrui.common.utils;

import com.xuanrui.common.core.model.result.BizException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * @Description: validate
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
public class ValidationUtils {
	/**
	 * 使用hibernate的注解来进行验证
	 * 
	 */
	private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory()
			.getValidator();

	/**
	 * 功能描述: <br>
	 * 〈注解验证参数〉
	 *
	 * @param obj
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static <T> void validate(T obj) throws BizException {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
		// 抛出检验异常
		if (constraintViolations.size() > 0) {
            throw new BizException(constraintViolations.iterator().next().getMessage());
		}
	}

	/**
	 * 校验字段错误
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> Map<String, String> validateParam(T obj) {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
		Map<String, String> errorMessage = new HashMap<>();
		if (constraintViolations.size() > 0) {
			constraintViolations.forEach(t -> {
				errorMessage.put("msg", t.getMessage());
				errorMessage.put("field", t.getPropertyPath().toString());
			});
		}
		if (errorMessage.size() > 0) {
			return errorMessage;
		} else {
			Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, String> customObjErrorMessage;
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					if (field.getType().getName().startsWith("com.xuanrui")) {
						customObjErrorMessage = validateParam(field.get(obj));
						if (customObjErrorMessage != null && customObjErrorMessage.size() > 0) {
							return customObjErrorMessage;
						}
					} else if (field.get(obj) instanceof Iterable) {
						Iterable<?> iterable = (Iterable<?>) field.get(obj);
                        for (Object entity : iterable) {
                            if (!entity.getClass().getName().startsWith("com.xuanrui")) break;
							customObjErrorMessage = validateParam(entity);
							if (customObjErrorMessage != null && customObjErrorMessage.size() > 0) {
								return customObjErrorMessage;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}