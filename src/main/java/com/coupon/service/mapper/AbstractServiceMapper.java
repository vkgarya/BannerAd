package com.coupon.service.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

/**
 * Common mapping methods.
 */
public abstract class AbstractServiceMapper {

    /**
     * Get ModelMapper.
     *
     * @return modelMapper
     */
    protected abstract ModelMapper getModelMapper();

    /**
     * Map input bean to a new output bean.
     *
     * @param input       Input bean
     * @param outputClass Output bean class
     * @return New output bean
     */
    protected <I, O> O map(final I input, final Class<O> outputClass) {
        return getModelMapper().map(input, outputClass);
    }

    /**
     * Map input bean to an existing output bean.
     *
     * @param input  Input bean
     * @param output Output bean
     */
    protected <I, O> void map(final I input, final O output) {
        getModelMapper().map(input, output);
    }

    /**
     * Map input beans to new output beans.
     *
     * @param inputs      Input beans
     * @param outputClass Output beans class
     * @return New output beans
     */
    protected <I, O> List<O> map(final Collection<I> inputs, final Class<O> outputClass) {
        List<O> outputs = new ArrayList<O>();
        for (I input : inputs) {
            O output = map(input, outputClass);
            outputs.add(output);
        }
        return outputs;
    }

    /**
     * Map input beans to existing output beans.
     *
     * @param inputs      Input beans
     * @param outputs     Output beans
     * @param outputClass Output beans class
     */
    protected <I, O> void map(final Collection<I> inputs, final Collection<O> outputs, final Class<O> outputClass) {
        for (I input : inputs) {
            O output = map(input, outputClass);
            outputs.add(output);
        }
    }

}
