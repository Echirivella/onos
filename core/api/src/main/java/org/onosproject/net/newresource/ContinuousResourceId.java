/*
 * Copyright 2016 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.net.newresource;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ResourceId for {@link ContinuousResource}
 *
 * Note: This class is exposed to the public, but intended to be used in the resource API
 * implementation only. It is not for resource API user.
 */
@Beta
public final class ContinuousResourceId extends ResourceId {
    final ImmutableList<Object> components;

    // for printing purpose only (used in toString() implementation)
    private final String name;

    ContinuousResourceId(ImmutableList<Object> components, String name) {
        this.components = components;
        this.name = checkNotNull(name);
    }

    ContinuousResourceId(ImmutableList.Builder<Object> parentComponents, Class<?> last) {
        this.components = parentComponents.add(last.getCanonicalName()).build();
        this.name = last.getSimpleName();
    }

    /**
     * {@inheritDoc}
     *
     * A child of a continuous-type resource is prohibited.
     * {@link UnsupportedOperationException} is always thrown.
     */
    @Override
    public DiscreteResourceId child(Object child) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * A child of a continuous-type resource is prohibited.
     * {@link UnsupportedOperationException} is always thrown.
     */
    @Override
    public ContinuousResourceId child(Class<?> child) {
        throw new UnsupportedOperationException();
    }

    @Override
    DiscreteResourceId parent() {
        if (components.size() == 0) {
            return null;
        }
        if (components.size() == 1) {
            return ROOT;
        } else {
            return new DiscreteResourceId(components.subList(0, components.size() - 1));
        }
    }

    @Override
    public int hashCode() {
        return components.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ContinuousResourceId other = (ContinuousResourceId) obj;
        return Objects.equals(this.components, other.components);
    }

    @Override
    public String toString() {
        // due to performance consideration, the value might need to be stored in a field
        return ImmutableList.builder()
                .addAll(components.subList(0, components.size() - 1))
                .add(name)
                .build().toString();
    }
}
