package ttv.poltoraha.pivka.handler;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handlerEntityNotFound(EntityNotFoundException exception) {
        logger.error("ошибка: ", exception);
        return ResponseEntity.status(404).build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<Void> handlerServerError(EntityNotFoundException exception) {
        logger.error("ошибка: ", exception);
        return ResponseEntity.internalServerError().build();
    }
}
