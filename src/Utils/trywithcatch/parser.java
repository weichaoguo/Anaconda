/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 */
package trywithcatch;

//----------------------------------------------------
// The following code was generated by CUP v0.10k TUM Edition 20050516
// Fri Sep 23 17:08:31 CEST 2005
//----------------------------------------------------
import java.util.LinkedList;
import java.util.List;


/** CUP v0.10k TUM Edition 20050516 generated parser.
 * @version Fri Sep 23 17:08:31 CEST 2005
 */
public class parser extends trywithcatch.java_cup.runtime.lr_parser {

    /** Default constructor. */
    public parser() {
        super();
    }

    /** Constructor which sets the default scanner. */
    public parser(trywithcatch.java_cup.runtime.Scanner s) {
        super(s);
    }

    /** Production table. */
    protected static final short[][] _production_table = unpackFromStrings(new String[] { "\000\016\000\002\003\004\000\002\002\004\000\002\003"
        + "\002\000\002\004\003\000\002\004\003\000\002\004\003"
        + "\000\002\004\003\000\002\004\003\000\002\005\005\000"
        + "\002\006\006\000\002\007\006\000\002\007\002\000\002" + "\010\004\000\002\010\002" });

    /** Access to production table. */
    @Override
    public short[][] production_table() {
        return _production_table;
    }

    /** Parse-action table. */
    protected static final short[][] _action_table = unpackFromStrings(new String[] { "\000\025\000\016\002\uffff\005\uffff\010\uffff\012\uffff\013"
        + "\uffff\014\uffff\001\002\000\016\002\011\005\013\010\012"
        + "\012\007\013\015\014\005\001\002\000\020\002\ufffb\005"
        + "\ufffb\010\ufffb\011\ufffb\012\ufffb\013\ufffb\014\ufffb\001\002"
        + "\000\020\002\001\005\001\010\001\011\001\012\001\013"
        + "\001\014\001\001\002\000\020\002\ufffd\005\ufffd\010\ufffd"
        + "\011\ufffd\012\ufffd\013\ufffd\014\ufffd\001\002\000\020\002"
        + "\ufffe\005\ufffe\010\ufffe\011\ufffe\012\ufffe\013\ufffe\014\ufffe"
        + "\001\002\000\004\002\000\001\002\000\016\005\uffff\010"
        + "\uffff\011\uffff\012\uffff\013\uffff\014\uffff\001\002\000\004"
        + "\010\012\001\002\000\020\002\ufffa\005\ufffa\010\ufffa\011"
        + "\ufffa\012\ufffa\013\ufffa\014\ufffa\001\002\000\020\002\ufffc"
        + "\005\ufffc\010\ufffc\011\ufffc\012\ufffc\013\ufffc\014\ufffc\001"
        + "\002\000\024\002\ufff6\005\ufff6\006\ufff6\007\ufff6\010\ufff6"
        + "\011\ufff6\012\ufff6\013\ufff6\014\ufff6\001\002\000\024\002"
        + "\ufff4\005\ufff4\006\022\007\020\010\ufff4\011\ufff4\012\ufff4"
        + "\013\ufff4\014\ufff4\001\002\000\004\010\012\001\002\000"
        + "\020\002\ufff8\005\ufff8\010\ufff8\011\ufff8\012\ufff8\013\ufff8"
        + "\014\ufff8\001\002\000\004\004\023\001\002\000\004\010"
        + "\012\001\002\000\024\002\ufff7\005\ufff7\006\ufff7\007\ufff7"
        + "\010\ufff7\011\ufff7\012\ufff7\013\ufff7\014\ufff7\001\002\000"
        + "\020\002\ufff5\005\ufff5\010\ufff5\011\ufff5\012\ufff5\013\ufff5"
        + "\014\ufff5\001\002\000\016\005\013\010\012\011\027\012"
        + "\007\013\015\014\005\001\002\000\024\002\ufff9\005\ufff9"
        + "\006\ufff9\007\ufff9\010\ufff9\011\ufff9\012\ufff9\013\ufff9\014" + "\ufff9\001\002" });

    /** Access to parse-action table. */
    @Override
    public short[][] action_table() {
        return _action_table;
    }

    /** <code>reduce_goto</code> table. */
    protected static final short[][] _reduce_table = unpackFromStrings(new String[] { "\000\025\000\004\003\003\001\001\000\010\004\005\005"
        + "\007\006\013\001\001\000\002\001\001\000\002\001\001"
        + "\000\002\001\001\000\002\001\001\000\002\001\001\000"
        + "\004\003\025\001\001\000\004\005\015\001\001\000\002"
        + "\001\001\000\002\001\001\000\004\007\016\001\001\000"
        + "\004\010\020\001\001\000\004\005\024\001\001\000\002"
        + "\001\001\000\002\001\001\000\004\005\023\001\001\000"
        + "\002\001\001\000\002\001\001\000\010\004\005\005\007" + "\006\013\001\001\000\002\001\001" });

    /** Access to <code>reduce_goto</code> table. */
    @Override
    public short[][] reduce_table() {
        return _reduce_table;
    }

    /** Instance of action encapsulation class. */
    protected CUP$parser$actions action_obj;

    /** Action encapsulation object initializer. */
    @Override
    protected void init_actions() {
        action_obj = new CUP$parser$actions(this);
    }

