/*
 * This file is part of the GeoLatte project.
 *
 *     GeoLatte is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     GeoLatte is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with GeoLatte.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2010 - 2011 and Ownership of code is shared by:
 * Qmino bvba - Romeinsestraat 18 - 3001 Heverlee  (http://www.qmino.com)
 * Geovise bvba - Generaal Eisenhowerlei 9 - 2140 Antwerpen (http://www.geovise.com)
 */

package org.geolatte.geom;

/**
 * A portion of a line delimited (inclusively) by two <code>Position</code>s.
 * @author Karel Maesen, Geovise BVBA
 *         creation-date: 4/25/11
 */
public class LineSegment<P extends Position<P>> {


    private final P p0;
    private final P p1;

    /**
     * Constructs a <code>LineSegment</code> from the specified <code>Position</code>s.
     *
     * @param p0 the start <code>Position</code>
     * @param p1 the end <code>Position</code>
     */
    public LineSegment(P p0, P p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    /**
     * Returns the first, or start <code>Position</code> of this <code>LineSegment</code>
     * @return the first, or start <code>Position</code> of this <code>LineSegment</code>
     */
    public P getStartPosition() {
        return p0;
    }

    /**
     * Returns the second, or end <code>Position</code> of this <code>LineSegment</code>
     * @return the second, or end <code>Position</code> of this <code>LineSegment</code>
     */
    public P getEndPosition() {
        return p1;
    }

}
