/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oozie.executor.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.oozie.ErrorCode;

import java.util.Objects;

/**
 * Load the CoordinatorJob into a Bean and return it.
 */
public class CoordJobGetActionByActionNumberJPAExecutor implements JPAExecutor<String> {

    private String coordJobId = null;
    private final int actionNumber;

    public CoordJobGetActionByActionNumberJPAExecutor(String coordJobId, int actionNumber) {
        this.coordJobId = Objects.requireNonNull(coordJobId, "coordJobId cannot be null");
        this.actionNumber = actionNumber;
    }

    @Override
    public String getName() {
        return "CoordJobGetActionByActionNumberJPAExecutor";
    }

    @Override
    public String execute(EntityManager em) throws JPAExecutorException {
        try {
            Query q = em.createNamedQuery("GET_COORD_ACTION_FOR_COORD_JOB_BY_ACTION_NUMBER");
            q.setParameter("jobId", coordJobId);
            q.setParameter("actionNumber", actionNumber);
            String actionId = (String) q.getSingleResult();
            return actionId;
        }
        catch (Exception e) {
            throw new JPAExecutorException(ErrorCode.E0603, e.getMessage(), e);
        }
    }
}
