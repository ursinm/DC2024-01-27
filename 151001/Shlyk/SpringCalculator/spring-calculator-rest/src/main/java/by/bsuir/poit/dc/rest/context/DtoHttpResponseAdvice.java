package by.bsuir.poit.dc.rest.context;

import by.bsuir.poit.dc.rest.api.dto.response.DeleteDto;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Paval Shlyk
 * @since 26/02/2024
 */
@Aspect
@Component
public class DtoHttpResponseAdvice {
    @AfterReturning(
	pointcut = "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ",
	returning = "dto"
    )
    public ResponseEntity<?> handleDeleteDto(DeleteDto dto) {
	var status = dto.isDeleted()
			 ? HttpStatus.NO_CONTENT
			 : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }
    //public class DeleteHttpResponseConverter extends AbstractHttpMessageConverter<DeleteDto> {
//    @Override
//    protected boolean supports(@NonNull Class<?> clazz) {
//	return clazz.isAssignableFrom(DeleteDto.class);
//    }
//
//    @Override
//    public boolean canRead(Class<?> clazz, MediaType mediaType) {
//	return false;
//    }
//
//    @Override
//    protected DeleteDto readInternal(Class<? extends DeleteDto> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
//	throw new UnsupportedOperationException("The delete response converter supports only conversation to response");
//    }
//
//    @Override
//    protected void writeInternal(DeleteDto deleteDto, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//	outputMessage.getHeaders().get`
//    }
}
