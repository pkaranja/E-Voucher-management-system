package co.tz.qroo.zawadi.giftcard.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import co.tz.qroo.zawadi.giftcard.service.GiftcardService;
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
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = GiftcardTransactionUnique.GiftcardTransactionUniqueValidator.class
)
public @interface GiftcardTransactionUnique {

    String message() default "{Exists.giftcard.transaction}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class GiftcardTransactionUniqueValidator implements ConstraintValidator<GiftcardTransactionUnique, UUID> {

        private final GiftcardService giftcardService;
        private final HttpServletRequest request;

        public GiftcardTransactionUniqueValidator(final GiftcardService giftcardService,
                final HttpServletRequest request) {
            this.giftcardService = giftcardService;
            this.request = request;
        }

        @Override
        public boolean isValid(final UUID value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(giftcardService.get(UUID.fromString(currentId)).getTransaction())) {
                // value hasn't changed
                return true;
            }
            return !giftcardService.transactionExists(value);
        }

    }

}
