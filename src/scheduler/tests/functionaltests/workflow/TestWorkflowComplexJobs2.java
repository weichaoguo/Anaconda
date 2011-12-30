/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2011 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ActiveEon Team
 *                        http://www.activeeon.com/
 *  Contributor(s):
 *
 * ################################################################
 * $$ACTIVEEON_INITIAL_DEV$$
 */
package functionaltests.workflow;

import org.ow2.proactive.scheduler.common.task.flow.FlowActionType;


/**
 * Tests the correctness of workflow-controlled jobs including {@link FlowActionType#LOOP} and 
 * {@link FlowActionType#REPLICATE} actions
 * 
 * @author The ProActive Team
 * @since ProActive Scheduling 2.2
 */
public class TestWorkflowComplexJobs2 extends TWorkflowJobs {

    @Override
    public String getJobPrefix() {
        return "/functionaltests/workflow/descriptors/flow_complex_2_";
    }

    @Override
    public String[][] getJobs() {
        return new String[][] {
        /** REPLICATE & LOOP **/
        // 1: loop block -> replicate block -> loop block
                { "T 0 ()", "T1 1 (T)", "T2 2 (T1)", "T3 4 (T1 T2)", "T1#1 5 (T3)", "T2#1 6 (T1#1)", "T3#1 12 (T1#1 T2#1)", "T1#2 13 (T3#1)", "T2#2 14 (T1#2)",
                        "T3#2 28 (T1#2 T2#2)", "T1*1 1 (T)", "T2*1 2 (T1*1)", "T3*1 4 (T1*1 T2*1)", "T1#1*1 5 (T3*1)", "T2#1*1 6 (T1#1*1)", "T3#1*1 12 (T1#1*1 T2#1*1)",
                        "T1#2*1 13 (T3#1*1)", "T2#2*1 14 (T1#2*1)", "T3#2*1 28 (T1#2*1 T2#2*1)", "T1*2 1 (T)", "T2*2 2 (T1*2)", "T3*2 4 (T1*2 T2*2)", "T1#1*2 5 (T3*2)",
                        "T2#1*2 6 (T1#1*2)", "T3#1*2 12 (T1#1*2 T2#1*2)", "T1#2*2 13 (T3#1*2)", "T2#2*2 14 (T1#2*2)", "T3#2*2 28 (T1#2*2 T2#2*2)", "T4 85 (T3#2 T3#2*1 T3#2*2)", "T#1 86 (T4)",
                        "T1#3 87 (T#1)", "T2#3 88 (T1#3)", "T3#3 176 (T1#3 T2#3)", "T1#4 177 (T3#3)", "T2#4 178 (T1#4)", "T3#4 356 (T1#4 T2#4)", "T1#5 357 (T3#4)",
                        "T2#5 358 (T1#5)", "T3#5 716 (T1#5 T2#5)", "T1#3*1 87 (T#1)", "T2#3*1 88 (T1#3*1)", "T3#3*1 176 (T1#3*1 T2#3*1)", "T1#4*1 177 (T3#3*1)",
                        "T2#4*1 178 (T1#4*1)", "T3#4*1 356 (T1#4*1 T2#4*1)", "T1#5*1 357 (T3#4*1)", "T2#5*1 358 (T1#5*1)", "T3#5*1 716 (T1#5*1 T2#5*1)", "T1#3*2 87 (T#1)",
                        "T2#3*2 88 (T1#3*2)", "T3#3*2 176 (T1#3*2 T2#3*2)", "T1#4*2 177 (T3#3*2)", "T2#4*2 178 (T1#4*2)", "T3#4*2 356 (T1#4*2 T2#4*2)", "T1#5*2 357 (T3#4*2)",
                        "T2#5*2 358 (T1#5*2)", "T3#5*2 716 (T1#5*2 T2#5*2)", "T4#1 2149 (T3#5 T3#5*1 T3#5*2)", "T#2 2150 (T4#1)", "T1#6 2151 (T#2)", "T2#6 2152 (T1#6)",
                        "T3#6 4304 (T1#6 T2#6)", "T1#7 4305 (T3#6)", "T2#7 4306 (T1#7)", "T3#7 8612 (T1#7 T2#7)", "T1#8 8613 (T3#7)", "T2#8 8614 (T1#8)",
                        "T3#8 17228 (T1#8 T2#8)", "T1#6*1 2151 (T#2)", "T2#6*1 2152 (T1#6*1)", "T3#6*1 4304 (T1#6*1 T2#6*1)", "T1#7*1 4305 (T3#6*1)",
                        "T2#7*1 4306 (T1#7*1)", "T3#7*1 8612 (T1#7*1 T2#7*1)", "T1#8*1 8613 (T3#7*1)", "T2#8*1 8614 (T1#8*1)", "T3#8*1 17228 (T1#8*1 T2#8*1)",
                        "T1#6*2 2151 (T#2)", "T2#6*2 2152 (T1#6*2)", "T3#6*2 4304 (T1#6*2 T2#6*2)", "T1#7*2 4305 (T3#6*2)", "T2#7*2 4306 (T1#7*2)",
                        "T3#7*2 8612 (T1#7*2 T2#7*2)", "T1#8*2 8613 (T3#7*2)", "T2#8*2 8614 (T1#8*2)", "T3#8*2 17228 (T1#8*2 T2#8*2)", "T4#2 51685 (T3#8 T3#8*1 T3#8*2)", },

                // 2: complex multiple loops / replicates
                { "T 0 ()", "T1 1 (T)", "T6 2 (T1)", "T16 3 (T6)", "T18 4 (T16)", "T14 3 (T6)", "T17 4 (T14)", "T17#1 5 (T17)", "T17#2 6 (T17#1)", "T17#3 7 (T17#2)",
                        "T17#4 8 (T17#3)", "T15 12 (T14 T17#4)", "T7 3 (T6)", "T8 4 (T7)", "T9 5 (T8)", "T8*1 4 (T7)", "T9*1 5 (T8*1)", "T8*2 4 (T7)", "T9*2 5 (T8*2)",
                        "T11 4 (T7)", "T10 5 (T11)", "T11*1 4 (T7)", "T10*1 5 (T11*1)", "T11*2 4 (T7)", "T10*2 5 (T11*2)", "T12 31 (T9 T9*1 T9*2 T10 T10*1 T10*2)", "T7#1 32 (T12)",
                        "T8#1 33 (T7#1)", "T9#1 34 (T8#1)", "T8#1*1 33 (T7#1)", "T9#1*1 34 (T8#1*1)", "T8#1*2 33 (T7#1)", "T9#1*2 34 (T8#1*2)", "T11#1 33 (T7#1)",
                        "T10#1 34 (T11#1)", "T11#1*1 33 (T7#1)", "T10#1*1 34 (T11#1*1)", "T11#1*2 33 (T7#1)", "T10#1*2 34 (T11#1*2)", "T12#1 205 (T9#1 T9#1*1 T9#1*2 T10#1 T10#1*1 T10#1*2)",
                        "T7#2 206 (T12#1)", "T8#2 207 (T7#2)", "T9#2 208 (T8#2)", "T8#2*1 207 (T7#2)", "T9#2*1 208 (T8#2*1)", "T8#2*2 207 (T7#2)",
                        "T9#2*2 208 (T8#2*2)", "T11#2 207 (T7#2)", "T10#2 208 (T11#2)", "T11#2*1 207 (T7#2)", "T10#2*1 208 (T11#2*1)", "T11#2*2 207 (T7#2)",
                        "T10#2*2 208 (T11#2*2)", "T12#2 1249 (T9#2 T9#2*1 T9#2*2 T10#2 T10#2*1 T10#2*2)", "T13 1266 (T12#2 T15 T18)", "T6#1 1267 (T13)", "T16#1 1268 (T6#1)", "T18#1 1269 (T16#1)",
                        "T14#1 1268 (T6#1)", "T17#5 1269 (T14#1)", "T17#6 1270 (T17#5)", "T17#7 1271 (T17#6)", "T17#8 1272 (T17#7)", "T17#9 1273 (T17#8)",
                        "T15#1 2542 (T14#1 T17#9)", "T7#3 1268 (T6#1)", "T8#3 1269 (T7#3)", "T9#3 1270 (T8#3)", "T8#3*1 1269 (T7#3)", "T9#3*1 1270 (T8#3*1)",
                        "T8#3*2 1269 (T7#3)", "T9#3*2 1270 (T8#3*2)", "T11#3 1269 (T7#3)", "T10#3 1270 (T11#3)", "T11#3*1 1269 (T7#3)",
                        "T10#3*1 1270 (T11#3*1)", "T11#3*2 1269 (T7#3)", "T10#3*2 1270 (T11#3*2)", "T12#3 7621 (T9#3 T9#3*1 T9#3*2 T10#3 T10#3*1 T10#3*2)", "T7#4 7622 (T12#3)",
                        "T8#4 7623 (T7#4)", "T9#4 7624 (T8#4)", "T8#4*1 7623 (T7#4)", "T9#4*1 7624 (T8#4*1)", "T8#4*2 7623 (T7#4)", "T9#4*2 7624 (T8#4*2)",
                        "T11#4 7623 (T7#4)", "T10#4 7624 (T11#4)", "T11#4*1 7623 (T7#4)", "T10#4*1 7624 (T11#4*1)", "T11#4*2 7623 (T7#4)",
                        "T10#4*2 7624 (T11#4*2)", "T12#4 45745 (T9#4 T9#4*1 T9#4*2 T10#4 T10#4*1 T10#4*2)", "T7#5 45746 (T12#4)", "T8#5 45747 (T7#5)", "T9#5 45748 (T8#5)",
                        "T8#5*1 45747 (T7#5)", "T9#5*1 45748 (T8#5*1)", "T8#5*2 45747 (T7#5)", "T9#5*2 45748 (T8#5*2)", "T11#5 45747 (T7#5)",
                        "T10#5 45748 (T11#5)", "T11#5*1 45747 (T7#5)", "T10#5*1 45748 (T11#5*1)", "T11#5*2 45747 (T7#5)", "T10#5*2 45748 (T11#5*2)",
                        "T12#5 274489 (T9#5 T9#5*1 T9#5*2 T10#5 T10#5*1 T10#5*2)", "T13#1 278301 (T12#5 T15#1 T18#1)", "T19 2 (T1)", "T27 3 (T19)", "T27*1 3 (T19)", "T28 7 (T27 T27*1)", "T29 3 (T19)",
                        "T30 4 (T29)", "T30#1 5 (T30)", "T30#2 6 (T30#1)", "T30#3 7 (T30#2)", "T30#4 8 (T30#3)", "T31 4 (T29)", "T31#1 5 (T31)", "T31#2 6 (T31#1)",
                        "T31#3 7 (T31#2)", "T31#4 8 (T31#3)", "T32 17 (T30#4 T31#4)", "T29*1 3 (T19)", "T30*1 4 (T29*1)", "T30#1*1 5 (T30*1)", "T30#2*1 6 (T30#1*1)",
                        "T30#3*1 7 (T30#2*1)", "T30#4*1 8 (T30#3*1)", "T31*1 4 (T29*1)", "T31#1*1 5 (T31*1)", "T31#2*1 6 (T31#1*1)", "T31#3*1 7 (T31#2*1)",
                        "T31#4*1 8 (T31#3*1)", "T32*1 17 (T30#4*1 T31#4*1)", "T21 3 (T19)", "T24 4 (T21)", "T22 8 (T21 T24)", "T26 5 (T24)", "T26#1 6 (T26)", "T26#2 7 (T26#1)",
                        "T26#3 8 (T26#2)", "T26#4 9 (T26#3)", "T25 22 (T22 T24 T26#4)", "T23 31 (T22 T25)", "T21#1 32 (T23)", "T24#1 33 (T21#1)", "T22#1 66 (T21#1 T24#1)",
                        "T26#5 34 (T24#1)", "T26#6 35 (T26#5)", "T26#7 36 (T26#6)", "T26#8 37 (T26#7)", "T26#9 38 (T26#8)", "T25#1 138 (T22#1 T24#1 T26#9)", "T23#1 205 (T22#1 T25#1)",
                        "T21#2 206 (T23#1)", "T24#2 207 (T21#2)", "T22#2 414 (T21#2 T24#2)", "T26#10 208 (T24#2)", "T26#11 209 (T26#10)", "T26#12 210 (T26#11)",
                        "T26#13 211 (T26#12)", "T26#14 212 (T26#13)", "T25#2 834 (T22#2 T24#2 T26#14)", "T23#2 1249 (T22#2 T25#2)", "T21*1 3 (T19)", "T24*1 4 (T21*1)",
                        "T22*1 8 (T21*1 T24*1)", "T26*1 5 (T24*1)", "T26#1*1 6 (T26*1)", "T26#2*1 7 (T26#1*1)", "T26#3*1 8 (T26#2*1)", "T26#4*1 9 (T26#3*1)", "T25*1 22 (T22*1 T24*1 T26#4*1)",
                        "T23*1 31 (T22*1 T25*1)", "T21#1*1 32 (T23*1)", "T24#1*1 33 (T21#1*1)", "T22#1*1 66 (T21#1*1 T24#1*1)", "T26#5*1 34 (T24#1*1)", "T26#6*1 35 (T26#5*1)",
                        "T26#7*1 36 (T26#6*1)", "T26#8*1 37 (T26#7*1)", "T26#9*1 38 (T26#8*1)", "T25#1*1 138 (T22#1*1 T24#1*1 T26#9*1)", "T23#1*1 205 (T22#1*1 T25#1*1)",
                        "T21#2*1 206 (T23#1*1)", "T24#2*1 207 (T21#2*1)", "T22#2*1 414 (T21#2*1 T24#2*1)", "T26#10*1 208 (T24#2*1)", "T26#11*1 209 (T26#10*1)",
                        "T26#12*1 210 (T26#11*1)", "T26#13*1 211 (T26#12*1)", "T26#14*1 212 (T26#13*1)", "T25#2*1 834 (T22#2*1 T24#2*1 T26#14*1)", "T23#2*1 1249 (T22#2*1 T25#2*1)",
                        "T20 2540 (T23#2 T23#2*1 T28 T32 T32*1)", "T2 280842 (T13#1 T20)", "T3 280843 (T2)", "T4 280843 (T2)", "T5 280843 (T2)",

                },
        //                

        };
    }

    @org.junit.Test
    public void run() throws Throwable {
        internalRun();
    }
}
