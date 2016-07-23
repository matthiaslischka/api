package io.nobt.profiles.spi;

import io.nobt.profiles.ActiveProfileEvaluator;

import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ActiveProfileEvaluatorLoader {

    public static ActiveProfileEvaluator load() {

        final List<ActiveProfileEvaluator> evaluators = StreamSupport
                .stream(ServiceLoader.load(ActiveProfileEvaluator.class).spliterator(), false)
                .collect(Collectors.toList());

        Collections.sort(evaluators);

        return evaluators.stream().findFirst().orElseThrow( () -> new ActiveProfileEvaluatorLoaderException("No ActiveProfileEvaluator was found."));
    }
}