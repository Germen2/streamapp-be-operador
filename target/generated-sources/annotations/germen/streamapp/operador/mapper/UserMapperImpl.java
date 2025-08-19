package germen.streamapp.operador.mapper;

import germen.streamapp.operador.model.Role;
import germen.streamapp.operador.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T22:06:55-0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public void updateUserFromSource(User source, User target) {
        if ( source == null ) {
            return;
        }

        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getFirstName() != null ) {
            target.setFirstName( source.getFirstName() );
        }
        if ( source.getLastName() != null ) {
            target.setLastName( source.getLastName() );
        }
        if ( source.getPassword() != null ) {
            target.setPassword( source.getPassword() );
        }
        if ( source.getEmail() != null ) {
            target.setEmail( source.getEmail() );
        }
        if ( target.getRoles() != null ) {
            List<Role> list = source.getRoles();
            if ( list != null ) {
                target.getRoles().clear();
                target.getRoles().addAll( list );
            }
        }
        else {
            List<Role> list = source.getRoles();
            if ( list != null ) {
                target.setRoles( new ArrayList<Role>( list ) );
            }
        }
    }
}
