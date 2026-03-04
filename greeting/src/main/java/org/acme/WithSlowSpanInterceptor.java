package org.acme;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import io.opentelemetry.context.Context;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Instance;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

@Priority(2020)
@Interceptor
@WithSlowSpan
class WithSlowSpanInterceptor {
    private final Instance<OpenTelemetry> instance;
    private final Tracer tracer;

    public WithSlowSpanInterceptor(Instance<OpenTelemetry> instance, Tracer tracer) {
        this.instance = instance;
        this.tracer = tracer;
    }

    @AroundInvoke
    public Object addSpan(InvocationContext invocationContext) throws Exception {
        final var minimumDurationInMs = invocationContext.getMethod().getAnnotation(WithSlowSpan.class)
                .minimumDurationInMs();
        final var startTime = Instant.now();

        try {
            return invocationContext.proceed();
        } finally {
            if (getOpenTelemetry().isPresent()) {
                Duration duration = Duration.between(startTime, Instant.now());
                if (duration.toMillis() > minimumDurationInMs) {
                    tracer.spanBuilder(invocationContext.getMethod().getDeclaringClass().getSimpleName() + "." + invocationContext.getMethod().getName())
                            .setParent(Context.current())
                            .setStartTimestamp(startTime)
                            .startSpan()
                            .end();
                }
            }
        }
    }

    private Optional<OpenTelemetry> getOpenTelemetry() {
        return instance.isResolvable() ? Optional.of(instance.get()) : Optional.empty();
    }

}