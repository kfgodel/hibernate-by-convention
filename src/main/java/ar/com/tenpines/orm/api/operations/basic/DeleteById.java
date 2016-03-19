package ar.com.tenpines.orm.api.operations.basic;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import ar.com.tenpines.orm.api.entities.Persistable;
import ar.com.tenpines.orm.api.operations.SessionOperation;
import ar.com.tenpines.orm.api.operations.TransactionOperation;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This type represents the operation to delete an object in the database using its id
 * Created by kfgodel on 04/04/15.
 */
public class DeleteById implements TransactionOperation<Nary<Void>> {
    public static Logger LOG = LoggerFactory.getLogger(DeleteById.class);

    private Class<? extends Persistable> persistentType;
    private Long deletedId;

    public static DeleteById create(Class<? extends Persistable> persistentType, Long deletedId) {
        DeleteById deleteById = new DeleteById();
        deleteById.persistentType = persistentType;
        deleteById.deletedId = deletedId;
        return deleteById;
    }

    @Override
    public Nary<Void> applyWithTransactionOn(TransactionContext transactionContext) {
        // It seems the only efficient way to delete by id (without loading the object) is using
        // a HQL query
        String deleteHql = "DELETE FROM " + persistentType.getName() + " WHERE id = :deletedId";
        Session session = transactionContext.getSession();
        Query deleteQuery = session.createQuery(deleteHql);
        deleteQuery.setParameter("deletedId", deletedId);
        int affectedRows = deleteQuery.executeUpdate();
        if(affectedRows != 1){
            LOG.debug("Deletion of {}[{}] did not affect just 1 row: {}", persistentType, deletedId, affectedRows);
        }
        // No result expected from this operation
        return Nary.empty();
    }
}
