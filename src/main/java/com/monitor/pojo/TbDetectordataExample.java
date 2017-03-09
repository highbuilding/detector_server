package com.monitor.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbDetectordataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbDetectordataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidIsNull() {
            addCriterion("device_mainid is null");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidIsNotNull() {
            addCriterion("device_mainid is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidEqualTo(Integer value) {
            addCriterion("device_mainid =", value, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidNotEqualTo(Integer value) {
            addCriterion("device_mainid <>", value, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidGreaterThan(Integer value) {
            addCriterion("device_mainid >", value, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_mainid >=", value, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidLessThan(Integer value) {
            addCriterion("device_mainid <", value, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidLessThanOrEqualTo(Integer value) {
            addCriterion("device_mainid <=", value, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidIn(List<Integer> values) {
            addCriterion("device_mainid in", values, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidNotIn(List<Integer> values) {
            addCriterion("device_mainid not in", values, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidBetween(Integer value1, Integer value2) {
            addCriterion("device_mainid between", value1, value2, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMainidNotBetween(Integer value1, Integer value2) {
            addCriterion("device_mainid not between", value1, value2, "deviceMainid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridIsNull() {
            addCriterion("device_minorid is null");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridIsNotNull() {
            addCriterion("device_minorid is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridEqualTo(Integer value) {
            addCriterion("device_minorid =", value, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridNotEqualTo(Integer value) {
            addCriterion("device_minorid <>", value, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridGreaterThan(Integer value) {
            addCriterion("device_minorid >", value, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_minorid >=", value, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridLessThan(Integer value) {
            addCriterion("device_minorid <", value, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridLessThanOrEqualTo(Integer value) {
            addCriterion("device_minorid <=", value, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridIn(List<Integer> values) {
            addCriterion("device_minorid in", values, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridNotIn(List<Integer> values) {
            addCriterion("device_minorid not in", values, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridBetween(Integer value1, Integer value2) {
            addCriterion("device_minorid between", value1, value2, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andDeviceMinoridNotBetween(Integer value1, Integer value2) {
            addCriterion("device_minorid not between", value1, value2, "deviceMinorid");
            return (Criteria) this;
        }

        public Criteria andCurrentIsNull() {
            addCriterion("current is null");
            return (Criteria) this;
        }

        public Criteria andCurrentIsNotNull() {
            addCriterion("current is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentEqualTo(Float value) {
            addCriterion("current =", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentNotEqualTo(Float value) {
            addCriterion("current <>", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentGreaterThan(Float value) {
            addCriterion("current >", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentGreaterThanOrEqualTo(Float value) {
            addCriterion("current >=", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentLessThan(Float value) {
            addCriterion("current <", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentLessThanOrEqualTo(Float value) {
            addCriterion("current <=", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentIn(List<Float> values) {
            addCriterion("current in", values, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentNotIn(List<Float> values) {
            addCriterion("current not in", values, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentBetween(Float value1, Float value2) {
            addCriterion("current between", value1, value2, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentNotBetween(Float value1, Float value2) {
            addCriterion("current not between", value1, value2, "current");
            return (Criteria) this;
        }

        public Criteria andVoltageIsNull() {
            addCriterion("voltage is null");
            return (Criteria) this;
        }

        public Criteria andVoltageIsNotNull() {
            addCriterion("voltage is not null");
            return (Criteria) this;
        }

        public Criteria andVoltageEqualTo(Float value) {
            addCriterion("voltage =", value, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageNotEqualTo(Float value) {
            addCriterion("voltage <>", value, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageGreaterThan(Float value) {
            addCriterion("voltage >", value, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageGreaterThanOrEqualTo(Float value) {
            addCriterion("voltage >=", value, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageLessThan(Float value) {
            addCriterion("voltage <", value, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageLessThanOrEqualTo(Float value) {
            addCriterion("voltage <=", value, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageIn(List<Float> values) {
            addCriterion("voltage in", values, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageNotIn(List<Float> values) {
            addCriterion("voltage not in", values, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageBetween(Float value1, Float value2) {
            addCriterion("voltage between", value1, value2, "voltage");
            return (Criteria) this;
        }

        public Criteria andVoltageNotBetween(Float value1, Float value2) {
            addCriterion("voltage not between", value1, value2, "voltage");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNull() {
            addCriterion("temperature is null");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNotNull() {
            addCriterion("temperature is not null");
            return (Criteria) this;
        }

        public Criteria andTemperatureEqualTo(Float value) {
            addCriterion("temperature =", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotEqualTo(Float value) {
            addCriterion("temperature <>", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThan(Float value) {
            addCriterion("temperature >", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThanOrEqualTo(Float value) {
            addCriterion("temperature >=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThan(Float value) {
            addCriterion("temperature <", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThanOrEqualTo(Float value) {
            addCriterion("temperature <=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureIn(List<Float> values) {
            addCriterion("temperature in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotIn(List<Float> values) {
            addCriterion("temperature not in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureBetween(Float value1, Float value2) {
            addCriterion("temperature between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotBetween(Float value1, Float value2) {
            addCriterion("temperature not between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNull() {
            addCriterion("frequency is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNotNull() {
            addCriterion("frequency is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyEqualTo(Float value) {
            addCriterion("frequency =", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotEqualTo(Float value) {
            addCriterion("frequency <>", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThan(Float value) {
            addCriterion("frequency >", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThanOrEqualTo(Float value) {
            addCriterion("frequency >=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThan(Float value) {
            addCriterion("frequency <", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThanOrEqualTo(Float value) {
            addCriterion("frequency <=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyIn(List<Float> values) {
            addCriterion("frequency in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotIn(List<Float> values) {
            addCriterion("frequency not in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyBetween(Float value1, Float value2) {
            addCriterion("frequency between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotBetween(Float value1, Float value2) {
            addCriterion("frequency not between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andApparentPowerIsNull() {
            addCriterion("apparent_power is null");
            return (Criteria) this;
        }

        public Criteria andApparentPowerIsNotNull() {
            addCriterion("apparent_power is not null");
            return (Criteria) this;
        }

        public Criteria andApparentPowerEqualTo(Float value) {
            addCriterion("apparent_power =", value, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerNotEqualTo(Float value) {
            addCriterion("apparent_power <>", value, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerGreaterThan(Float value) {
            addCriterion("apparent_power >", value, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerGreaterThanOrEqualTo(Float value) {
            addCriterion("apparent_power >=", value, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerLessThan(Float value) {
            addCriterion("apparent_power <", value, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerLessThanOrEqualTo(Float value) {
            addCriterion("apparent_power <=", value, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerIn(List<Float> values) {
            addCriterion("apparent_power in", values, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerNotIn(List<Float> values) {
            addCriterion("apparent_power not in", values, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerBetween(Float value1, Float value2) {
            addCriterion("apparent_power between", value1, value2, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andApparentPowerNotBetween(Float value1, Float value2) {
            addCriterion("apparent_power not between", value1, value2, "apparentPower");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNull() {
            addCriterion("created is null");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNotNull() {
            addCriterion("created is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedEqualTo(Date value) {
            addCriterion("created =", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotEqualTo(Date value) {
            addCriterion("created <>", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThan(Date value) {
            addCriterion("created >", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("created >=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThan(Date value) {
            addCriterion("created <", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThanOrEqualTo(Date value) {
            addCriterion("created <=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedIn(List<Date> values) {
            addCriterion("created in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotIn(List<Date> values) {
            addCriterion("created not in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedBetween(Date value1, Date value2) {
            addCriterion("created between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotBetween(Date value1, Date value2) {
            addCriterion("created not between", value1, value2, "created");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}