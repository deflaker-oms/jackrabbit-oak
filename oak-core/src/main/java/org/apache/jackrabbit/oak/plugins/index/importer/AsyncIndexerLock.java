/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.jackrabbit.oak.plugins.index.importer;

import org.apache.jackrabbit.oak.api.CommitFailedException;

/**
 * Lock used to prevent AsyncIndexUpdate from running when Index import
 * is in progress
 */
interface AsyncIndexerLock<T extends AsyncIndexerLock.LockToken> {

    AsyncIndexerLock NOOP_LOCK = new AsyncIndexerLock() {
        private final LockToken noopToken = new LockToken() {};

        @Override
        public LockToken lock(String asyncIndexerLane) {
            return noopToken;
        }

        @Override
        public void unlock(LockToken token) {

        }
    };

    /**
     * Marker interface. The implementation can return any
     * opaque implementation which would be handed back in unlock
     * call
     */
    interface LockToken {

    }

    T lock(String asyncIndexerLane) throws CommitFailedException;

    void unlock(T token) throws CommitFailedException;
}