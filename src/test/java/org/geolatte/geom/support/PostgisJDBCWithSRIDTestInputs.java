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
 * Copyright (C) 2010 - 2012 and Ownership of code is shared by:
 * Qmino bvba - Romeinsestraat 18 - 3001 Heverlee  (http://www.qmino.com)
 * Geovise bvba - Generaal Eisenhowerlei 9 - 2140 Antwerpen (http://www.geovise.com)
 */

package org.geolatte.geom.support;

import org.geolatte.geom.*;
import org.geolatte.geom.crs.CoordinateReferenceSystem;
import org.geolatte.geom.crs.CrsRegistry;
import org.geolatte.geom.crs.LengthUnit;

/**
 * @author Karel Maesen, Geovise BVBA
 *         creation-date: 10/13/12
 */
public class PostgisJDBCWithSRIDTestInputs extends WktWkbCodecTestBase {

    private static CoordinateReferenceSystem<G2D> wgs84 = CrsRegistry.getGeographicCoordinateReferenceSystemForEPSG(4326);
    private static CoordinateReferenceSystem<G3D> wgs84_Z= wgs84.addVerticalAxis(LengthUnit.METER);
    private static CoordinateReferenceSystem<G2DM> wgs84_M = wgs84.addMeasureAxis(LengthUnit.METER);
    private static CoordinateReferenceSystem<G3DM> wgs84_ZM = wgs84_Z.addMeasureAxis(LengthUnit.METER);

    public PostgisJDBCWithSRIDTestInputs() {
        PostgisJDBCUnitTestInputs base = new PostgisJDBCUnitTestInputs();
        for (Integer testCase : base.getCases()) {
            addCase(testCase,
                    "SRID=4326;" + base.getWKT(testCase),
                    toSRIDPrefixedWKB(base, testCase),
                    addCrsId(base, testCase),
                    false
            );
        }
    }

    private Geometry addCrsId(PostgisJDBCUnitTestInputs base, Integer testCase) {
        Geometry geom = base.getExpected(testCase);
        CoordinateReferenceSystem<?> crs = null;
        CoordinateReferenceSystem<?> srcCrs = geom.getCoordinateReferenceSystem();

        if (srcCrs.hasVerticalAxis() &&  srcCrs.hasMeasureAxis()) {
            crs = wgs84_ZM;
        } else if (srcCrs.hasVerticalAxis()) {
            crs = wgs84_Z;
        } else if (srcCrs.hasMeasureAxis()) {
            crs = wgs84_M;
        } else {
            crs = wgs84;
        }
        return Geometry.forceToCrs(base.getExpected(testCase), crs);

    }

    private String toSRIDPrefixedWKB(PostgisJDBCUnitTestInputs base, Integer testCase) {
        String hexBase = base.getWKBHexString(testCase);
        ByteBuffer inBuffer = ByteBuffer.from(hexBase);
        //get the relevant parts
        inBuffer.setByteOrder(ByteOrder.NDR);
        byte bo = inBuffer.get();
        int type = inBuffer.getInt();
        byte[] bytes = inBuffer.toByteArray();

        //calculate the output size
        int outputSize = 4 + bytes.length;
        ByteBuffer outBuffer = ByteBuffer.allocate(outputSize);

        outBuffer.setByteOrder(ByteOrder.NDR);
        outBuffer.put(bo);
        type |= 0x20000000; // OR with the SRID-flag
        outBuffer.putInt(type);
        //write the srid
        outBuffer.putInt(4326);
        for (int i = 5; i < bytes.length; i++) {
            outBuffer.put(bytes[i]);
        }
        return outBuffer.toString();
    }

}
