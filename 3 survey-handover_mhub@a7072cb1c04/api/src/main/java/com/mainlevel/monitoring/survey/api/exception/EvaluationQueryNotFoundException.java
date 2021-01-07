package com.mainlevel.monitoring.survey.api.exception;

import java.text.MessageFormat;

/**
 * Raised when the evaluation query could not be found.
 */
@SuppressWarnings("serial")
public class EvaluationQueryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Cypher query with name [{0}] not found.";

    private String query;

    /**
     * Constructor for EvaluationQueryNotFoundException.
     *
     * @param query name of the missing query
     */
    public EvaluationQueryNotFoundException(String query) {
        super(MessageFormat.format(MESSAGE, query));
        this.query = query;
    }

    /**
     * Gets the query for EvaluationQueryNotFoundException.
     *
     * @return query the query name
     */
    public String getQuery() {
        return query;
    }

}
