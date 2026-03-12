/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.level.path;

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.map.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ExtractMethodRecommender")
public class KAStarPathfinderPositiveTests extends KStandardTestClass {

    private final KLocation location;

    public KAStarPathfinderPositiveTests() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KTileInfo tileInfo = new KTileInfo(1, true, 0, Map.of());
        KTileInfo impassable = new KTileInfo(2, false, 0, Map.of());

        KTileLayer tl1 = new KTileLayer(5, 5);
        KTileLayer tl2 = new KTileLayer(5, 5);
        KTileLayer tl3 = new KTileLayer(5, 5);
        KTileLayer tl4 = new KTileLayer(5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tl1.placeTile(i, j, impassable);
                tl2.placeTile(i, j, impassable);
                tl3.placeTile(i, j, impassable);
                tl4.placeTile(i, j, impassable);
            }
        }

        tl1
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(3, 4, tileInfo);

        tl2
            .placeTile(3, 0, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(2, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(0, 3, tileInfo);

        tl3
            .placeTile(4, 3, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(2, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(1, 2, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(1, 0, tileInfo);

        tl4
            .placeTile(1, 4, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(1, 2, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 4, tileInfo);

        KSectorLinkLayer sl1 = new KSectorLinkLayer();
        KSectorLinkLayer sl2 = new KSectorLinkLayer();
        KSectorLinkLayer sl3 = new KSectorLinkLayer();
        KSectorLinkLayer sl4 = new KSectorLinkLayer();

        KMapSector s1 = new KMapSector(es, "s1", tl1, new KHeightLayer(11, 11), sl1, new KMapEntityLayer());
        KMapSector s2 = new KMapSector(es, "s2", tl2, new KHeightLayer(11, 11), sl2, new KMapEntityLayer());
        KMapSector s3 = new KMapSector(es, "s3", tl3, new KHeightLayer(11, 11), sl3, new KMapEntityLayer());
        KMapSector s4 = new KMapSector(es, "s4", tl4, new KHeightLayer(11, 11), sl4, new KMapEntityLayer());

        sl1.link(3, 4, s2, 3, 0);
        sl2.link(0, 3, s3, 4, 3);
        sl3.link(1, 0, s4, 1, 4);

        this.location = new KLocation(
            "l1", List.of(s1, s2, s3, s4)
        );

    }

    @Test
    public void testFindPathInsideSector() {

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(this.location, "s1", 1, 1, "s1", 3, 1);
        KPath ch = chebyshev.findPath(this.location, "s1", 1, 1, "s1", 3, 1);
        KPath nch = naturalizedChebyshev.findPath(this.location, "s1", 1, 1, "s1", 3, 1);
        KPath mn = manhattan.findPath(this.location, "s1", 1, 1, "s1", 3, 1);

        KVector2i[] expected = new KVector2i[] {
            new KVector2i(1, 0),
            new KVector2i(1, 0)
        };

        for (KVector2i el : expected) {
            Assertions.assertEquals(el, ep.next());
            Assertions.assertEquals(el, ch.next());
            Assertions.assertEquals(el, nch.next());
            Assertions.assertEquals(el, mn.next());
        }

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());
    }

    @Test
    public void testFindPathToAnotherSectorWithOneJump() {

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(this.location, "s1", 1, 1, "s2", 3, 1);
        KPath ch = chebyshev.findPath(this.location, "s1", 1, 1, "s2", 3, 1);
        KPath nch = naturalizedChebyshev.findPath(this.location, "s1", 1, 1, "s2", 3, 1);
        KPath mn = manhattan.findPath(this.location, "s1", 1, 1, "s2", 3, 1);

        KVector2i[] expected = new KVector2i[] {
            new KVector2i(1, 0),
            new KVector2i(1, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
        };

        for (KVector2i el : expected) {
            Assertions.assertEquals(el, ep.next());
            Assertions.assertEquals(el, ch.next());
            Assertions.assertEquals(el, nch.next());
            Assertions.assertEquals(el, mn.next());
        }

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());

    }

    @Test
    public void testFindPathToAnotherSectorWithTwoJumps() {

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(this.location, "s1", 1, 1, "s3", 3, 3);
        KPath ch = chebyshev.findPath(this.location, "s1", 1, 1, "s3", 3, 3);
        KPath nch = naturalizedChebyshev.findPath(this.location, "s1", 1, 1, "s3", 3, 3);
        KPath mn = manhattan.findPath(this.location, "s1", 1, 1, "s3", 3, 3);

        KVector2i[] expected = new KVector2i[] {
            new KVector2i(1, 0),
            new KVector2i(1, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(-1, 1),
            new KVector2i(-1, 0),
            new KVector2i(-1, 0),
            new KVector2i(-1, 0),
            // new KVector2i(-1, 0),
        };

        for (KVector2i el : expected) {
            Assertions.assertEquals(el, ep.next());
            Assertions.assertEquals(el, ch.next());
            Assertions.assertEquals(el, nch.next());
            Assertions.assertEquals(el, mn.next());
        }

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());

    }

    @Test
    public void testFindPathToAnotherSectorWithThreeJumps() {

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(this.location, "s1", 1, 1, "s4", 1, 3);
        KPath ch = chebyshev.findPath(this.location, "s1", 1, 1, "s4", 1, 3);
        KPath nch = naturalizedChebyshev.findPath(this.location, "s1", 1, 1, "s4", 1, 3);
        KPath mn = manhattan.findPath(this.location, "s1", 1, 1, "s4", 1, 3);

        KVector2i[] expected = new KVector2i[] {
            new KVector2i(1, 0),
            new KVector2i(1, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(0, 1),
            new KVector2i(-1, 1),
            new KVector2i(-1, 0),
            new KVector2i(-1, 0),
            new KVector2i(-1, 0),
            new KVector2i(-1, 0),
            new KVector2i(-1, -1),
            new KVector2i(0, -1),
            new KVector2i(0, -1),
            new KVector2i(0, -1),
        };

        for (KVector2i el : expected) {
            Assertions.assertEquals(el, ep.next());
            Assertions.assertEquals(el, ch.next());
            Assertions.assertEquals(el, nch.next());
            Assertions.assertEquals(el, mn.next());
        }

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());

    }

    @Test
    public void testGetPathToNonExistentSectors() {

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        Assertions.assertTrue(euclidean.findPath(this.location, "s1", 1, 1, "s5", 1, 3).finished());
        Assertions.assertTrue(chebyshev.findPath(this.location, "s1", 1, 1, "s5", 1, 3).finished());
        Assertions.assertTrue(naturalizedChebyshev.findPath(this.location, "s1", 1, 1, "s5", 1, 3).finished());
        Assertions.assertTrue(manhattan.findPath(this.location, "s1", 1, 1, "s5", 1, 3).finished());

        Assertions.assertTrue(euclidean.findPath(this.location, "s0", 1, 1, "s2", 1, 3).finished());
        Assertions.assertTrue(chebyshev.findPath(this.location, "s0", 1, 1, "s2", 1, 3).finished());
        Assertions.assertTrue(naturalizedChebyshev.findPath(this.location, "s0", 1, 1, "s2", 1, 3).finished());
        Assertions.assertTrue(manhattan.findPath(this.location, "s0", 1, 1, "s2", 1, 3).finished());

        Assertions.assertTrue(euclidean.findPath(this.location, "s0", 1, 1, "s5", 1, 3).finished());
        Assertions.assertTrue(chebyshev.findPath(this.location, "s0", 1, 1, "s5", 1, 3).finished());
        Assertions.assertTrue(naturalizedChebyshev.findPath(this.location, "s0", 1, 1, "s5", 1, 3).finished());
        Assertions.assertTrue(manhattan.findPath(this.location, "s0", 1, 1, "s5", 1, 3).finished());

    }

    @Test
    public void testGetPathButSectorPathIsEmpty() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KTileInfo tileInfo = new KTileInfo(1, true, 0, Map.of());
        KTileInfo impassable = new KTileInfo(2, false, 0, Map.of());

        KTileLayer tl1 = new KTileLayer(5, 5);
        KTileLayer tl2 = new KTileLayer(5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tl1.placeTile(i, j, impassable);
                tl2.placeTile(i, j, impassable);
            }
        }

        tl1
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(3, 4, tileInfo);

        tl2
            .placeTile(3, 0, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(2, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(0, 3, tileInfo);

        KMapSector s1 = new KMapSector(es, "s1", tl1, new KHeightLayer(11, 11), new KSectorLinkLayer(), new KMapEntityLayer());
        KMapSector s2 = new KMapSector(es, "s2", tl2, new KHeightLayer(11, 11), new KSectorLinkLayer(), new KMapEntityLayer());


        var loc2 = new KLocation(
            "l2", List.of(s1, s2)
        );

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        Assertions.assertTrue(euclidean.findPath(loc2, "s1", 1, 1, "s2", 1, 3).finished());
        Assertions.assertTrue(chebyshev.findPath(loc2, "s1", 1, 1, "s2", 1, 3).finished());
        Assertions.assertTrue(naturalizedChebyshev.findPath(loc2, "s1", 1, 1, "s2", 1, 3).finished());
        Assertions.assertTrue(manhattan.findPath(loc2, "s1", 1, 1, "s2", 1, 3).finished());

    }

    @Test
    public void testGetPathToSelfButPathIsThroughAnotherSectors() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KTileInfo tileInfo = new KTileInfo(1, true, 0, Map.of());
        KTileInfo impassable = new KTileInfo(2, false, 0, Map.of());

        KTileLayer tl1 = new KTileLayer(5, 5);
        KTileLayer tl2 = new KTileLayer(5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tl1.placeTile(i, j, impassable);
                tl2.placeTile(i, j, impassable);
            }
        }

        tl1
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(4, 1, tileInfo);

        tl2
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(4, 1, tileInfo);

        KSectorLinkLayer sl1 = new KSectorLinkLayer();
        KSectorLinkLayer sl2 = new KSectorLinkLayer();

        KMapSector s1 = new KMapSector(es, "s1", tl1, new KHeightLayer(11, 11), sl1, new KMapEntityLayer());
        KMapSector s2 = new KMapSector(es, "s2", tl2, new KHeightLayer(11, 11), sl2, new KMapEntityLayer());

        sl1.link(4, 1, s2, 0, 1);
        sl2.link(4, 1, s1, 0, 1);

        var loc2 = new KLocation(
            "l2", List.of(s1, s2)
        );

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(loc2, "s1", 3, 1, "s1", 1, 1);
        KPath ch = chebyshev.findPath(loc2, "s1", 3, 1, "s1", 1, 1);
        KPath nch = naturalizedChebyshev.findPath(loc2, "s1", 3, 1, "s1", 1, 1);
        KPath mn = manhattan.findPath(loc2, "s1", 3, 1, "s1", 1, 1);

        KVector2i[] expected = new KVector2i[] {
            new KVector2i(1, 0),
            new KVector2i(1, 0),
            new KVector2i(1, 0),
            new KVector2i(1, 0),
            new KVector2i(1, 0),
            new KVector2i(1, 0),
        };

        for (KVector2i el : expected) {
            Assertions.assertEquals(el, ep.next());
            Assertions.assertEquals(el, ch.next());
            Assertions.assertEquals(el, nch.next());
            Assertions.assertEquals(el, mn.next());
        }

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());

    }

    @Test
    public void testGetPathToSelfAndThereIsNoPath() {


        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KTileInfo tileInfo = new KTileInfo(1, true, 0, Map.of());
        KTileInfo impassable = new KTileInfo(2, false, 0, Map.of());

        KTileLayer tl1 = new KTileLayer(5, 5);
        KTileLayer tl2 = new KTileLayer(5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tl1.placeTile(i, j, impassable);
                tl2.placeTile(i, j, impassable);
            }
        }

        tl1
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(4, 1, tileInfo);

        tl2
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(4, 1, tileInfo);

        KSectorLinkLayer sl1 = new KSectorLinkLayer();
        KSectorLinkLayer sl2 = new KSectorLinkLayer();

        KMapSector s1 = new KMapSector(es, "s1", tl1, new KHeightLayer(11, 11), sl1, new KMapEntityLayer());
        KMapSector s2 = new KMapSector(es, "s2", tl2, new KHeightLayer(11, 11), sl2, new KMapEntityLayer());

        sl2.link(4, 1, s1, 0, 1);

        var loc2 = new KLocation(
            "l2", List.of(s1, s2)
        );

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(loc2, "s1", 3, 1, "s1", 1, 1);
        KPath ch = chebyshev.findPath(loc2, "s1", 3, 1, "s1", 1, 1);
        KPath nch = naturalizedChebyshev.findPath(loc2, "s1", 3, 1, "s1", 1, 1);
        KPath mn = manhattan.findPath(loc2, "s1", 3, 1, "s1", 1, 1);

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());

    }

    @Test
    public void testGetPathToAnotherSectorButSomeLinksAreBad() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KTileInfo tileInfo = new KTileInfo(1, true, 0, Map.of());
        KTileInfo impassable = new KTileInfo(2, false, 0, Map.of());

        KTileLayer tl1 = new KTileLayer(5, 5);
        KTileLayer tl2 = new KTileLayer(5, 5);
        KTileLayer tl3 = new KTileLayer(5, 5);
        KTileLayer tl4 = new KTileLayer(5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tl1.placeTile(i, j, impassable);
                tl2.placeTile(i, j, impassable);
                tl3.placeTile(i, j, impassable);
                tl4.placeTile(i, j, impassable);
            }
        }

        tl1
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(3, 4, tileInfo);

        tl2
            .placeTile(3, 0, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(0, 3, tileInfo);

        tl3
            .placeTile(4, 3, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(2, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(1, 2, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(1, 0, tileInfo);

        tl4
            .placeTile(1, 4, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(1, 2, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 4, tileInfo);

        KSectorLinkLayer sl1 = new KSectorLinkLayer();
        KSectorLinkLayer sl2 = new KSectorLinkLayer();
        KSectorLinkLayer sl3 = new KSectorLinkLayer();
        KSectorLinkLayer sl4 = new KSectorLinkLayer();

        KMapSector s1 = new KMapSector(es, "s1", tl1, new KHeightLayer(11, 11), sl1, new KMapEntityLayer());
        KMapSector s2 = new KMapSector(es, "s2", tl2, new KHeightLayer(11, 11), sl2, new KMapEntityLayer());
        KMapSector s3 = new KMapSector(es, "s3", tl3, new KHeightLayer(11, 11), sl3, new KMapEntityLayer());
        KMapSector s4 = new KMapSector(es, "s4", tl4, new KHeightLayer(11, 11), sl4, new KMapEntityLayer());

        sl1.link(3, 4, s2, 3, 0);
        sl2.link(0, 3, s3, 4, 3);
        sl3.link(1, 0, s4, 1, 4);

        var loc2 = new KLocation(
            "l1", List.of(s1, s2, s3, s4)
        );

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(loc2, "s1", 3, 1, "s3", 3, 3);
        KPath ch = chebyshev.findPath(loc2, "s1", 3, 1, "s3", 3, 3);
        KPath nch = naturalizedChebyshev.findPath(loc2, "s1", 3, 1, "s3", 3, 3);
        KPath mn = manhattan.findPath(loc2, "s1", 3, 1, "s3", 3, 3);

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());
    }

    @Test
    public void testGetPathToAnotherSectorButSomeLinksAreBadOnLast() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KTileInfo tileInfo = new KTileInfo(1, true, 0, Map.of());
        KTileInfo impassable = new KTileInfo(2, false, 0, Map.of());

        KTileLayer tl1 = new KTileLayer(5, 5);
        KTileLayer tl2 = new KTileLayer(5, 5);
        KTileLayer tl3 = new KTileLayer(5, 5);
        KTileLayer tl4 = new KTileLayer(5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tl1.placeTile(i, j, impassable);
                tl2.placeTile(i, j, impassable);
                tl3.placeTile(i, j, impassable);
                tl4.placeTile(i, j, impassable);
            }
        }

        tl1
            .placeTile(1, 1, tileInfo)
            .placeTile(2, 1, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(3, 4, tileInfo);

        tl2
            .placeTile(3, 0, tileInfo)
            .placeTile(3, 1, tileInfo)
            .placeTile(3, 2, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(2, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(0, 3, tileInfo);

        tl3
            .placeTile(4, 3, tileInfo)
            .placeTile(3, 3, tileInfo)
            .placeTile(2, 3, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(1, 2, tileInfo)
            .placeTile(1, 1, tileInfo)
            .placeTile(1, 0, tileInfo);

        tl4
            .placeTile(1, 4, tileInfo)
            .placeTile(1, 3, tileInfo)
            .placeTile(1, 2, tileInfo)
            .placeTile(3, 4, tileInfo);

        KSectorLinkLayer sl1 = new KSectorLinkLayer();
        KSectorLinkLayer sl2 = new KSectorLinkLayer();
        KSectorLinkLayer sl3 = new KSectorLinkLayer();
        KSectorLinkLayer sl4 = new KSectorLinkLayer();

        KMapSector s1 = new KMapSector(es, "s1", tl1, new KHeightLayer(11, 11), sl1, new KMapEntityLayer());
        KMapSector s2 = new KMapSector(es, "s2", tl2, new KHeightLayer(11, 11), sl2, new KMapEntityLayer());
        KMapSector s3 = new KMapSector(es, "s3", tl3, new KHeightLayer(11, 11), sl3, new KMapEntityLayer());
        KMapSector s4 = new KMapSector(es, "s4", tl4, new KHeightLayer(11, 11), sl4, new KMapEntityLayer());

        sl1.link(3, 4, s2, 3, 0);
        sl2.link(0, 3, s3, 4, 3);
        sl3.link(1, 0, s4, 1, 4);

        var loc2 = new KLocation(
            "l1", List.of(s1, s2, s3, s4)
        );

        KPathfinder euclidean = new KAStarPathfinder(KAStarPathfinder.Heuristic.EUCLIDEAN);
        KPathfinder chebyshev = new KAStarPathfinder(KAStarPathfinder.Heuristic.CHEBYSHEV);
        KPathfinder naturalizedChebyshev = new KAStarPathfinder();
        KPathfinder manhattan = new KAStarPathfinder(KAStarPathfinder.Heuristic.MANHATTAN);

        KPath ep = euclidean.findPath(loc2, "s1", 3, 1, "s4", 3, 4);
        KPath ch = chebyshev.findPath(loc2, "s1", 3, 1, "s4", 3, 4);
        KPath nch = naturalizedChebyshev.findPath(loc2, "s1", 3, 1, "s4", 3, 4);
        KPath mn = manhattan.findPath(loc2, "s1", 3, 1, "s4", 3, 4);

        Assertions.assertTrue(ep.finished());
        Assertions.assertTrue(ch.finished());
        Assertions.assertTrue(nch.finished());
        Assertions.assertTrue(mn.finished());

    }
}
