package org.niis.xroad.restapi.openapi;

import lombok.extern.slf4j.Slf4j;
import org.niis.xroad.restapi.domain.ErrorInfo;
import org.niis.xroad.restapi.exceptions.*;
import org.niis.xroad.restapi.openapi.model.InlineObject9;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
@Slf4j
@PreAuthorize("permitAll") // WARNING: for tests only
public class TestController implements ServiceDescriptionsApi {

    private int counter = 0;

    @Override
    public ResponseEntity<Void> validateServiceDescription(@Valid InlineObject9 inlineObject9) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorInfo errorDto = new ErrorInfo(status.value());
        errorDto.setErrorCode("dsadasdsa");
        if (counter % 2 == 0) {
            NotFoundException e = new NotFoundException("example with warnings", ErrorCode.of("foo_error_code"));
            e.addWarning("adding_service", "fooService.v1");
            e.addWarning("adding_service", "barService.v1");
            e.addWarning("adding_service", "bazService.v1");
            throw e;
        }
        return new ResponseEntity<Void>(status);
    }
}
