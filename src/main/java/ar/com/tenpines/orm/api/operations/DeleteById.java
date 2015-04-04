package ar.com.tenpines.orm.api.operations;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * This type represents the operation to delete an object in the database using its id
 * Created by kfgodel on 04/04/15.
 */
public class DeleteById implements Function<Session, Nary<Void>> {
    public static Logger LOG = LoggerFactory.getLogger(DeleteById.class);

    private Class<Object> persistentType;
    private Long deletedId;

    @Override
    public Nary<Void> apply(Session session) {
        // It seems the only efficient way to delete by id (without loading the object) is using
        // a HQL query
        String deleteHql = "DELETE FROM " + persistentType.getName() + " WHERE id = :deletedId";
        Query deleteQuery = session.createQuery(deleteHql);
        deleteQuery.setParameter("deletedId", deletedId);
        int affectedRows = deleteQuery.executeUpdate();
        if(affectedRows != 1){
            LOG.debug("Deletion of {}[{}] did not affect just 1 row: {}", persistentType, deletedId, affectedRows);
        }
        // No result expected from this operation
        return NaryFromNative.empty();
    }

    public static DeleteById create(Class<?> persistentType, Long deletedId) {
        DeleteById deleteById = new DeleteById();
        deleteById.persistentType = (Class<Object>) persistentType;
        deleteById.deletedId = deletedId;
        return deleteById;
    }

}
