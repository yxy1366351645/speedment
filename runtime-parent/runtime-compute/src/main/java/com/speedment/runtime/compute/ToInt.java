package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.trait.HasAbs;
import com.speedment.runtime.compute.trait.HasAsDouble;
import com.speedment.runtime.compute.trait.HasAsInt;
import com.speedment.runtime.compute.trait.HasAsLong;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasDivide;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMinus;
import com.speedment.runtime.compute.trait.HasMultiply;
import com.speedment.runtime.compute.trait.HasNegate;
import com.speedment.runtime.compute.trait.HasPlus;
import com.speedment.runtime.compute.trait.HasPow;
import com.speedment.runtime.compute.trait.HasSign;
import com.speedment.runtime.compute.trait.HasSqrt;

import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

/**
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToInt<T>
extends Expression,
        ToIntFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasAbs<ToInt<T>>,
        HasSign<ToByte<T>>,
        HasSqrt<ToDouble<T>>,
        HasNegate<ToInt<T>>,
        HasPow<T, ToLong<T>, ToDouble<T>>,
        HasPlus<T, ToInt<T>, ToInt<T>, ToLong<T>>,
        HasMinus<T, ToInt<T>, ToInt<T>, ToLong<T>>,
        HasMultiply<T, ToLong<T>, ToLong<T>, ToLong<T>>,
        HasDivide<T, ToInt<T>>,
        HasHash<T>,
        HasCompare<T> {

    int applyAsInt(T object);

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.INT;
    }

    @Override
    default ToDouble<T> asDouble() {
        return this::applyAsInt;
    }

    @Override
    default ToInt<T> asInt() {
        return this;
    }

    @Override
    default ToLong<T> asLong() {
        return this::applyAsInt;
    }

    default ToDouble<T> mapToDouble(IntToDoubleFunction operator) {
        return object -> operator.applyAsDouble(applyAsInt(object));
    }

    default ToInt<T> map(IntUnaryOperator operator) {
        return t -> operator.applyAsInt(applyAsInt(t));
    }

    @Override
    default ToInt<T> abs() {
        return Expressions.abs(this);
    }

    @Override
    default ToByte<T> sign() {
        return Expressions.sign(this);
    }

    @Override
    default ToDouble<T> sqrt() {
        return Expressions.sqrt(this);
    }

    @Override
    default ToLong<T> pow(byte power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> pow(int power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(double power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> pow(ToByte<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> pow(ToInt<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(ToDouble<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToInt<T> plus(byte other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToInt<T> plus(ToByte<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToInt<T> plus(int other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToInt<T> plus(ToInt<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(long other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(ToLong<T> other) {
        return Expressions.plus(this.asLong(), other);
    }

    @Override
    default ToDouble<T> plus(double other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(ToDouble<T> other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToInt<T> minus(byte other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToInt<T> minus(ToByte<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToInt<T> minus(int other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToInt<T> minus(ToInt<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(long other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(ToLong<T> other) {
        return Expressions.minus(this.asLong(), other);
    }

    @Override
    default ToDouble<T> minus(double other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(ToDouble<T> other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToLong<T> multiply(byte other) {
        return Expressions.multiply(this.asLong(), other);
    }

    @Override
    default ToLong<T> multiply(ToByte<T> other) {
        return Expressions.multiply(this.asLong(), other.asLong());
    }

    @Override
    default ToLong<T> multiply(int other) {
        return Expressions.multiply(this.asLong(), other);
    }

    @Override
    default ToLong<T> multiply(ToInt<T> other) {
        return Expressions.multiply(this.asLong(), other);
    }

    @Override
    default ToLong<T> multiply(long other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToLong<T> multiply(ToLong<T> other) {
        return Expressions.multiply(this.asLong(), other);
    }

    @Override
    default ToDouble<T> multiply(double other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(ToDouble<T> other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToInt<T> divideFloor(byte divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToInt<T> divideFloor(ToByte<T> divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToInt<T> divideFloor(int divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToInt<T> divideFloor(ToInt<T> divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToInt<T> divideFloor(long divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToInt<T> divideFloor(ToLong<T> divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToDouble<T> divide(byte divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToByte<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(int divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToInt<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(long divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToLong<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(double divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToDouble<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToInt<T> negate() {
        return Expressions.negate(this);
    }

    @Override
    default long hash(T object) {
        return applyAsInt(object);
    }

    @Override
    default int compare(T first, T second) {
        return Integer.compare(
            applyAsInt(first),
            applyAsInt(second)
        );
    }
}