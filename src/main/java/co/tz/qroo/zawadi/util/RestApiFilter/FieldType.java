package co.tz.qroo.zawadi.util.RestApiFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import co.tz.qroo.zawadi.user.model.ActiveStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum FieldType {

    BOOLEAN {
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },

    CHAR {
        public Object parse(String value) {
            return value.charAt(0);
        }
    },

    DATE {
        public Object parse(String value) {
            Object date = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                date = LocalDateTime.parse(value, formatter);
            } catch (Exception e) {
                log.info("Failed parse field type DATE {}", e.getMessage());
            }

            return date;
        }
    },

    DOUBLE {
        public Object parse(String value) {
            return Double.valueOf(value);
        }
    },

    INTEGER {
        public Object parse(String value) {
            return Integer.valueOf(value);
        }
    },

    LONG {
        public Object parse(String value) {
            return Long.valueOf(value);
        }
    },

    STRING {
        public Object parse(String value) {
            return value;
        }
    },

    ACTIVESTATUS {
        public Object parse(String value) { return ActiveStatus.valueOf(value); }
    },

    UUID {
        public Object parse(String value) { return UUID.valueOf(value); }
    };

    public abstract Object parse(String value);

}