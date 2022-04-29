package com.gr.learningpath.exceptions.translator;

import com.gr.learningpath.api.ErrorResponse;
import com.gr.learningpath.exceptions.EntityAlreadyExistsException;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.exceptions.ErrorCode;
import org.dom4j.tree.AbstractEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.gr.learningpath.exceptions.ErrorCode.VALIDATION_FAILED;
import static com.gr.learningpath.exceptions.ErrorConstants.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;
import static org.zalando.problem.Status.BAD_REQUEST;

@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private final Map<Class<? extends AbstractEntity>, Integer> notFoundErrorCodes;
    private final Map<Class<? extends AbstractEntity>, Integer> alreadyExistsErrorCodes;

    public ExceptionTranslator() {
        this.notFoundErrorCodes = new HashMap<>();
        this.alreadyExistsErrorCodes = new HashMap<>();

    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    ResponseEntity<Problem> handleException(final EntityAlreadyExistsException exception, final NativeWebRequest request) {
        ProblemBuilder builder = Problem.builder()
                .withTitle(BAD_REQUEST.getReasonPhrase())
                .withStatus(BAD_REQUEST)
                .withDetail(exception.getMessage())
                .with(ERROR_KEY, alreadyExistsErrorCodes.get(exception.getClazz()));
        return create(exception, builder.build(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Problem> handleException(final EntityNotFoundException exception, final NativeWebRequest request) {
        ProblemBuilder builder = Problem.builder()
                .withTitle(BAD_REQUEST.getReasonPhrase())
                .withStatus(BAD_REQUEST)
                .withDetail(exception.getMessage())
                .with(ERROR_KEY, notFoundErrorCodes.get(exception.getClazz()));
        return create(exception, builder.build(), request);
    }


//    @ExceptionHandler(UsernameAlreadyExistsException.class)
//    ResponseEntity<Problem> handleException(final UsernameAlreadyExistsException exception, final NativeWebRequest request) {
//        ProblemBuilder builder = Problem.builder()
//                .withTitle(BAD_REQUEST.getReasonPhrase())
//                .withStatus(BAD_REQUEST)
//                .withDetail(exception.getMessage())
//                .with(ERROR_KEY, USERNAME_ALREADY_EXIST);
//        return create(exception, builder.build(), request);
//    }


    @SuppressWarnings("All")
    @Override
    public ResponseEntity process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {

        if (entity == null) {
            return null;
        }

        Problem problem = entity.getBody();
        if (!(problem instanceof DefaultProblem || problem instanceof ConstraintViolationProblem)) {
            return new ResponseEntity(
                    ErrorResponse.builder().error(entity.getBody()).build(),
                    entity.getHeaders(),
                    entity.getStatusCode()
            );
        }
        ProblemBuilder builder = Problem.builder()
                .withType(fromUri(PROBLEM_BASE_URI).path("/" + resolveType(problem)).build().toUri())
                .withInstance(fromCurrentRequest().build().toUri())
                .withTitle(problem.getTitle())
                .withStatus(problem.getStatus())
                .withDetail(problem.getDetail());

        if (problem instanceof ConstraintViolationProblem) {
            builder.with(VIOLATION_KEY, ((ConstraintViolationProblem) problem).getViolations())
                    .with(ERROR_KEY, VALIDATION_FAILED);
        } else {
            builder.withCause(((DefaultProblem) problem).getCause());
            problem.getParameters().forEach(builder::with);
        }

        return new ResponseEntity(
                ErrorResponse.builder().error(builder.build()).build(),
                entity.getHeaders(),
                entity.getStatusCode());
    }

    private int resolveType(@Nonnull final Problem problem) {
        if (!problem.getParameters().containsKey(ERROR_KEY) && problem.getStatus() != null) {
            return problem.getStatus().getStatusCode();
        } else {
            try {
                return ((ErrorCode) problem.getParameters().get(ERROR_KEY)).getStatusCode();
            } catch (Exception e) {
                return (Integer) problem.getParameters().get(ERROR_KEY);
            }
        }
    }
}