    /** Invoke a user supplied parse action. */
    @Override
    public trywithcatch.java_cup.runtime.Symbol do_action(int act_num,
            trywithcatch.java_cup.runtime.lr_parser parser, java.util.Stack stack, int top)
            throws java.lang.Exception {

        /* call code in generated class */
        return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
    }

    /** Indicates start state. */
    @Override
    public int start_state() {
        return 0;
    }

    /** Indicates start production. */
    @Override
    public int start_production() {
        return 1;
    }

    /** <code>EOF</code> Symbol index. */
    @Override
    public int EOF_sym() {
        return 0;
    }

    /** <code>error</code> Symbol index. */
    @Override
    public int error_sym() {
        return 1;
    }
}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {
    private final parser parser;

    /** Constructor */
    CUP$parser$actions(parser parser) {
        this.parser = parser;
    }

    /** Method with the actual generated action code. */
    public final trywithcatch.java_cup.runtime.Symbol CUP$parser$do_action(int CUP$parser$act_num,
            trywithcatch.java_cup.runtime.lr_parser CUP$parser$parser, java.util.Stack CUP$parser$stack,
            int CUP$parser$top) throws java.lang.Exception {

        /* Symbol object for return from actions */
        trywithcatch.java_cup.runtime.Symbol CUP$parser$result;

        /* select the action based on the action number */
        switch (CUP$parser$act_num) {

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 13: // finally ::= 
            {
                Block RESULT = null;
                RESULT = null;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    6 /*finally*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 12: // finally ::= FINALLY block 
            {
                Block RESULT = null;
                int bleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int bright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Block b = (Block) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = b;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    6 /*finally*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 1)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 11: // catch_blocks ::= 
            {
                List RESULT = null;
                RESULT = new LinkedList();
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    5 /*catch_blocks*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 10: // catch_blocks ::= catch_blocks CATCH IDENT block 
            {
                List RESULT = null;
                int cleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 3)).left;
                int cright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 3)).right;
                List c = (List) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 3)).value;
                int ileft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).left;
                int iright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).right;
                String i = (String) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).value;
                int bleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int bright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Block b = (Block) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                c.add(new Catch(i, b));
                RESULT = c;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    5 /*catch_blocks*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 3)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 9: // try_catch ::= TRY block catch_blocks finally 
            {
                TryCatch RESULT = null;
                int tleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 3)).left;
                int tright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 3)).right;
                Terminal t = (Terminal) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 3)).value;
                int bleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 2)).left;
                int bright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 2)).right;
                Block b = (Block) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 2)).value;
                int cleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).left;
                int cright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).right;
                List c = (List) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).value;
                int fleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int fright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Block f = (Block) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = new TryCatch(t, b, c, f);
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    4 /*try_catch*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 3)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 8: // block ::= BLOCK_START things BLOCK_END 
            {
                Block RESULT = null;
                int sleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 2)).left;
                int sright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 2)).right;
                Terminal s = (Terminal) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 2)).value;
                int tleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).left;
                int tright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).right;
                List t = (List) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).value;
                int eleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int eright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Terminal e = (Terminal) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = new Block(s, e, t);
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    3 /*block*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 2)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 7: // anything ::= try_catch 
            {
                Anything RESULT = null;
                int rleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int rright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                TryCatch r = (TryCatch) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = r;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    2 /*anything*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 6: // anything ::= REMOVE_TRY_WITH_CATCH 
            {
                Anything RESULT = null;
                int rleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int rright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Terminal r = (Terminal) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = r;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    2 /*anything*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 5: // anything ::= END_TRY_WITH_CATCH 
            {
                Anything RESULT = null;
                int rleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int rright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Terminal r = (Terminal) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = r;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    2 /*anything*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 4: // anything ::= TRY_WITH_CATCH 
            {
                Anything RESULT = null;
                int rleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int rright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Terminal r = (Terminal) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = r;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    2 /*anything*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 3: // anything ::= block 
            {
                Anything RESULT = null;
                int rleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int rright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Block r = (Block) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                RESULT = r;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    2 /*anything*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 2: // things ::= 
            {
                List RESULT = null;
                RESULT = new LinkedList();
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    1 /*things*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 1: // $START ::= things EOF 
            {
                Object RESULT = null;
                int start_valleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).left;
                int start_valright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).right;
                List start_val = (List) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).value;
                RESULT = start_val;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    0 /*$START*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 1)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                /* ACCEPT */
                CUP$parser$parser.done_parsing();
                return CUP$parser$result;

                /*. . . . . . . . . . . . . . . . . . . .*/
            case 0: // things ::= things anything 
            {
                List RESULT = null;
                int tleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).left;
                int tright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).right;
                List t = (List) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 1)).value;
                int aleft = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).left;
                int aright = ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).right;
                Anything a = (Anything) ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack
                        .elementAt(CUP$parser$top - 0)).value;
                if (!a.isEmpty()) {
                    t.add(a);
                }
                RESULT = t;
                CUP$parser$result = new trywithcatch.java_cup.runtime.Symbol(
                    1 /*things*/,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 1)).left,
                    ((trywithcatch.java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top - 0)).right,
                    RESULT);
            }
                return CUP$parser$result;

                /* . . . . . .*/
            default:
                throw new Exception("Invalid action number found in internal parse table");
        }
    }
}
