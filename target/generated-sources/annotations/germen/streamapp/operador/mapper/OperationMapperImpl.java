package germen.streamapp.operador.mapper;

import germen.streamapp.operador.model.Operation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T22:06:55-0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class OperationMapperImpl implements OperationMapper {

    @Override
    public void updateOperationFromSource(Operation source, Operation target) {
        if ( source == null ) {
            return;
        }

        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getUserId() != null ) {
            target.setUserId( source.getUserId() );
        }
        if ( source.getMovieId() != null ) {
            target.setMovieId( source.getMovieId() );
        }
        if ( source.getOperationType() != null ) {
            target.setOperationType( source.getOperationType() );
        }
        if ( source.getOperationDate() != null ) {
            target.setOperationDate( source.getOperationDate() );
        }
        if ( source.getExpirationDate() != null ) {
            target.setExpirationDate( source.getExpirationDate() );
        }
    }
}
