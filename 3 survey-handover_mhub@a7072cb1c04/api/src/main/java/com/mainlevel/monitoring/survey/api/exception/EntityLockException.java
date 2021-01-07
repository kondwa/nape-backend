package com.mainlevel.monitoring.survey.api.exception;

/**
 * Raised when an lock should be acquired on an already locked entity.
 */
public class EntityLockException extends RuntimeException {

    private static final long serialVersionUID = 5234484520099434574L;

    /**
     * Constructor for EntityLockException.
     *
     * @param id the entity id
     * @param user the user id acquiring the lock
     */
    public EntityLockException(Long id, String user) {
        super(String.format("Node [id=%s] already locked by user [%s].", id, user));
    }
}
