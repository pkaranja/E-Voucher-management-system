package co.tz.qroo.zawadi.issuer.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import co.tz.qroo.zawadi.issuer.service.IssuerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the name value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = IssuerNameUnique.IssuerNameUniqueValidator.class
)
public @interface IssuerNameUnique {

    String message() default "{Exists.issuer.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class IssuerNameUniqueValidator implements ConstraintValidator<IssuerNameUnique, String> {

        private final IssuerService issuerService;
        private final HttpServletRequest request;

        public IssuerNameUniqueValidator(final IssuerService issuerService,
                final HttpServletRequest request) {
            this.issuerService = issuerService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(issuerService.get(UUID.fromString(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !issuerService.nameExists(value);
        }

    }

}
